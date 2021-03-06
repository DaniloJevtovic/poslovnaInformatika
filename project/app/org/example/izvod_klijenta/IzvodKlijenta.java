//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.05 at 10:42:18 PM CEST 
//


package org.example.izvod_klijenta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="datum_od" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="datum_do" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="klijent" type="{http://www.example.org/izvod_klijenta}Klijent"/>
 *         &lt;element name="racuni" type="{http://www.example.org/izvod_klijenta}Racuni"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datumOd",
    "datumDo",
    "klijent",
    "racuni"
})
@XmlRootElement(name = "izvod_klijenta")
public class IzvodKlijenta {

    @XmlElement(name = "datum_od", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumOd;
    @XmlElement(name = "datum_do", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumDo;
    @XmlElement(required = true)
    protected Klijent klijent;
    @XmlElement(required = true)
    protected Racuni racuni;

    /**
     * Gets the value of the datumOd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumOd() {
        return datumOd;
    }

    /**
     * Sets the value of the datumOd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumOd(XMLGregorianCalendar value) {
        this.datumOd = value;
    }

    /**
     * Gets the value of the datumDo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumDo() {
        return datumDo;
    }

    /**
     * Sets the value of the datumDo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumDo(XMLGregorianCalendar value) {
        this.datumDo = value;
    }

    /**
     * Gets the value of the klijent property.
     * 
     * @return
     *     possible object is
     *     {@link Klijent }
     *     
     */
    public Klijent getKlijent() {
        return klijent;
    }

    /**
     * Sets the value of the klijent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Klijent }
     *     
     */
    public void setKlijent(Klijent value) {
        this.klijent = value;
    }

    /**
     * Gets the value of the racuni property.
     * 
     * @return
     *     possible object is
     *     {@link Racuni }
     *     
     */
    public Racuni getRacuni() {
        return racuni;
    }

    /**
     * Sets the value of the racuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link Racuni }
     *     
     */
    public void setRacuni(Racuni value) {
        this.racuni = value;
    }

}
