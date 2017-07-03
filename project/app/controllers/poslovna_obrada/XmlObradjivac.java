package controllers.poslovna_obrada;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.example.instrumenti_placanja.InstrumentiPlacanja;
import org.xml.sax.SAXException;

public class XmlObradjivac {
	public InstrumentiPlacanja ucitajInstrumentePlacanja(File xmlDatoteka) throws JAXBException, SAXException  {
		// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
		JAXBContext context = JAXBContext.newInstance("org.example.instrumenti_placanja");
		// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
		Unmarshaller unmarshaller = context.createUnmarshaller();
		//Kreiranje seme po kojoj se vrsi validacija zada se tip seme
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		//I lokacija na kojoj se nalazi
		Schema schema = schemaFactory.newSchema(new File("instrumenti_placanja.xsd"));
		//Postavljanjem seme na unmarshaller zahtijevamo da se pri ucitavanju podataka vrsi validacija po semi
		unmarshaller.setSchema(schema);
		// Unmarshalling generiše objektni model na osnovu XML fajla 
		InstrumentiPlacanja xmlPodaci = (InstrumentiPlacanja) unmarshaller.unmarshal(xmlDatoteka);
		return xmlPodaci;
	}
}
