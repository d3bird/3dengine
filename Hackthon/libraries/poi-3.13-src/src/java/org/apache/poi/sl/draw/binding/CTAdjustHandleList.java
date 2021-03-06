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

package org.apache.poi.sl.draw.binding;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CT_AdjustHandleList complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CT_AdjustHandleList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="ahXY" type="{http://schemas.openxmlformats.org/drawingml/2006/main}CT_XYAdjustHandle"/>
 *         &lt;element name="ahPolar" type="{http://schemas.openxmlformats.org/drawingml/2006/main}CT_PolarAdjustHandle"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CT_AdjustHandleList", namespace = "http://schemas.openxmlformats.org/drawingml/2006/main", propOrder = {
    "ahXYOrAhPolar"
})
public class CTAdjustHandleList {

    @XmlElements({
        @XmlElement(name = "ahXY", namespace = "http://schemas.openxmlformats.org/drawingml/2006/main", type = CTXYAdjustHandle.class),
        @XmlElement(name = "ahPolar", namespace = "http://schemas.openxmlformats.org/drawingml/2006/main", type = CTPolarAdjustHandle.class)
    })
    protected List<Object> ahXYOrAhPolar;

    /**
     * Gets the value of the ahXYOrAhPolar property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ahXYOrAhPolar property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAhXYOrAhPolar().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CTXYAdjustHandle }
     * {@link CTPolarAdjustHandle }
     *
     *
     */
    public List<Object> getAhXYOrAhPolar() {
        if (ahXYOrAhPolar == null) {
            ahXYOrAhPolar = new ArrayList<Object>();
        }
        return this.ahXYOrAhPolar;
    }

    public boolean isSetAhXYOrAhPolar() {
        return ((this.ahXYOrAhPolar!= null)&&(!this.ahXYOrAhPolar.isEmpty()));
    }

    public void unsetAhXYOrAhPolar() {
        this.ahXYOrAhPolar = null;
    }

}
