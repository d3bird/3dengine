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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CT_EmbeddedWAVAudioFile complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CT_EmbeddedWAVAudioFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute ref="{http://schemas.openxmlformats.org/officeDocument/2006/relationships}embed use="required""/>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="builtIn" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CT_EmbeddedWAVAudioFile", namespace = "http://schemas.openxmlformats.org/drawingml/2006/main")
public class CTEmbeddedWAVAudioFile {

    @XmlAttribute(namespace = "http://schemas.openxmlformats.org/officeDocument/2006/relationships", required = true)
    protected String embed;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected Boolean builtIn;

    /**
     * Embedded Audio File Relationship ID
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEmbed() {
        return embed;
    }

    /**
     * Sets the value of the embed property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEmbed(String value) {
        this.embed = value;
    }

    public boolean isSetEmbed() {
        return (this.embed!= null);
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        if (name == null) {
            return "";
        } else {
            return name;
        }
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

    /**
     * Gets the value of the builtIn property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isBuiltIn() {
        if (builtIn == null) {
            return false;
        } else {
            return builtIn;
        }
    }

    /**
     * Sets the value of the builtIn property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setBuiltIn(boolean value) {
        this.builtIn = value;
    }

    public boolean isSetBuiltIn() {
        return (this.builtIn!= null);
    }

    public void unsetBuiltIn() {
        this.builtIn = null;
    }

}
