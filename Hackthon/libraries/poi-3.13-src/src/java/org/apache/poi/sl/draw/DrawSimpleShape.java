/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.sl.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.poi.sl.draw.binding.CTCustomGeometry2D;
import org.apache.poi.sl.draw.geom.Context;
import org.apache.poi.sl.draw.geom.CustomGeometry;
import org.apache.poi.sl.draw.geom.Outline;
import org.apache.poi.sl.draw.geom.Path;
import org.apache.poi.sl.usermodel.LineDecoration;
import org.apache.poi.sl.usermodel.LineDecoration.DecorationSize;
import org.apache.poi.sl.usermodel.PaintStyle.SolidPaint;
import org.apache.poi.sl.usermodel.Shadow;
import org.apache.poi.sl.usermodel.SimpleShape;
import org.apache.poi.sl.usermodel.StrokeStyle;
import org.apache.poi.sl.usermodel.StrokeStyle.LineCap;
import org.apache.poi.sl.usermodel.StrokeStyle.LineDash;
import org.apache.poi.util.Units;


public class DrawSimpleShape extends DrawShape {

    public DrawSimpleShape(SimpleShape<?,?> shape) {
        super(shape);
    }

    @Override
    public void draw(Graphics2D graphics) {
        DrawPaint drawPaint = DrawFactory.getInstance(graphics).getPaint(getShape());
        Paint fill = drawPaint.getPaint(graphics, getShape().getFillStyle().getPaint());
        Paint line = drawPaint.getPaint(graphics, getShape().getStrokeStyle().getPaint());
        BasicStroke stroke = getStroke(); // the stroke applies both to the shadow and the shape
        graphics.setStroke(stroke);

        Collection<Outline> elems = computeOutlines(graphics);

        // first paint the shadow
        drawShadow(graphics, elems, fill, line);

        // then fill the shape interior
        if (fill != null) {
            graphics.setPaint(fill);
            for (Outline o : elems) {
                if (o.getPath().isFilled()){
                    java.awt.Shape s = o.getOutline();
                    graphics.setRenderingHint(Drawable.GRADIENT_SHAPE, s);
                    graphics.fill(s);
                }
            }
        }

        // then draw any content within this shape (text, image, etc.)
        drawContent(graphics);

        // then stroke the shape outline
        if(line != null) {
            graphics.setPaint(line);
            for(Outline o : elems){
                if(o.getPath().isStroked()){
                    java.awt.Shape s = o.getOutline();
                    graphics.setRenderingHint(Drawable.GRADIENT_SHAPE, s);
                    graphics.draw(s);
                }
            }
        }

        // draw line decorations
        drawDecoration(graphics, line, stroke);
    }

    protected void drawDecoration(Graphics2D graphics, Paint line, BasicStroke stroke) {
        if(line == null) return;
        graphics.setPaint(line);

        List<Outline> lst = new ArrayList<Outline>();
        LineDecoration deco = getShape().getLineDecoration();
        Outline head = getHeadDecoration(graphics, deco, stroke);
        if (head != null) lst.add(head);
        Outline tail = getTailDecoration(graphics, deco, stroke);
        if (tail != null) lst.add(tail);


        for(Outline o : lst){
            java.awt.Shape s = o.getOutline();
            Path p = o.getPath();
            graphics.setRenderingHint(Drawable.GRADIENT_SHAPE, s);

            if(p.isFilled()) graphics.fill(s);
            if(p.isStroked()) graphics.draw(s);
        }
    }

    protected Outline getTailDecoration(Graphics2D graphics, LineDecoration deco, BasicStroke stroke) {
        DecorationSize tailLength = deco.getTailLength();
        DecorationSize tailWidth = deco.getTailWidth();

        double lineWidth = Math.max(2.5, stroke.getLineWidth());

        Rectangle2D anchor = getAnchor(graphics, getShape());
        double x2 = anchor.getX() + anchor.getWidth(),
               y2 = anchor.getY() + anchor.getHeight();

        double alpha = Math.atan(anchor.getHeight() / anchor.getWidth());

        AffineTransform at = new AffineTransform();
        java.awt.Shape tailShape = null;
        Path p = null;
        Rectangle2D bounds;
        final double scaleY = Math.pow(2, tailWidth.ordinal()+1);
        final double scaleX = Math.pow(2, tailLength.ordinal()+1);
        switch (deco.getTailShape()) {
            case OVAL:
                p = new Path();
                tailShape = new Ellipse2D.Double(0, 0, lineWidth * scaleX, lineWidth * scaleY);
                bounds = tailShape.getBounds2D();
                at.translate(x2 - bounds.getWidth() / 2, y2 - bounds.getHeight() / 2);
                at.rotate(alpha, bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2);
                break;
            case STEALTH:
            case ARROW:
                p = new Path(false, true);
                GeneralPath arrow = new GeneralPath();
                arrow.moveTo((float) (-lineWidth * scaleX), (float) (-lineWidth * scaleY / 2));
                arrow.lineTo(0, 0);
                arrow.lineTo((float) (-lineWidth * scaleX), (float) (lineWidth * scaleY / 2));
                tailShape = arrow;
                at.translate(x2, y2);
                at.rotate(alpha);
                break;
            case TRIANGLE:
                p = new Path();
                GeneralPath triangle = new GeneralPath();
                triangle.moveTo((float) (-lineWidth * scaleX), (float) (-lineWidth * scaleY / 2));
                triangle.lineTo(0, 0);
                triangle.lineTo((float) (-lineWidth * scaleX), (float) (lineWidth * scaleY / 2));
                triangle.closePath();
                tailShape = triangle;
                at.translate(x2, y2);
                at.rotate(alpha);
                break;
            default:
                break;
        }

        if (tailShape != null) {
            tailShape = at.createTransformedShape(tailShape);
        }
        return tailShape == null ? null : new Outline(tailShape, p);
    }

    protected Outline getHeadDecoration(Graphics2D graphics, LineDecoration deco, BasicStroke stroke) {
        DecorationSize headLength = deco.getHeadLength();
        DecorationSize headWidth = deco.getHeadWidth();

        double lineWidth = Math.max(2.5, stroke.getLineWidth());

        Rectangle2D anchor = getAnchor(graphics, getShape());
        double x1 = anchor.getX(),
                y1 = anchor.getY();

        double alpha = Math.atan(anchor.getHeight() / anchor.getWidth());

        AffineTransform at = new AffineTransform();
        java.awt.Shape headShape = null;
        Path p = null;
        Rectangle2D bounds;
        final double scaleY = Math.pow(2, headWidth.ordinal()+1);
        final double scaleX = Math.pow(2, headLength.ordinal()+1);
        switch (deco.getHeadShape()) {
            case OVAL:
                p = new Path();
                headShape = new Ellipse2D.Double(0, 0, lineWidth * scaleX, lineWidth * scaleY);
                bounds = headShape.getBounds2D();
                at.translate(x1 - bounds.getWidth() / 2, y1 - bounds.getHeight() / 2);
                at.rotate(alpha, bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2);
                break;
            case STEALTH:
            case ARROW:
                p = new Path(false, true);
                GeneralPath arrow = new GeneralPath();
                arrow.moveTo((float) (lineWidth * scaleX), (float) (-lineWidth * scaleY / 2));
                arrow.lineTo(0, 0);
                arrow.lineTo((float) (lineWidth * scaleX), (float) (lineWidth * scaleY / 2));
                headShape = arrow;
                at.translate(x1, y1);
                at.rotate(alpha);
                break;
            case TRIANGLE:
                p = new Path();
                GeneralPath triangle = new GeneralPath();
                triangle.moveTo((float) (lineWidth * scaleX), (float) (-lineWidth * scaleY / 2));
                triangle.lineTo(0, 0);
                triangle.lineTo((float) (lineWidth * scaleX), (float) (lineWidth * scaleY / 2));
                triangle.closePath();
                headShape = triangle;
                at.translate(x1, y1);
                at.rotate(alpha);
                break;
            default:
                break;
        }

        if (headShape != null) {
            headShape = at.createTransformedShape(headShape);
        }
        return headShape == null ? null : new Outline(headShape, p);
    }

    public BasicStroke getStroke() {
        StrokeStyle strokeStyle = getShape().getStrokeStyle();

        float lineWidth = (float) strokeStyle.getLineWidth();
        if (lineWidth == 0.0f) lineWidth = 0.25f; // Both PowerPoint and OOo draw zero-length lines as 0.25pt

        LineDash lineDash = strokeStyle.getLineDash();
        if (lineDash == null) {
        	lineDash = LineDash.SOLID;
        }

        int dashPatI[] = lineDash.pattern;
        final float dash_phase = 0;
        float[] dashPatF = null;
        if (dashPatI != null) {
            dashPatF = new float[dashPatI.length];
            for (int i=0; i<dashPatI.length; i++) {
                dashPatF[i] = dashPatI[i]*Math.max(1, lineWidth);
            }
        }

        LineCap lineCapE = strokeStyle.getLineCap();
        if (lineCapE == null) lineCapE = LineCap.FLAT;
        int lineCap;
        switch (lineCapE) {
            case ROUND:
                lineCap = BasicStroke.CAP_ROUND;
                break;
            case SQUARE:
                lineCap = BasicStroke.CAP_SQUARE;
                break;
            default:
            case FLAT:
                lineCap = BasicStroke.CAP_BUTT;
                break;
        }

        int lineJoin = BasicStroke.JOIN_ROUND;

        return new BasicStroke(lineWidth, lineCap, lineJoin, lineWidth, dashPatF, dash_phase);
    }

    protected void drawShadow(
            Graphics2D graphics
          , Collection<Outline> outlines
          , Paint fill
          , Paint line
    ) {
          Shadow<?,?> shadow = getShape().getShadow();
          if (shadow == null || (fill == null && line == null)) return;

          SolidPaint shadowPaint = shadow.getFillStyle();
          Color shadowColor = DrawPaint.applyColorTransform(shadowPaint.getSolidColor());

          double shapeRotation = getShape().getRotation();
          if(getShape().getFlipVertical()) {
              shapeRotation += 180;
          }
          double angle = shadow.getAngle() - shapeRotation;
          double dist = shadow.getDistance();
          double dx = dist * Math.cos(Math.toRadians(angle));
          double dy = dist * Math.sin(Math.toRadians(angle));

          graphics.translate(dx, dy);

          for(Outline o : outlines){
              java.awt.Shape s = o.getOutline();
              Path p = o.getPath();
              graphics.setRenderingHint(Drawable.GRADIENT_SHAPE, s);
              graphics.setPaint(shadowColor);

              if(fill != null && p.isFilled()){
                  graphics.fill(s);
              } else if (line != null && p.isStroked()) {
                  graphics.draw(s);
              }
          }

          graphics.translate(-dx, -dy);
      }

    protected static CustomGeometry getCustomGeometry(String name) {
        return getCustomGeometry(name, null);
    }

    protected static CustomGeometry getCustomGeometry(String name, Graphics2D graphics) {
        @SuppressWarnings("unchecked")
        Map<String, CustomGeometry> presets = (graphics == null)
            ? null
            : (Map<String, CustomGeometry>)graphics.getRenderingHint(Drawable.PRESET_GEOMETRY_CACHE);

        if (presets == null) {
            presets = new HashMap<String,CustomGeometry>();
            if (graphics != null) {
                graphics.setRenderingHint(Drawable.PRESET_GEOMETRY_CACHE, presets);
            }

            String packageName = "org.apache.poi.sl.draw.binding";
            InputStream presetIS = Drawable.class.getResourceAsStream("presetShapeDefinitions.xml");

            // StAX:
            EventFilter startElementFilter = new EventFilter() {
                @Override
                public boolean accept(XMLEvent event) {
                    return event.isStartElement();
                }
            };

            try {
                XMLInputFactory staxFactory = XMLInputFactory.newInstance();
                XMLEventReader staxReader = staxFactory.createXMLEventReader(presetIS);
                XMLEventReader staxFiltRd = staxFactory.createFilteredReader(staxReader, startElementFilter);
                // Ignore StartElement:
                staxFiltRd.nextEvent();
                // JAXB:
                JAXBContext jaxbContext = JAXBContext.newInstance(packageName);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                while (staxFiltRd.peek() != null) {
                    StartElement evRoot = (StartElement)staxFiltRd.peek();
                    String cusName = evRoot.getName().getLocalPart();
                    // XMLEvent ev = staxReader.nextEvent();
                    JAXBElement<org.apache.poi.sl.draw.binding.CTCustomGeometry2D> el = unmarshaller.unmarshal(staxReader, CTCustomGeometry2D.class);
                    CTCustomGeometry2D cusGeom = el.getValue();

                    presets.put(cusName, new CustomGeometry(cusGeom));
                }
            } catch (Exception e) {
                throw new RuntimeException("Unable to load preset geometries.", e);
            } finally {
                try {
                    presetIS.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to load preset geometries.", e);
                }
            }
        }

        return presets.get(name);
    }

    protected Collection<Outline> computeOutlines(Graphics2D graphics) {

        List<Outline> lst = new ArrayList<Outline>();
        CustomGeometry geom = getShape().getGeometry();
        if(geom == null) {
            return lst;
        }

        Rectangle2D anchor = getAnchor(graphics, getShape());
        for (Path p : geom) {

            double w = p.getW() == -1 ? anchor.getWidth() * Units.EMU_PER_POINT : p.getW();
            double h = p.getH() == -1 ? anchor.getHeight() * Units.EMU_PER_POINT : p.getH();

            // the guides in the shape definitions are all defined relative to each other,
            // so we build the path starting from (0,0).
            final Rectangle2D pathAnchor = new Rectangle2D.Double(0,0,w,h);

            Context ctx = new Context(geom, pathAnchor, getShape());

            java.awt.Shape gp = p.getPath(ctx);

            // translate the result to the canvas coordinates in points
            AffineTransform at = new AffineTransform();
            at.translate(anchor.getX(), anchor.getY());

            double scaleX, scaleY;
            if (p.getW() != -1) {
                scaleX = anchor.getWidth() / p.getW();
            } else {
                scaleX = 1.0 / Units.EMU_PER_POINT;
            }
            if (p.getH() != -1) {
                scaleY = anchor.getHeight() / p.getH();
            } else {
                scaleY = 1.0 / Units.EMU_PER_POINT;
            }

            at.scale(scaleX, scaleY);

            java.awt.Shape canvasShape = at.createTransformedShape(gp);

            lst.add(new Outline(canvasShape, p));
        }

        return lst;
    }

    @Override
    protected SimpleShape<?,?> getShape() {
        return (SimpleShape<?,?>)shape;
    }
}
