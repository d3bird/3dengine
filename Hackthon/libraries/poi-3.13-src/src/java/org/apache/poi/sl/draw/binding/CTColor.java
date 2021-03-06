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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CT_Color complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CT_Color">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://schemas.openxmlformats.org/drawingml/2006/main}EG_ColorChoice"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CT_Color", namespace = "http://schemas.openxmlformats.org/drawingml/2006/main", propOrder = {
    "scrgbClr",
    "srgbClr",
    "hslClr",
    "sysClr",
    "schemeClr",
    "prstClr"
})
public class CTColor {

    @XmlElement(namespace = "http://schemas.openxmlformats.org/drawingml/2006/main")
    protected CTScRgbColor scrgbClr;
    @XmlElement(namespace = "http://schemas.openxmlformats.org/drawingml/2006/main")
    protected CTSRgbColor srgbClr;
    @XmlElement(namespace = "http://schemas.openxmlformats.org/drawingml/2006/main")
    protected CTHslColor hslClr;
    @XmlElement(namespace = "http://schemas.openxmlformats.org/drawingml/2006/main")
    protected CTSystemColor sysClr;
    @XmlElement(namespace = "http://schemas.openxmlformats.org/drawingml/2006/main")
    protected CTSchemeColor schemeClr;
    @XmlElement(namespace = "http://schemas.openxmlformats.org/drawingml/2006/main")
    protected CTPresetColor prstClr;

    /**
     * Gets the value of the scrgbClr property.
     *
     * @return
     *     possible object is
     *     {@link CTScRgbColor }
     *
     */
    public CTScRgbColor getScrgbClr() {
        return scrgbClr;
    }

    /**
     * Sets the value of the scrgbClr property.
     *
     * @param value
     *     allowed object is
     *     {@link CTScRgbColor }
     *
     */
    public void setScrgbClr(CTScRgbColor value) {
        this.scrgbClr = value;
    }

    public boolean isSetScrgbClr() {
        return (this.scrgbClr!= null);
    }

    /**
     * Gets the value of the srgbClr property.
     *
     * @return
     *     possible object is
     *     {@link CTSRgbColor }
     *
     */
    public CTSRgbColor getSrgbClr() {
        return srgbClr;
    }

    /**
     * Sets the value of the srgbClr property.
     *
     * @param value
     *     allowed object is
     *     {@link CTSRgbColor }
     *
     */
    public void setSrgbClr(CTSRgbColor value) {
        this.srgbClr = value;
    }

    public boolean isSetSrgbClr() {
        return (this.srgbClr!= null);
    }

    /**
     * Gets the value of the hslClr property.
     *
     * @return
     *     possible object is
     *     {@link CTHslColor }
     *
     */
    public CTHslColor getHslClr() {
        return hslClr;
    }

    /**
     * Sets the value of the hslClr property.
     *
     * @param value
     *     allowed object is
     *     {@link CTHslColor }
     *
     */
    public void setHslClr(CTHslColor value) {
        this.hslClr = value;
    }

    public boolean isSetHslClr() {
        return (this.hslClr!= null);
    }

    /**
     * Gets the value of the sysClr property.
     *
     * @return
     *     possible object is
     *     {@link CTSystemColor }
     *
     */
    public CTSystemColor getSysClr() {
        return sysClr;
    }

    /**
     * Sets the value of the sysClr property.
     *
     * @param value
     *     allowed object is
     *     {@link CTSystemColor }
     *
     */
    public void setSysClr(CTSystemColor value) {
        this.sysClr = value;
    }

    public boolean isSetSysClr() {
        return (this.sysClr!= null);
    }

    /**
     * Gets the value of the schemeClr property.
     *
     * @return
     *     possible object is
     *     {@link CTSchemeColor }
     *
     */
    public CTSchemeColor getSchemeClr() {
        return schemeClr;
    }

    /**
     * Sets the value of the schemeClr property.
     *
     * @param value
     *     allowed object is
     *     {@link CTSchemeColor }
     *
     */
    public void setSchemeClr(CTSchemeColor value) {
        this.schemeClr = value;
    }

    public boolean isSetSchemeClr() {
        return (this.schemeClr!= null);
    }

    /**
     * Gets the value of the prstClr property.
     *
     * @return
     *     possible object is
     *     {@link CTPresetColor }
     *
     */
    public CTPresetColor getPrstClr() {
        return prstClr;
    }

    /**
     * Sets the value of the prstClr property.
     *
     * @param value
     *     allowed object is
     *     {@link CTPresetColor }
     *
     */
    public void setPrstClr(CTPresetColor value) {
        this.prstClr = value;
    }

    public boolean isSetPrstClr() {
        return (this.prstClr!= null);
    }

}
