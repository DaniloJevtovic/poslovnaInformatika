package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;

import com.sun.xml.internal.txw2.Document;

import javafx.scene.transform.Rotate;
import jj.play.org.eclipse.mylyn.wikitext.core.parser.builder.DocBookDocumentBuilder;

//import com.sun.java.util.jar.pack.Package.File;

import models.AnalitikaIzvoda;
import models.MedjubankarskiPrenos;
import models.StavkaKliringa;
import play.libs.XPath;
import play.mvc.Controller;

import java.io.*;

import org.apache.ivy.core.event.download.NeedArtifactEvent;
import org.w3c.dom.*;

/*
 * Radi Danilo
 */
public class AnalitikeIzvoda extends Controller{

	public AnalitikeIzvoda() {
		// TODO Auto-generated constructor stub
	}
	
	public static void show(String mode){
		
		
		List<AnalitikaIzvoda> analitikeIzvoda = AnalitikaIzvoda.findAll();
		if(mode == null || mode.equals(""))
		{
			mode = "edit";
		}
		render(analitikeIzvoda, mode);
	}
	
	public static void create(String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			String datumPrijema, String datumValute, String racunDuznika, Integer modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, Integer modelOdobrenja, String pozNaBrojOdobrenja, Boolean hitno, Long iznos,
			Integer tipGreske, String status){
		
		//datum prijema
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datumP = null;
		
		try {
			datumP = dateFormat.parse(datumPrijema);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		//datum valute
		DateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
		Date datumV = null;
		
		try {
			datumV = dateFormat1.parse(datumValute);
		} catch (Exception e) {
			System.out.println("Greska!");
		}

		AnalitikaIzvoda analitikaIzvoda = new AnalitikaIzvoda(duzNalogodavac, svrhaPlacanja, povjerPrimalac, 
				datumP, datumV, racunDuznika, modelZaduzenja, pozNaBrojZaduzenja, racunPovjerioca, modelOdobrenja,
				pozNaBrojOdobrenja, hitno, iznos, tipGreske, status);
		
		analitikaIzvoda.save();		
		show("add");
	}
	
	public static void edit(Long id, String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			String datumPrijema, String datumValute, String racunDuznika, Integer modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, Integer modelOdobrenja, String pozNaBrojOdobrenja, Boolean hitno, Long iznos,
			Integer tipGreske, String status){
		
		// datum prijema
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datumP = null;

		try {
			datumP = dateFormat.parse(datumPrijema);
		} catch (Exception e) {
			System.out.println("Greska!");
		}

		// datum valute
		DateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
		Date datumV = null;

		try {
			datumV = dateFormat1.parse(datumValute);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		AnalitikaIzvoda analitikaIzvoda = AnalitikaIzvoda.findById(id);
		analitikaIzvoda.duzNalogodavac = duzNalogodavac;
		analitikaIzvoda.svrhaPlacanja = svrhaPlacanja;
		analitikaIzvoda.povjerPrimalac = povjerPrimalac;
		analitikaIzvoda.datumPrijema = datumP;
		analitikaIzvoda.datumValute = datumV;
		analitikaIzvoda.racunDuznika = racunDuznika;
		analitikaIzvoda.modelZaduzenja = modelZaduzenja;
		analitikaIzvoda.pozNaBrojZaduzenja = pozNaBrojZaduzenja;
		analitikaIzvoda.racunPovjerioca = racunPovjerioca;
		analitikaIzvoda.modelOdobrenja = modelOdobrenja;
		analitikaIzvoda.pozNaBrojOdobrenja = pozNaBrojOdobrenja;
		analitikaIzvoda.hitno = hitno;
		analitikaIzvoda.iznos = iznos;
		analitikaIzvoda.tipGreske = tipGreske;
		analitikaIzvoda.status = status;
		
		analitikaIzvoda.save();
		show("edit");
	}
	
	public static void filter(String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			String datumPrijema, String datumValute, String racunDuznika, String modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, String modelOdobrenja, String pozNaBrojOdobrenja, String hitno, String iznos,
			String tipGreske, String status){
		
		List<AnalitikaIzvoda> analitikeIzvoda = AnalitikaIzvoda.find("byBrojStavkeLikeAndDuzNalogodavacLikeAndSvrhaPlacanjaLike"
				+ "AndPovjerPrimalacLikeAndRacunDuznikaLikeAndModelZaduzenjaLikeAndPozNaBrojZaduzenjaLikeAndRacunPovjeriocaLike"
				+ "AndModelOdobrenjaLikeAndPozNaBrojOdobrenjaLikeAndIznosLikeAndTipGreskeLikeAndStatusLike", 
				"%" + duzNalogodavac + "%", "%" + svrhaPlacanja + "%", "%" + povjerPrimalac + "%","%" + racunDuznika + "%", 
				"%" + modelZaduzenja + "%", "%" + pozNaBrojZaduzenja + "%", "%" + racunPovjerioca + "%","%" + modelOdobrenja + "%",
				"%" + pozNaBrojOdobrenja + "%", "%" + iznos + "%", "%" + tipGreske + "%", "%" + status + "%").fetch();
		
		String mode = "edit";
		renderTemplate("AnalitikeIzvoda/show.html", analitikeIzvoda, mode);
	}
	
	public static void delete(Long id){
		AnalitikaIzvoda analitikaIzvoda = AnalitikaIzvoda.findById(id);
		analitikaIzvoda.delete();
		show("edit");
	}
	
	
	//ucitava sve analitike
	public static void loadAll(){
	
		try {
			File myFile = new File("./nalozi/analitike_izvoda.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(myFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("analitika_izvoda");
			System.out.println(nList.getLength());
			
			for(int i=0; i<nList.getLength(); i++)
			{
				Node node = nList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE){
					
					Element element = (Element) node;
					
					String duznik = element.getElementsByTagName("duznik").item(0).getTextContent();
					String svrhaPlacanja = element.getElementsByTagName("svhra_placanja").item(0).getTextContent();
					String primalac = element.getElementsByTagName("primalac").item(0).getTextContent();
					
					String datumPrijemaA = element.getElementsByTagName("datum_prijema").item(0).getTextContent();
					Date datumPrijema = new SimpleDateFormat("dd.MM.yyyy").parse(datumPrijemaA);
					
					String datumValuteE = element.getElementsByTagName("datum_valute").item(0).getTextContent();
					Date datumValute = new SimpleDateFormat("dd.MM.yyyy").parse(datumValuteE);
					
					String racunDuznika = element.getElementsByTagName("racun_duznika").item(0).getTextContent();
					
					String modelZaduzenjaA = element.getElementsByTagName("model_zaduzenja").item(0).getTextContent();
					Integer modelZaduzenja = Integer.parseInt(modelZaduzenjaA);
					
					String pozNaBrojZaduzenja = element.getElementsByTagName("poz_na_broj_zaduzenja").item(0).getTextContent();
					String racunPrimaoca = element.getElementsByTagName("racun_povjerioca").item(0).getTextContent();
					
					String modelOdobrenjaA = element.getElementsByTagName("model_odobrenja").item(0).getTextContent();
					Integer modelOdobrenja = Integer.parseInt(modelOdobrenjaA);
					
					String pozNaBrojOdobrenja = element.getElementsByTagName("poz_na_broj_odobrenja").item(0).getTextContent();
					
					String hitnoO = element.getElementsByTagName("hitno").item(0).getTextContent();
					Boolean hitno = Boolean.parseBoolean(hitnoO);
					
					String iznosS = element.getElementsByTagName("iznos").item(0).getTextContent();
					Long iznos = Long.parseLong(iznosS);
					
					String tipGreskeE = element.getElementsByTagName("tip_greske").item(0).getTextContent();
					Integer tipGreske  = Integer.parseInt(tipGreskeE);
					
					String status = element.getElementsByTagName("status").item(0).getTextContent();
					
					
					AnalitikaIzvoda analitikaIzvoda = new AnalitikaIzvoda(duznik, svrhaPlacanja, primalac, datumPrijema,
							datumValute, racunDuznika, modelZaduzenja, pozNaBrojZaduzenja, racunPrimaoca, modelOdobrenja, 
							pozNaBrojOdobrenja, hitno, iznos, tipGreske, status);
					
					System.out.println(analitikaIzvoda.duzNalogodavac);
					
					analitikaIzvoda.save();
				}	
			}
			
			show("edit");
			
		} catch (Exception e) {
			// TODO: handle exception
			show("edit");
		}
		
	}
	
	//ucitavanje analitike iz fajla
	public static void load(String myFile) throws ParserConfigurationException, SAXException, IOException, ParseException, FileNotFoundException{
		
		try {
			//File fXmlFile = new File("123.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				
			org.w3c.dom.Document document = documentBuilder.parse(myFile);
			//org.w3c.dom.Document document = documentBuilder.parse("./nalozi/analitika_izvoda.xml");
			Element root = document.getDocumentElement();
			
			String duzNalogodavac = XPath.selectText("duznik", root);
			String svrhaPlacanja = XPath.selectText("svhra_placanja", root);
			String povjerPrimalac = XPath.selectText("primalac", root);
			
			String datumPrijemaA = XPath.selectText("datum_prijema", root);
			Date datumPrijema = new SimpleDateFormat("dd.MM.yyyy").parse(datumPrijemaA);
			
			String datumValuteE = XPath.selectText("datum_valute", root);
			Date datumValute = new SimpleDateFormat("dd.MM.yyyy").parse(datumValuteE);
			
			String racunDuznika = XPath.selectText("racun_duznika", root);
			Integer modelZaduzenja = Integer.parseInt(XPath.selectText("model_zaduzenja", root));
			String pozNaBrojZaduzenja = XPath.selectText("poz_na_broj_zaduzenja", root);
			String racunPovjerioca = XPath.selectText("racun_primaoca", root);
			Integer modelOdobrenja = Integer.parseInt(XPath.selectText("model_odobrenja", root));
			String pozNaBrojOdobrenja = XPath.selectText("poz_na_broj_odobrenja", root);
			
			Boolean hitno = Boolean.parseBoolean(XPath.selectText("hitno", root));
			Long iznos = Long.parseLong(XPath.selectText("iznos", root));
			Integer tipGreske  = Integer.parseInt(XPath.selectText("tip_greske", root));
			String status = XPath.selectText("status", root);
			
			AnalitikaIzvoda analitikaIzvoda = new AnalitikaIzvoda(duzNalogodavac, 
					svrhaPlacanja, povjerPrimalac, datumPrijema, datumValute, racunDuznika, 
					modelZaduzenja, pozNaBrojZaduzenja, racunPovjerioca, modelOdobrenja, 
					pozNaBrojOdobrenja, hitno, iznos, tipGreske, status);
		 
			
			analitikaIzvoda.save();
			//renderTemplate("AnalitikeIzvoda/show.html", analitikaIzvoda);
			show("edit");
			
		} catch (Exception e) {
			// TODO: handle exception
			show("");
		}
	}

	//generisanje naloga za medjubankarski prenos
	public static void genNaloga(Long analitikaIzvoda_id){
		
		AnalitikaIzvoda analitikaIzvoda = AnalitikaIzvoda.findById(analitikaIzvoda_id);
		
		//RTGS
		if(analitikaIzvoda.iznos > 250000 || analitikaIzvoda.hitno.equals(true)){
			
			MedjubankarskiPrenos medjubankarskiPrenos = new MedjubankarskiPrenos(analitikaIzvoda.datumPrijema, 
					analitikaIzvoda.racunDuznika, analitikaIzvoda.racunPovjerioca, analitikaIzvoda.iznos, "MT103 (RTGS)");
				
				medjubankarskiPrenos.save();
				StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenos);
				stavkaKliringa.save();
		}
		
		//kliring
		else {
			MedjubankarskiPrenos medjubankarskiPrenos = new MedjubankarskiPrenos(analitikaIzvoda.datumPrijema, 
					analitikaIzvoda.racunDuznika, analitikaIzvoda.racunPovjerioca, analitikaIzvoda.iznos, "MT102 (KLIRING)");
			
			medjubankarskiPrenos.save();
			StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenos);
			stavkaKliringa.save();
		}
		
		show("");
	}
	
	//export naloga u .xml fajl
	public static void expNaloga(Long analitikaIzvoda_id){
		
		AnalitikaIzvoda analitikaIzvoda = AnalitikaIzvoda.findById(analitikaIzvoda_id);
		
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			//root
			org.w3c.dom.Document document = documentBuilder.newDocument();
			
			Element root = document.createElement("analitika_izvoda");
			document.appendChild(root);
			
			Element duznik = document.createElement("duznik");
			duznik.setTextContent(analitikaIzvoda.duzNalogodavac);
			root.appendChild(duznik);
			
			Element svrhaPlacanja = document.createElement("svhra_placanja");
			svrhaPlacanja.setTextContent(analitikaIzvoda.svrhaPlacanja);
			root.appendChild(svrhaPlacanja);
			
			Element primalac = document.createElement("primalac");
			primalac.setTextContent(analitikaIzvoda.povjerPrimalac);
			root.appendChild(primalac);
			
			Element datumPrijema = document.createElement("datum_prijema");
			String datumPrijemaA = new SimpleDateFormat("dd.MM.yyyy").format(analitikaIzvoda.datumPrijema);
			datumPrijema.setTextContent(datumPrijemaA);
			root.appendChild(datumPrijema);
			
			Element datumValute = document.createElement("datum_valute");
			String datumValuteE = new SimpleDateFormat("dd.MM.yyyy").format(analitikaIzvoda.datumValute);
			datumValute.setTextContent(datumValuteE);
			root.appendChild(datumValute);
			
			Element racunDuznika = document.createElement("racun_duznika");
			racunDuznika.setTextContent(analitikaIzvoda.racunDuznika);
			root.appendChild(racunDuznika);
			
			Element modelZaduzenja = document.createElement("model_zaduzenja");
			modelZaduzenja.setTextContent(analitikaIzvoda.modelZaduzenja.toString());
			root.appendChild(modelZaduzenja);
			
			Element pozNaBrojZaduzenja = document.createElement("poz_na_broj_zaduzenja");
			pozNaBrojZaduzenja.setTextContent(analitikaIzvoda.pozNaBrojZaduzenja);
			root.appendChild(pozNaBrojZaduzenja);
			
			Element racunPrimaoca = document.createElement("racun_primaoca");
			racunPrimaoca.setTextContent(analitikaIzvoda.racunPovjerioca);
			root.appendChild(racunPrimaoca);
			
			Element modelOdobrenja = document.createElement("model_odobrenja");
			modelOdobrenja.setTextContent(analitikaIzvoda.modelOdobrenja.toString());
			root.appendChild(modelOdobrenja);
			
			Element pozNaBrojOdobrenja = document.createElement("poz_na_broj_odobrenja");
			pozNaBrojOdobrenja.setTextContent(analitikaIzvoda.pozNaBrojOdobrenja);
			root.appendChild(pozNaBrojOdobrenja);
			
			Element hitno = document.createElement("hitno");
			hitno.setTextContent(analitikaIzvoda.hitno.toString());
			root.appendChild(hitno);
			
			Element iznos = document.createElement("iznos");
			iznos.setTextContent(analitikaIzvoda.iznos.toString());
			root.appendChild(iznos);
			
			Element tipGreske = document.createElement("tip_greske");
			tipGreske.setTextContent(analitikaIzvoda.tipGreske.toString());
			root.appendChild(tipGreske);
			
			Element status = document.createElement("status");
			status.setTextContent(analitikaIzvoda.status);
			root.appendChild(status);
			
			//upisivanje u xml fajl
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File("./nalozi/analitika_export.xml"));
			
			transformer.transform(domSource, streamResult);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		show("");
	}
	
	
//	public static void loadFile(String myFile) throws ParserConfigurationException, SAXException, IOException, ParseException{
//	//load("C:/Users/Lemur/Desktop/analitika_izvoda.xml");
//	load(myFile);
//	renderTemplate("AnalitikeIzvoda/show.html");
//}

}
