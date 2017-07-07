//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.05 at 10:42:18 PM CEST 
//


package org.example.izvod_klijenta;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DnevnoStanjeRacuna complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DnevnoStanjeRacuna">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="datum" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="prethodno_stanje" type="{http://www.example.org/izvod_klijenta}Iznos"/>
 *         &lt;element name="promet_u_korist" type="{http://www.example.org/izvod_klijenta}Iznos"/>
 *         &lt;element name="promet_na_teret" type="{http://www.example.org/izvod_klijenta}Iznos"/>
 *         &lt;element name="novo_stanje" type="{http://www.example.org/izvod_klijenta}Iznos"/>
 *         &lt;element name="transakcije" type="{http://www.example.org/izvod_klijenta}Transakcije"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id_dnevnog_stanja" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DnevnoStanjeRacuna", propOrder = {
    "datum",
    "prethodnoStanje",
    "prometUKorist",
    "prometNaTeret",
    "novoStanje",
    "transakcije"
})
public class DnevnoStanjeRacuna {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datum;
    @XmlElement(name = "prethodno_stanje", required = true)
    protected BigDecimal prethodnoStanje;
    @XmlElement(name = "promet_u_korist", required = true)
    protected BigDecimal prometUKorist;
    @XmlElement(name = "promet_na_teret", required = true)
    protected BigDecimal prometNaTeret;
    @XmlElement(name = "novo_stanje", required = true)
    protected BigDecimal novoStanje;
    @XmlElement(required = true)
    protected Transakcije transakcije;
    @XmlAttribute(name = "id_dnevnog_stanja")
    protected Long idDnevnogStanja;

    /**
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatum(XMLGregorianCalendar value) {
        this.datum = value;
    }

    /**
     * Gets the value of the prethodnoStanje property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrethodnoStanje() {
        return prethodnoStanje;
    }

    /**
     * Sets the value of the prethodnoStanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrethodnoStanje(BigDecimal value) {
        this.prethodnoStanje = value;
    }

    /**
     * Gets the value of the prometUKorist property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrometUKorist() {
        return prometUKorist;
    }

    /**
     * Sets the value of the prometUKorist property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrometUKorist(BigDecimal value) {
        this.prometUKorist = value;
    }

    /**
     * Gets the value of the prometNaTeret property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrometNaTeret() {
        return prometNaTeret;
    }

    /**
     * Sets the value of the prometNaTeret property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrometNaTeret(BigDecimal value) {
        this.prometNaTeret = value;
    }

    /**
     * Gets the value of the novoStanje property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNovoStanje() {
        return novoStanje;
    }

    /**
     * Sets the value of the novoStanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNovoStanje(BigDecimal value) {
        this.novoStanje = value;
    }

    /**
     * Gets the value of the transakcije property.
     * 
     * @return
     *     possible object is
     *     {@link Transakcije }
     *     
     */
    public Transakcije getTransakcije() {
        return transakcije;
    }

    /**
     * Sets the value of the transakcije property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transakcije }
     *     
     */
    public void setTransakcije(Transakcije value) {
        this.transakcije = value;
    }

    /**
     * Gets the value of the idDnevnogStanja property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdDnevnogStanja() {
        return idDnevnogStanja;
    }

    /**
     * Sets the value of the idDnevnogStanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdDnevnogStanja(Long value) {
        this.idDnevnogStanja = value;
    }

}