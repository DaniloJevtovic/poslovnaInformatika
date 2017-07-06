package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.session.KonstanteSesije;
import models.AnalitikaIzvoda;
import models.DnevnoStanjeRacuna;
import models.Racun;
import play.mvc.Controller;

/*
 * Danilo radi
 */
public class DnevnaStanjaRacuna extends Controller {

	public DnevnaStanjaRacuna() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void showDefault() {
		KonstanteSesije.clearFlashConfig(flash);
		if(!KonstanteSesije.filterIsValid(flash, Konstante.IME_ENTITETA_DNEVNO_STANJE_RACUNA, KonstanteSesije.FILTRI_DNEVNOG_STANJA_RACUNA)) {
			KonstanteSesije.clearFlashFilter(flash);
		}
		flash.keep();
		show(Konstante.KONF_IZMJENA, "");
	}
	
	public static void show(String mode, String highlightedId){
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<DnevnoStanjeRacuna> dnevnaStanjaRacuna;
			if(Konstante.IME_ENTITETA_ANALITIKA_IZVODA.equals(flash.get(KonstanteSesije.TARGET_ENTITY))) {
				String query = "";
				switch(flash.get(KonstanteSesije.FILTER_ENTITY)) {
				case Konstante.IME_ENTITETA_RACUN:
					query = "select dsr from DnevnoStanjeRacuna dsr where dsr.racun.id = ?";
					break;
				}
				long filterId = Long.parseLong(flash.get(KonstanteSesije.FILTER_ID)); 
				dnevnaStanjaRacuna = DnevnoStanjeRacuna.find(query, filterId).fetch();
			} else {
				dnevnaStanjaRacuna = DnevnoStanjeRacuna.findAll();
			}
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(dnevnaStanjaRacuna);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void filter(String brojIzvoda, String racun, String datumPrometa, String predhodnoStanje, String prometUKorist,
			String prometNaTeret, String novoStanje){
		List<DnevnoStanjeRacuna> dnevnaStanjaRacuna =DnevnoStanjeRacuna.find("byBrojIzvodaLikeAndRacunLikeAndPredhodnoStanjeLikeAndPrometUKoristLikeAndPrometNaTeretLikeAndNovoStanjeLike", 
				"%" + brojIzvoda + "%", "%" + racun + "%", "%" + predhodnoStanje + "%", "%" + prometUKorist + "%", "%" + prometNaTeret + "%", "%" + novoStanje + "%").fetch();
		String mode = "edit";
		renderTemplate("DnevnaStanjaRacuna/show.html", dnevnaStanjaRacuna, mode);
	}
	

}
