package controllers;

import java.sql.Date;
import java.util.List;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import models.Banka;
import models.Klijent;
import models.Racun;
import models.Valuta;
import play.mvc.Controller;

/*
 * Stevan radi
 */
public class Racuni extends Controller {

	public Racuni() {
		//prazno
	}
	
	public static void showDefault() {
		show(Konstante.KONF_IZMJENA);
	}
	
	public static void show(String mode) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Racun> racuni = Racun.findAll();
			render(mode, racuni);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(String brojRacuna, Long racun_klijent_id, Long racun_valuta_id) {
		validation.required(brojRacuna);
		
		if(brojRacuna.matches("^[0-9]{3}-[0-9]{13}-[0-9]{2}$")) {
			long brojRacuna18 = Long.parseLong(brojRacuna.replace("-", ""));
			long kontrolneCifre = brojRacuna18 % 100;
			if(98 - ((brojRacuna18-kontrolneCifre) % 97) == kontrolneCifre) {
				java.util.Date sada = new java.util.Date();
				Date datumOtvaranja = new Date(sada.getTime());
				Racun racun = new Racun(brojRacuna18, datumOtvaranja, true);
				try {
					Banka banka = Banka.find("bySifraBanke", brojRacuna18 / 1000000000000000L).first();
					if(banka!=null) {
						Klijent klijent = Klijent.findById(racun_klijent_id);
						if(klijent!=null) {
							Valuta valuta =Valuta.findById(racun_valuta_id);
							if(valuta!=null) {
								racun.banka = banka;
								racun.save();
								show(Konstante.KONF_DODAVANJE);
							} else {
								//valuta je null
							}
						} else {
							//klijent je null
						}
					} else {
						//banka je null
					}
				} catch(Exception e) {
					//greska vjerovatno jer broj racuna nije jedinstven
					validation.addError("", "");
				}
			} else {
				validation.addError("", "");
			}
		} else {
			validation.addError("brojRacuna", "");
		}
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_DODAVANJE);
		}
	}

	public static void delete(Long id) {
		Racun racun = Racun.findById(id);
		if(racun!=null) {
			racun.delete();
			show(Konstante.KONF_IZMJENA);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_RACUN, id));
		}
	}
	
	public static void filter(String brojRacuna, Date datumOtvaranja, boolean vazeci) {
		List<Racun> racuni = Racun.find("byBrojRacunaAndDatumOtvaranjaAndVazeci", brojRacuna, datumOtvaranja, vazeci).fetch();
		renderTemplate("Racuni/show.html", Konstante.KONF_IZMJENA, racuni);
	}
	
	public static void nextForm(String klijent_id) {
		List<Racun> racuni = Racun.find("byKlijent_id", klijent_id).fetch();
		renderTemplate("Racuni/show.html", Konstante.KONF_IZMJENA, racuni);
	}
}
