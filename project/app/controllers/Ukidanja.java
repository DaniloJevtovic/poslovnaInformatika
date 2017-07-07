package controllers;

import java.sql.Date;
import java.util.List;









import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.session.KonstanteSesije;
import models.Banka;
import models.Drzava;
import models.Klijent;
import models.Racun;
import models.Ukidanje;
import models.Valuta;
import models.VrstaPlacanja;
import play.mvc.Controller;

/*
 * Nikola radi
 */
public class Ukidanja extends Controller {

	public Ukidanja() {
		// TODO Auto-generated constructor stub
	}
	public static void showDefault() {
		KonstanteSesije.clearFlashConfig(flash);
		if(!KonstanteSesije.filterIsValid(flash, Konstante.IME_ENTITETA_UKIDANJE, KonstanteSesije.FILTRI_UKIDANJA)) {
			KonstanteSesije.clearFlashFilter(flash);
		}
		flash.keep();
		show(Konstante.KONF_IZMJENA, "");
	}
	
	public static void show(String mode, String highlightedId) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Ukidanje> ukidanje;
			if(Konstante.IME_ENTITETA_UKIDANJE.equals(flash.get(KonstanteSesije.TARGET_ENTITY))) {
				String query = "";
				switch(flash.get(KonstanteSesije.FILTER_ENTITY)) {
				case Konstante.IME_ENTITETA_RACUN:
					query = "select u from Ukidanje u where u.racun.id = ?";
					break;
				}
				long filterId = Long.parseLong(flash.get(KonstanteSesije.FILTER_ID)); 
				ukidanje = Ukidanje.find(query, filterId).fetch();
			} else {
				ukidanje = Ukidanje.findAll();
			}
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(ukidanje);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}

	public static void filter(Long id,Date datumUkidanja, String sredSePrenNaRacun){
		List<Ukidanje> ukidanje = Ukidanje.find("bydatumUkidanjaLikeAndsredSePrenNaRacunLike", "%" + datumUkidanja + "%", "%" + sredSePrenNaRacun + "%").fetch();
		String mode = "edit";
		renderTemplate("Ukidanja/show.html", ukidanje, mode);
	}

}