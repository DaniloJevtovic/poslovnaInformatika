package controllers;

import java.util.List;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.validation.ValidacijaKlijenta;
import controllers.validation.Validacije;
import models.Klijent;
import models.constants.KonstanteKlijenta;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

/*
 * Stevan radi
 */
public class Klijenti extends Controller {

	public Klijenti() {
		//prazno
	}
	
	public static void showDefault() {
		show(Konstante.KONF_IZMJENA, null);
	}
	
	public static void show(String mode, Long highlightedId) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Klijent> klijenti = Klijent.findAll();
			render(mode, klijenti);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(String tipKlijenta, String nazivKlijenta, String imeKlijenta, String przKlijenta,
			String telKlijenta, String adrKlijenta, String webKlijenta, String emailKlijenta,
			String faksKlijenta) {
		Klijent klijent = new Klijent(
				tipKlijenta,
				nazivKlijenta,
				imeKlijenta,
				przKlijenta,
				telKlijenta,
				adrKlijenta,
				webKlijenta,
				emailKlijenta,
				faksKlijenta);
		//validacije tipa
		validation.required(tipKlijenta);
		validation.match(tipKlijenta, ValidacijaKlijenta.TIP_REGEX);
		//validacije naziva
		if(tipKlijenta.equals(ValidacijaKlijenta.PRAVNO_LICE)) {
			//naziv je obavezan za pravno lice
			validation.required(nazivKlijenta);
		}
		validation.maxSize(nazivKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_NAZIV);
		validation.match(nazivKlijenta, ValidacijaKlijenta.NAZIV_REGEX);
		//validacije imena
		validation.required(imeKlijenta);
		validation.maxSize(imeKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_IME);
		validation.match(imeKlijenta, ValidacijaKlijenta.IME_PREZIME_REGEX);
		//validacije prezimena
		validation.required(przKlijenta);
		validation.maxSize(przKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_PREZIME);
		validation.match(przKlijenta, ValidacijaKlijenta.IME_PREZIME_REGEX);
		//validacije broja telefona
		validation.required(telKlijenta);
		validation.maxSize(telKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_TELEFON);
		validation.phone(telKlijenta);
		//validacije adrese
		validation.required(adrKlijenta);
		validation.maxSize(adrKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_ADRESA);
		//validacije web adrese
		validation.maxSize(webKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_WEB);		
		validation.url(webKlijenta);
		//validacije emaila
		validation.maxSize(emailKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_EMAIL);
		validation.email(emailKlijenta);
		//validacije faksa
		validation.maxSize(faksKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_TELEFON);
		validation.phone(faksKlijenta);
		
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_DODAVANJE, null);
		} else {
			klijent.save();
			show(Konstante.KONF_DODAVANJE, klijent.id);
		}
	}
	
	public static void edit(Long id, String tipKlijenta, String nazivKlijenta, String imeKlijenta, String przKlijenta,
			String telKlijenta, String adrKlijenta, String webKlijenta, String emailKlijenta,
			String faksKlijenta) {
		//validacije tipa
		validation.required(tipKlijenta);
		validation.match(tipKlijenta, ValidacijaKlijenta.TIP_REGEX);
		//validacije naziva
		if(tipKlijenta.equals(ValidacijaKlijenta.PRAVNO_LICE)) {
			//naziv je obavezan za pravno lice
			validation.required(nazivKlijenta);
		}
		validation.maxSize(nazivKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_NAZIV);
		validation.match(nazivKlijenta, ValidacijaKlijenta.NAZIV_REGEX);
		//validacije imena
		validation.required(imeKlijenta);
		validation.maxSize(imeKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_IME);
		validation.match(imeKlijenta, ValidacijaKlijenta.IME_PREZIME_REGEX);
		//validacije prezimena
		validation.required(przKlijenta);
		validation.maxSize(przKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_PREZIME);
		validation.match(przKlijenta, ValidacijaKlijenta.IME_PREZIME_REGEX);
		//validacije broja telefona
		validation.required(telKlijenta);
		validation.maxSize(telKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_TELEFON);
		validation.phone(telKlijenta);
		//validacije adrese
		validation.required(adrKlijenta);
		validation.maxSize(adrKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_ADRESA);
		//validacije web adrese
		validation.maxSize(webKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_WEB);		
		validation.url(webKlijenta);
		//validacije emaila
		validation.maxSize(emailKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_EMAIL);
		validation.email(emailKlijenta);
		//validacije faksa
		validation.maxSize(faksKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_TELEFON);
		validation.phone(faksKlijenta);
		
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_IZMJENA, id);
		} else {
			Klijent klijent = Klijent.findById(id);
			if(klijent!=null) {
				klijent.tipKlijenta = tipKlijenta;
				klijent.nazivKlijenta = nazivKlijenta;
				klijent.imeKlijenta = imeKlijenta;
				klijent.przKlijenta = przKlijenta;
				klijent.telKlijenta = telKlijenta;
				klijent.adrKlijenta = adrKlijenta;
				klijent.webKlijenta = webKlijenta;
				klijent.emailKlijenta = emailKlijenta;
				klijent.faksKlijenta = faksKlijenta;
				klijent.save();
				show(Konstante.KONF_IZMJENA, id);
			} else {
				notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KLIJENT, id));
			}
		}
	}
	
	public static void delete(Long id) {
		Klijent klijent = Klijent.findById(id);
		if(klijent!=null) {
			klijent.delete();
			Klijent prviVeci = Klijent.find("byIdGreater", id).first();
			if(prviVeci!=null) {
				show(Konstante.KONF_IZMJENA, prviVeci.id);
				return;
			}
			Klijent prviManji = Klijent.find("byIdLower", id).first();
			if(prviManji!=null) {
				show(Konstante.KONF_IZMJENA, prviManji.id);
				return;
			}
			show(Konstante.KONF_IZMJENA, null);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KLIJENT, id));
		}
	}
	
	public static void filter(String tipKlijenta, String nazivKlijenta, String imeKlijenta, String przKlijenta,
			String telKlijenta, String adrKlijenta, String webKlijenta, String emailKlijenta,
			String faksKlijenta) {
		//"byTipKlijentaLikeAndNazivKlijentaLike"
		JPAQuery q = Klijent.find(
				"byTipKlijentaLikeAnd" + 
				"NazivKlijentaLikeAnd" + 
				"ImeKlijentaLikeAnd" + 
				"PrzKlijentaLikeAnd" + 
				"TelKlijentaLikeAnd" + 
				"AdrKlijentaLikeAnd" + 
				"WebKlijentaLikeAnd" + 
				"EmailKlijentaLikeAnd"+
				"FaksKlijentaLike", 
				PomocneOperacije.dataToQueryParam(tipKlijenta),
				PomocneOperacije.dataToQueryParam(nazivKlijenta),
				PomocneOperacije.dataToQueryParam(imeKlijenta),
				PomocneOperacije.dataToQueryParam(przKlijenta),
				PomocneOperacije.dataToQueryParam(telKlijenta),
				PomocneOperacije.dataToQueryParam(adrKlijenta),
				PomocneOperacije.dataToQueryParam(webKlijenta),
				PomocneOperacije.dataToQueryParam(emailKlijenta),
				PomocneOperacije.dataToQueryParam(faksKlijenta)
				);
		List<Klijent> klijenti  = q.fetch();
		renderTemplate("Klijenti/show.html", Konstante.KONF_IZMJENA, klijenti);
	}
}
