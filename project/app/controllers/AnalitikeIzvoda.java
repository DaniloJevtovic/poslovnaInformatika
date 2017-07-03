package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.poslovna_obrada.Obrada;
import controllers.session.KonstanteSesije;

//import com.sun.java.util.jar.pack.Package.File;

import models.AnalitikaIzvoda;
import models.MedjubankarskiPrenos;
import models.StavkaKliringa;
import play.mvc.Controller;


/*
 * Radi Danilo
 */
public class AnalitikeIzvoda extends Controller{

	public AnalitikeIzvoda() {
		// TODO Auto-generated constructor stub
	}
	
	public static void showDefault() {
		KonstanteSesije.clearFlashConfig(flash);
		if(!KonstanteSesije.filterIsValid(flash, Konstante.IME_ENTITETA_ANALITIKA_IZVODA, KonstanteSesije.FILTRI_ANALITIKE_IZVODA)) {
			KonstanteSesije.clearFlashFilter(flash);
		}
		flash.keep();
		show(Konstante.KONF_DODAVANJE, "");
	}
	
	public static void show(String mode, String highlightedId){
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<AnalitikaIzvoda> analitikeIzvoda;
			if(Konstante.IME_ENTITETA_ANALITIKA_IZVODA.equals(flash.get(KonstanteSesije.TARGET_ENTITY))) {
				String query = "";
				switch(flash.get(KonstanteSesije.FILTER_ENTITY)) {
				case Konstante.IME_ENTITETA_DNEVNO_STANJE_U_KORIST:
					query = "select ai from AnalitikaIzvoda ai where ai.dnevnoStanjeUKorist.id = ?";
					break;
				case Konstante.IME_ENTITETA_DNEVNO_STANJE_NA_TERET:
					query = "select ai from AnalitikaIzvoda ai where ai.dnevnoStanjeNaTeret.id = ?";
					break;
				case Konstante.IME_ENTITETA_VALUTA:
					query = "select ai from AnalitikaIzvoda ai where ai.valuta.id = ?";
					break;
				case Konstante.IME_ENTITETA_VRSTA_PLACANJA:
					query = "select ai from AnalitikaIzvoda ai where ai.vrstaPlacanja.id = ?";
					break;
				case Konstante.IME_ENTITETA_NASELJENO_MESTO:
					query = "select ai from AnalitikaIzvoda ai where ai.naseljenoMjesto.id = ?";
					break;
				}
				long filterId = Long.parseLong(flash.get(KonstanteSesije.FILTER_ID)); 
				analitikeIzvoda = AnalitikaIzvoda.find(query, filterId).fetch();
			} else {
				analitikeIzvoda = AnalitikaIzvoda.findAll();
			}
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(analitikeIzvoda);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	

	public static void filter(String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			String datumPrijema, String datumValute, String racunDuznika, String modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, String modelOdobrenja, String pozNaBrojOdobrenja, String hitno, String iznos,
			String tipGreske, String status){
		
		List<AnalitikaIzvoda> analitikeIzvoda = AnalitikaIzvoda.find("byDuzNalogodavacLikeAndSvrhaPlacanjaLike"
				+ "AndPovjerPrimalacLikeAndRacunDuznikaLikeAndModelZaduzenjaLikeAndPozNaBrojZaduzenjaLikeAndRacunPovjeriocaLike"
				+ "AndModelOdobrenjaLikeAndPozNaBrojOdobrenjaLikeAndIznosLikeAndTipGreskeLikeAndStatusLike", 
				"%" + duzNalogodavac + "%", "%" + svrhaPlacanja + "%", "%" + povjerPrimalac + "%","%" + racunDuznika + "%", 
				"%" + modelZaduzenja + "%", "%" + pozNaBrojZaduzenja + "%", "%" + racunPovjerioca + "%","%" + modelOdobrenja + "%",
				"%" + pozNaBrojOdobrenja + "%", "%" + iznos + "%", "%" + tipGreske + "%", "%" + status + "%").fetch();
		
		String mode = "edit";
		renderTemplate("AnalitikeIzvoda/show.html", analitikeIzvoda, mode);
	}	
	
	
	
	//ucitavanje analitike iz fajla
	public static void load(String myFile) throws ParserConfigurationException, SAXException, IOException, ParseException, FileNotFoundException{

		Obrada obrada = new Obrada();
		obrada.obradi(new File("data.xml"));
		String izvjestajObrade = obrada.izvjestaj();
		String linkNaAnalitike = "/AnalitikeIzvoda/showDefault";
		render("PoslovnaObradaIzvjestaj.html", izvjestajObrade, linkNaAnalitike);
		
	}

	//generisanje naloga za medjubankarski prenos
	public static void genNaloga(Long analitikaIzvoda_id){
		
		AnalitikaIzvoda analitikaIzvoda = AnalitikaIzvoda.findById(analitikaIzvoda_id);
		
		//RTGS
		if(analitikaIzvoda.iznos.compareTo(new BigDecimal(250000)) > 0 || analitikaIzvoda.hitno.equals(true)){
			
			MedjubankarskiPrenos medjubankarskiPrenos = new MedjubankarskiPrenos(analitikaIzvoda.datumPrijema, 
					analitikaIzvoda.racunDuznika, analitikaIzvoda.racunPovjerioca, analitikaIzvoda.iznos.longValue(), "MT103 (RTGS)");
				
				medjubankarskiPrenos.save();
				StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenos);
				stavkaKliringa.save();
		}
		
		//kliring
		else {
			MedjubankarskiPrenos medjubankarskiPrenos = new MedjubankarskiPrenos(analitikaIzvoda.datumPrijema, 
					analitikaIzvoda.racunDuznika, analitikaIzvoda.racunPovjerioca, analitikaIzvoda.iznos.longValue(), "MT102 (KLIRING)");
			
			medjubankarskiPrenos.save();
			StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenos);
			stavkaKliringa.save();
		}
		
		showDefault();
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
		
		
		showDefault();
	}
	
}
