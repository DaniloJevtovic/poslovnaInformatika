package controllers;

import java.util.List;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaKlijenta;
import models.Klijent;
import models.constants.KonstanteKlijenta;
import play.db.jpa.Model;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Before;
import play.mvc.Controller;

/*
 * Stevan radi
 */
public class Klijenti extends Controller {

	public Klijenti() {
		//prazno
	}
	
	public static void showDefault() {
		show(Konstante.KONF_IZMJENA, "");
	}
	
	public static void show(String mode, String highlightedId) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Klijent> klijenti = Klijent.findAll();
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(klijenti);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(String tipKlijenta, String nazivKlijenta, Integer pibKlijenta, String imeKlijenta, 
			String przKlijenta, String telKlijenta, String adrKlijenta, String webKlijenta, String emailKlijenta,
			String faksKlijenta) {
		ValidacijaKlijenta.validate(validation, tipKlijenta, nazivKlijenta,
				pibKlijenta, imeKlijenta, przKlijenta, telKlijenta, adrKlijenta,
				webKlijenta, emailKlijenta, faksKlijenta);
		if(validation.hasErrors()) {
			show(Konstante.KONF_DODAVANJE, "");
		} else {
			Klijent klijent = new Klijent(tipKlijenta, nazivKlijenta, pibKlijenta,
					imeKlijenta, przKlijenta, telKlijenta, adrKlijenta, webKlijenta,
					emailKlijenta, faksKlijenta);
			klijent.save();
			show(Konstante.KONF_DODAVANJE, klijent.id.toString());
		}
	}
	
	public static void edit(Long id, String tipKlijenta, String nazivKlijenta, Integer pibKlijenta, String imeKlijenta,
			String przKlijenta, String telKlijenta, String adrKlijenta, String webKlijenta, String emailKlijenta,
			String faksKlijenta) {
		ValidacijaKlijenta.validate(validation, tipKlijenta, nazivKlijenta,
				pibKlijenta, imeKlijenta, przKlijenta, telKlijenta, adrKlijenta,
				webKlijenta, emailKlijenta, faksKlijenta);
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_IZMJENA, id.toString());
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
				show(Konstante.KONF_IZMJENA, id.toString());
			} else {
				notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KLIJENT, id));
			}
		}
	}
	
	public static void delete(Long id) {
		Klijent klijent = Klijent.findById(id);
		if(klijent!=null) {
			klijent.delete();
			String highlightedId = "";
			Klijent prviVeci = Klijent.find("byIdGreaterThan", id).first();
			if(prviVeci!=null) {
				highlightedId = prviVeci.id.toString();
			} else {
				Klijent prviManji = Klijent.find("select k from Klijent k where k.id < ? order by id desc", id).first();
				if(prviManji!=null) {
					highlightedId = prviManji.id.toString();
				}
			}
			show(flash.get(KonstanteSesije.MODE), highlightedId);
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
