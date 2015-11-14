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

package org.apache.poi.sl.usermodel;

import java.awt.Color;



public interface TextParagraph<
    S extends Shape<S,P>,
    P extends TextParagraph<S,P,T>,
    T extends TextRun
> extends Iterable<T> {

    /**
     * Specifies a list of text alignment types
     */
    public enum TextAlign {
        /**
         * For horizontal text, left aligned.
         * For vertical text, top aligned.
         */
        LEFT,

        /**
         * For horizontal text, centered.
         * For vertical text, middle aligned.
         */
        CENTER,

        /**
         * For horizontal text, right aligned.
         * For vertical text, bottom aligned.
         */
        RIGHT,

        /**
         * Align text so that it is justified across the whole line. It
         * is smart in the sense that it will not justify sentences
         * which are short
         * 
         * For horizontal text, flush left and right.
         * For vertical text, flush top and bottom.
         */
        JUSTIFY,
        
        /**
         * Kashida justify low.
         */    
        JUSTIFY_LOW,

        /**
         * Distribute space between characters.
         */
        DIST,
        
        /**
         * Thai distribution justification.
         */
        THAI_DIST
    }

    /**
     * 
     */
    public enum FontAlign {
        AUTO,
        
        /**
         * Characters hang from top of line height.
         * Also known as "Hanging"
         */
        TOP,
        
        /**
         * Characters centered within line height.
         */
        CENTER,
        
        /**
         * Place characters on font baseline.
         * Also known as "Roman" 
         */
        BASELINE,
        
        /**
         * Characters are anchored to the very bottom of a single line.
         * This is different than BASELINE because of letters such as "g", "q", and "y".
         * Also known as "UpholdFixed"
         */
        BOTTOM; 
    }
    
    public interface BulletStyle {
        String getBulletCharacter();
        String getBulletFont();
        
        /**
         * The bullet point font size
         * If bulletFontSize >= 0, then space is a percentage of normal line height.
         * If bulletFontSize < 0, the absolute value in points
         *
         * @return the bullet point font size
         */
        Double getBulletFontSize();

        /**
         * Convenience function to set a solid color
         */
        void setBulletFontColor(Color color);
        
        void setBulletFontColor(PaintStyle color);

        /**
         *
         * @return the color of bullet characters within a given paragraph.
         * A {@code null} value means to use the text font color.
         */
        PaintStyle getBulletFontColor();
        
        AutoNumberingScheme getAutoNumberingScheme();
        /**
         * Index (1-based) of the first auto number value, or null if auto numbering scheme
         * wasn't assigned.
         */
        Integer getAutoNumberingStartAt();
    }

    /**
     * The amount of vertical white space before the paragraph
     * This may be specified in two different ways, percentage spacing and font point spacing:
     * <p>
     * If spaceBefore >= 0, then space is a percentage of normal line height.
     * If spaceBefore < 0, the absolute value in points
     * </p>
     *
     * @return the vertical white space before the paragraph, or null if unset
     */
    Double getSpaceBefore();

    /**
     * Set the amount of vertical white space that will be present before the paragraph.
     * This space is specified in either percentage or points:
     * <p>
     * If spaceBefore >= 0, then space is a percentage of normal line height.
     * If spaceBefore < 0, the absolute value of linespacing is the spacing in points
     * </p>
     * Examples:
     * <pre><code>
     *      // The paragraph will be formatted to have a spacing before the paragraph text.
     *      // The spacing will be 200% of the size of the largest text on each line
     *      paragraph.setSpaceBefore(200);
     *
     *      // The spacing will be a size of 48 points
     *      paragraph.setSpaceBefore(-48.0);
     * </code></pre>
     *
     * @param spaceBefore the vertical white space before the paragraph, null to unset
     */
    void setSpaceBefore(Double spaceBefore);    
    
    /**
     * The amount of vertical white space after the paragraph
     * This may be specified in two different ways, percentage spacing and font point spacing:
     * <p>
     * If spaceBefore >= 0, then space is a percentage of normal line height.
     * If spaceBefore < 0, the absolute value of linespacing is the spacing in points
     * </p>
     *
     * @return the vertical white space after the paragraph or null, if unset
     */
    Double getSpaceAfter();    

    /**
     * Set the amount of vertical white space that will be present after the paragraph.
     * This space is specified in either percentage or points:
     * <p>
     * If spaceAfter >= 0, then space is a percentage of normal line height.
     * If spaceAfter < 0, the absolute value of linespacing is the spacing in points
     * </p>
     * Examples:
     * <pre><code>
     *      // The paragraph will be formatted to have a spacing after the paragraph text.
     *      // The spacing will be 200% of the size of the largest text on each line
     *      paragraph.setSpaceAfter(200);
     *
     *      // The spacing will be a size of 48 points
     *      paragraph.setSpaceAfter(-48.0);
     * </code></pre>
     *
     * @param spaceAfter the vertical white space after the paragraph, null to unset
     */
    public void setSpaceAfter(Double spaceAfter);
    
    /**
     * @return the left margin (in points) of the paragraph or null, if unset
     */
    Double getLeftMargin();

    /**
     * Specifies the left margin of the paragraph. This is specified in addition to the text body
     * inset and applies only to this text paragraph. That is the text body Inset and the LeftMargin
     * attributes are additive with respect to the text position.
     * 
     * @param leftMargin the left margin (in points) or null to unset
     */
    void setLeftMargin(Double leftMargin);
    
    
    /**
     * Specifies the right margin of the paragraph. This is specified in addition to the text body
     * inset and applies only to this text paragraph. That is the text body Inset and the RightMargin
     * attributes are additive with respect to the text position.
     * 
     * The right margin is not support and therefore ignored by the HSLF implementation.
     * 
     * @return the right margin (in points) of the paragraph or null, if unset
     */
    Double getRightMargin();

    /**
     * @param rightMargin the right margin (in points) of the paragraph
     */
    void setRightMargin(Double rightMargin);
    
    /**
     * @return the indent (in points) applied to the first line of text in the paragraph.
     *  or null, if unset
     */
    Double getIndent();
    
    /**
     * Specifies the indent size that will be applied to the first line of text in the paragraph.   
     * 
     * @param indent the indent (in points) applied to the first line of text in the paragraph
     */
    void setIndent(Double indent);


    /**
     * @return the text level of this paragraph (0-based). Default is 0.
     */    
    int getIndentLevel();

    /**
     * Specifies the particular level text properties that this paragraph will follow.
     * The value for this attribute formats the text according to the corresponding level
     * paragraph properties defined in the SlideMaster.
     *
     * @param level the level (0 ... 4)
     */
    void setIndentLevel(int level);
    
    /**
     * Returns the vertical line spacing that is to be used within a paragraph.
     * This may be specified in two different ways, percentage spacing and font point spacing:
     * <p>
     * If linespacing >= 0, then linespacing is a percentage of normal line height.
     * If linespacing < 0, the absolute value of linespacing is the spacing in points
     * </p>
     *
     * @return the vertical line spacing or null, if unset
     */
    Double getLineSpacing();
    
    /**
     * This element specifies the vertical line spacing that is to be used within a paragraph.
     * This may be specified in two different ways, percentage spacing and font point spacing:
     * <p>
     * If linespacing >= 0, then linespacing is a percentage of normal line height
     * If linespacing < 0, the absolute value of linespacing is the spacing in points
     * </p>
     * Examples:
     * <pre><code>
     *      // spacing will be 120% of the size of the largest text on each line
     *      paragraph.setLineSpacing(120);
     *
     *      // spacing will be 200% of the size of the largest text on each line
     *      paragraph.setLineSpacing(200);
     *
     *      // spacing will be 48 points
     *      paragraph.setLineSpacing(-48.0);
     * </code></pre>
     * 
     * @param lineSpacing the vertical line spacing
     */
    void setLineSpacing(Double lineSpacing);

    String getDefaultFontFamily();
    
    /**
     * @return the default font size, in case its not set in the textrun or null, if unset
     */
    Double getDefaultFontSize();
    
    /**
     * Returns the alignment that is applied to the paragraph.
     *
     * If this attribute is omitted, then a value of left is implied.
     * @return ??? alignment that is applied to the paragraph
     */
    TextAlign getTextAlign();
    
    
    /**
     * Returns the font alignment that is applied to the paragraph.
     *
     * If this attribute is omitted, then null is return,
     * user code can imply the a value of {@link FontAlign#AUTO}
     * 
     * @return alignment that is applied to the paragraph
     */
    FontAlign getFontAlign();
    
    /**
     * @return the bullet style of the paragraph, if {@code null} then no bullets are used 
     */
    BulletStyle getBulletStyle();
    
    /**
     * @return the default size for a tab character within this paragraph in points, null if unset
     */
    Double getDefaultTabSize();

    
    TextShape<S,P> getParentShape();
}
