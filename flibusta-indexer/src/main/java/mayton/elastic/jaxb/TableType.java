//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.03 at 07:05:52 PM EEST 
//


package mayton.elastic.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Basic html-like tables
 * 
 * <p>Java class for tableType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tableType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tr" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice maxOccurs="unbounded">
 *                   &lt;element name="th" type="{http://www.gribuser.ru/xml/fictionbook/2.0}tdType"/>
 *                   &lt;element name="td" type="{http://www.gribuser.ru/xml/fictionbook/2.0}tdType"/>
 *                 &lt;/choice>
 *                 &lt;attribute name="align" type="{http://www.gribuser.ru/xml/fictionbook/2.0}alignType" default="left" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tableType", propOrder = {
    "tr"
})
public class TableType {

    @XmlElement(required = true)
    protected List<TableType.Tr> tr;
    @XmlAttribute(name = "style")
    protected String style;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the tr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TableType.Tr }
     * 
     * 
     */
    public List<TableType.Tr> getTr() {
        if (tr == null) {
            tr = new ArrayList<TableType.Tr>();
        }
        return this.tr;
    }

    /**
     * Gets the value of the style property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the value of the style property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyle(String value) {
        this.style = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice maxOccurs="unbounded">
     *         &lt;element name="th" type="{http://www.gribuser.ru/xml/fictionbook/2.0}tdType"/>
     *         &lt;element name="td" type="{http://www.gribuser.ru/xml/fictionbook/2.0}tdType"/>
     *       &lt;/choice>
     *       &lt;attribute name="align" type="{http://www.gribuser.ru/xml/fictionbook/2.0}alignType" default="left" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "thOrTd"
    })
    public static class Tr {

        @XmlElementRefs({
            @XmlElementRef(name = "th", namespace = "http://www.gribuser.ru/xml/fictionbook/2.0", type = JAXBElement.class, required = false),
            @XmlElementRef(name = "td", namespace = "http://www.gribuser.ru/xml/fictionbook/2.0", type = JAXBElement.class, required = false)
        })
        protected List<JAXBElement<TdType>> thOrTd;
        @XmlAttribute(name = "align")
        protected AlignType align;

        /**
         * Gets the value of the thOrTd property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the thOrTd property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getThOrTd().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JAXBElement }{@code <}{@link TdType }{@code >}
         * {@link JAXBElement }{@code <}{@link TdType }{@code >}
         * 
         * 
         */
        public List<JAXBElement<TdType>> getThOrTd() {
            if (thOrTd == null) {
                thOrTd = new ArrayList<JAXBElement<TdType>>();
            }
            return this.thOrTd;
        }

        /**
         * Gets the value of the align property.
         * 
         * @return
         *     possible object is
         *     {@link AlignType }
         *     
         */
        public AlignType getAlign() {
            if (align == null) {
                return AlignType.LEFT;
            } else {
                return align;
            }
        }

        /**
         * Sets the value of the align property.
         * 
         * @param value
         *     allowed object is
         *     {@link AlignType }
         *     
         */
        public void setAlign(AlignType value) {
            this.align = value;
        }

    }

}
