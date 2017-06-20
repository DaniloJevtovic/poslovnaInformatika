package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.xml.internal.txw2.Document;

//import com.sun.java.util.jar.pack.Package.File;

import models.AnalitikaIzvoda;
import play.libs.XPath;
import play.mvc.Controller;

import java.io.*;
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
			mode = "edit";
		render(analitikeIzvoda, mode);
	}
	
	public static void create(Long brojStavke, String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
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

		AnalitikaIzvoda analitikaIzvoda = new AnalitikaIzvoda(brojStavke, duzNalogodavac, svrhaPlacanja, povjerPrimalac, 
				datumP, datumV, racunDuznika, modelZaduzenja, pozNaBrojZaduzenja, racunPovjerioca, modelOdobrenja,
				pozNaBrojOdobrenja, hitno, iznos, tipGreske, status);
		
		analitikaIzvoda.save();		
		show("add");
	}
	
	public static void edit(Long id, Long brojStavke, String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
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
		analitikaIzvoda.brojStavke = brojStavke;
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
	
	public static void filter(String brojStavke, String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			String datumPrijema, String datumValute, String racunDuznika, String modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, String modelOdobrenja, String pozNaBrojOdobrenja, String hitno, String iznos,
			String tipGreske, String status){
		
		List<AnalitikaIzvoda> analitikeIzvoda = AnalitikaIzvoda.find("byBrojStavkeLikeAndDuzNalogodavacLikeAndSvrhaPlacanjaLike"
				+ "AndPovjerPrimalacLikeAndRacunDuznikaLikeAndModelZaduzenjaLikeAndPozNaBrojZaduzenjaLikeAndRacunPovjeriocaLike"
				+ "AndModelOdobrenjaLikeAndPozNaBrojOdobrenjaLikeAndIznosLikeAndTipGreskeLikeAndStatusLike", 
				"%" + brojStavke + "%", "%" + duzNalogodavac + "%", "%" + svrhaPlacanja + "%", "%" + povjerPrimalac + "%",
				"%" + racunDuznika + "%", "%" + modelZaduzenja + "%", "%" + pozNaBrojZaduzenja + "%", "%" + racunPovjerioca + "%",
				"%" + modelOdobrenja + "%", "%" + pozNaBrojOdobrenja + "%", "%" + iznos + "%",
				"%" + tipGreske + "%", "%" + status + "%").fetch();
		
		String mode = "edit";
		renderTemplate("AnalitikeIzvoda/show.html", analitikeIzvoda, mode);
	}
	
	public static void delete(Long id){
		AnalitikaIzvoda analitikaIzvoda = AnalitikaIzvoda.findById(id);
		analitikaIzvoda.delete();
		show("edit");
	}
	
	
	public static void load(File file) throws ParserConfigurationException, SAXException, IOException, ParseException{
		
		//File fXmlFile = new File("123.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		//org.w3c.dom.Document document = documentBuilder.parse(file);
		org.w3c.dom.Document document = documentBuilder.parse("C:/Users/Lemur/Desktop/analitika_izvoda.xml");
		Element root = document.getDocumentElement();

		Long brojStavke = Long.parseLong(XPath.selectText("broj_stavke", root));
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
		String racunPovjerioca = XPath.selectText("racun_povjerioca", root);
		Integer modelOdobrenja = Integer.parseInt(XPath.selectText("model_odobrenja", root));
		String pozNaBrojOdobrenja = XPath.selectText("poz_na_br_odobrenja", root);
		
		Boolean hitno = Boolean.parseBoolean(XPath.selectText("hitno", root));
		Long iznos = Long.parseLong(XPath.selectText("iznos", root));
		Integer tipGreske  = Integer.parseInt(XPath.selectText("tip_greske", root));
		String status = XPath.selectText("status", root);
		

		
		AnalitikaIzvoda analitikaIzvoda = new AnalitikaIzvoda(brojStavke, duzNalogodavac, 
				svrhaPlacanja, povjerPrimalac, datumPrijema, datumValute, racunDuznika, 
				modelZaduzenja, pozNaBrojZaduzenja, racunPovjerioca, modelOdobrenja, 
				pozNaBrojOdobrenja, hitno, iznos, tipGreske, status);
		
		analitikaIzvoda.save();
		renderTemplate("AnalitikeIzvoda/show.html", analitikaIzvoda);
	}

}
