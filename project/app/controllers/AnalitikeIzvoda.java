package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.example.instrumenti_placanja.InstrumentiPlacanja;
import org.xml.sax.SAXException;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.poslovna_obrada.Obrada;
import controllers.session.KonstanteSesije;

//import com.sun.java.util.jar.pack.Package.File;

import models.AnalitikaIzvoda;
import models.Banka;
import models.Klijent;
import models.Racun;
import models.Valuta;
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
				+ "AndModelOdobrenjaLikeAndPozNaBrojOdobrenjaLikeAndIznosLikeAndTipGreskeLikeAndStatusLikeAndSmjerLike", 
				"%" + duzNalogodavac + "%", "%" + svrhaPlacanja + "%", "%" + povjerPrimalac + "%",
				"%" + racunDuznika + "%", "%" + modelZaduzenja + "%", "%" + pozNaBrojZaduzenja + "%", "%" + racunPovjerioca + "%",
				"%" + modelOdobrenja + "%", "%" + pozNaBrojOdobrenja + "%", "%" + iznos + "%",
				"%" + tipGreske + "%", "%" + status + "%").fetch();
		
		String mode = "edit";
		renderTemplate("AnalitikeIzvoda/show.html", analitikeIzvoda, mode);
	}	
	
	public static void load(String myFile) throws ParserConfigurationException, SAXException, IOException, ParseException, FileNotFoundException{
		Obrada obrada = new Obrada();
		obrada.obradi(new File("data.xml"));
		String izvjestajObrade = obrada.izvjestaj();
		String linkNaAnalitike = "/AnalitikeIzvoda/showDefault";
		render("PoslovnaObradaIzvjestaj.html", izvjestajObrade, linkNaAnalitike);
	}
	
}
