package controllers;

import java.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import models.*;
import play.mvc.Controller;

/*
 * Danilo radi
 */
public class MedjubankarskiPrenosi extends Controller {

	public MedjubankarskiPrenosi() {
		// TODO Auto-generated constructor stub
	}
	
	public static void show(String mode){
		List<MedjubankarskiPrenos> medjubankarskiPrenosi = MedjubankarskiPrenos.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(medjubankarskiPrenosi, mode);
	}
	
	public static void create(String datumKliringa, String swiftKodBankeDuz, String racBankeDuz,
			String swiftKodBankePovj, String racBankePovj, Long ukupanIznos, String vrstaPrenosa){
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datum = null;
		
		try {
			datum = dateFormat.parse(datumKliringa);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		MedjubankarskiPrenos medjubankarskiPrenos = new MedjubankarskiPrenos(datum, racBankeDuz, 
				racBankePovj, ukupanIznos, vrstaPrenosa);
		medjubankarskiPrenos.save();		
		show("add");
	}
	
	public static void edit(Long id, String datumKliringa, String racBankeDuz,
			String racBankePovj, Long ukupanIznos, String vrstaPrenosa){
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datum = null;
		
		try {
			datum = dateFormat.parse(datumKliringa);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		MedjubankarskiPrenos medjubankarskiPrenos = MedjubankarskiPrenos.findById(id);
		medjubankarskiPrenos.datumKliringa = datum;
		//medjubankarskiPrenos.swiftKodBankeDuz = swiftKodBankeDuz;
		medjubankarskiPrenos.racBankeDuz = racBankeDuz;
		//medjubankarskiPrenos.swiftKodBankePovj = racBankePovj;
		medjubankarskiPrenos.ukupanIznos = ukupanIznos;
		medjubankarskiPrenos.vrstaPrenosa = vrstaPrenosa;

		medjubankarskiPrenos.save();
		show("edit");
	}
	
	public static void filter(String datumKliringa, String racBankeDuz,
			String racBankePovj, String ukupanIznos, String vrstaPrenosa){
		
		List<MedjubankarskiPrenos> medjubankarskiPrenosi = MedjubankarskiPrenos.find("byDatumKliringaLikeAndRacBankeDuzLikeAndRacBankePovjLikeAndUkupanIznosLikeAndVrstaPrenosaLike",
				"%" + datumKliringa + "%", "%" + racBankeDuz + "%", "%" + racBankePovj + "%", 
				"%" + ukupanIznos + "%", "%" + vrstaPrenosa + "%").fetch();
		String mode = "edit";
		renderTemplate("MedjubankarskiPrenosi/show.html", medjubankarskiPrenosi, mode);
	}
	
	public static void delete(Long id){
		MedjubankarskiPrenos medjubankarskiPrenos = MedjubankarskiPrenos.findById(id);
		medjubankarskiPrenos.delete();
		show("edit");
	}
	
	
	public static void exportToXml(List<AnalitikaIzvoda>analitike, MedjubankarskiPrenos medjubankarskiPrenos){
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			//root element
			Document document = documentBuilder.newDocument();
			Element root = document.createElement("medjubankarski_prenosi");
			document.appendChild(root);
			
			Element vrstaPrenosa = document.createElement("MT" + medjubankarskiPrenos.vrstaPrenosa);	//rtgs 103 ili klirng 102
			
			Element datumPrenosa = document.createElement("datum_prenosa");
			datumPrenosa.setTextContent(medjubankarskiPrenos.datumKliringa.toString());
			vrstaPrenosa.appendChild(datumPrenosa);
			
			Element racunBanDuz = document.createElement("racun_banke_duznika");
			racunBanDuz.setTextContent(medjubankarskiPrenos.racBankeDuz);
			vrstaPrenosa.appendChild(racunBanDuz);
		
			Element ukupanIznos = document.createElement("ukupan_iznos");
			ukupanIznos.setTextContent(medjubankarskiPrenos.ukupanIznos.toString());
			vrstaPrenosa.appendChild(ukupanIznos);
			
			root.appendChild(vrstaPrenosa);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
