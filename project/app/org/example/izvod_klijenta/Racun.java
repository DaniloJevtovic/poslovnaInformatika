//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.05 at 10:42:18 PM CEST 
//


package org.example.izvod_klijenta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Racun complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Racun">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="banka" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="broj_racuna" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valuta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="datum_otvaranja" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="vazeci" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="dnevna_stanja" type="{http://www.example.org/izvod_klijenta}DnevnaStanjaRacuna"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id_racuna" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Racun", propOrder = {
    "banka",
    "brojRacuna",
    "valuta",
    "datumOtvaranja",
    "vazeci",
    "dnevnaStanja"
})
public class Racun {

    @XmlElement(required = true)
    protected String banka;
    @XmlElement(name = "broj_racuna", required = true)
    protected String brojRacuna;
    @XmlElement(required = true)
    protected String valuta;
    @XmlElement(name = "datum_otvaranja", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumOtvaranja;
    protected boolean vazeci;
    @XmlElement(name = "dnevna_stanja", required = true)
    protected DnevnaStanjaRacuna dnevnaStanja;
    @XmlAttribute(name = "id_racuna")
    protected Long idRacuna;

    /**
     * Gets the value of the banka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBanka() {
        return banka;
    }

    /**
     * Sets the value of the banka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBanka(String value) {
        this.banka = value;
    }

    /**
     * Gets the value of the brojRacuna property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojRacuna() {
        return brojRacuna;
    }

    /**
     * Sets the value of the brojRacuna property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojRacuna(String value) {
        this.brojRacuna = value;
    }

    /**
     * Gets the value of the valuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuta() {
        return valuta;
    }

    /**
     * Sets the value of the valuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuta(String value) {
        this.valuta = value;
    }

    /**
     * Gets the value of the datumOtvaranja property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumOtvaranja() {
        return datumOtvaranja;
    }

    /**
     * Sets the value of the datumOtvaranja property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumOtvaranja(XMLGregorianCalendar value) {
        this.datumOtvaranja = value;
    }

    /**
     * Gets the value of the vazeci property.
     * 
     */
    public boolean isVazeci() {
        return vazeci;
    }

    /**
     * Sets the value of the vazeci property.
     * 
     */
    public void setVazeci(boolean value) {
        this.vazeci = value;
    }

    /**
     * Gets the value of the dnevnaStanja property.
     * 
     * @return
     *     possible object is
     *     {@link DnevnaStanjaRacuna }
     *     
     */
    public DnevnaStanjaRacuna getDnevnaStanja() {
        return dnevnaStanja;
    }

    /**
     * Sets the value of the dnevnaStanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link DnevnaStanjaRacuna }
     *     
     */
    public void setDnevnaStanja(DnevnaStanjaRacuna value) {
        this.dnevnaStanja = value;
    }

    /**
     * Gets the value of the idRacuna property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdRacuna() {
        return idRacuna;
    }

    /**
     * Sets the value of the idRacuna property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdRacuna(Long value) {
        this.idRacuna = value;
    }

}