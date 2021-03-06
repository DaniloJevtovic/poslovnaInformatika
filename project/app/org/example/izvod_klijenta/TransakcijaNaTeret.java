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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransakcijaNaTeret complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransakcijaNaTeret">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.org/izvod_klijenta}Transakcija">
 *       &lt;sequence>
 *         &lt;element name="povjerilac" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="racun_povjerioca" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="model_odobrenja" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="poziv_na_broj_odobrenja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransakcijaNaTeret", propOrder = {
    "povjerilac",
    "racunPovjerioca",
    "modelOdobrenja",
    "pozivNaBrojOdobrenja"
})
public class TransakcijaNaTeret
    extends Transakcija
{

    @XmlElement(required = true)
    protected String povjerilac;
    @XmlElement(name = "racun_povjerioca", required = true)
    protected String racunPovjerioca;
    @XmlElement(name = "model_odobrenja")
    protected Integer modelOdobrenja;
    @XmlElement(name = "poziv_na_broj_odobrenja")
    protected String pozivNaBrojOdobrenja;

    /**
     * Gets the value of the povjerilac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPovjerilac() {
        return povjerilac;
    }

    /**
     * Sets the value of the povjerilac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPovjerilac(String value) {
        this.povjerilac = value;
    }

    /**
     * Gets the value of the racunPovjerioca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRacunPovjerioca() {
        return racunPovjerioca;
    }

    /**
     * Sets the value of the racunPovjerioca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRacunPovjerioca(String value) {
        this.racunPovjerioca = value;
    }

    /**
     * Gets the value of the modelOdobrenja property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getModelOdobrenja() {
        return modelOdobrenja;
    }

    /**
     * Sets the value of the modelOdobrenja property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setModelOdobrenja(Integer value) {
        this.modelOdobrenja = value;
    }

    /**
     * Gets the value of the pozivNaBrojOdobrenja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPozivNaBrojOdobrenja() {
        return pozivNaBrojOdobrenja;
    }

    /**
     * Sets the value of the pozivNaBrojOdobrenja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPozivNaBrojOdobrenja(String value) {
        this.pozivNaBrojOdobrenja = value;
    }

}
