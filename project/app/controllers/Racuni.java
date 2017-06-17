package controllers;

import java.sql.Date;
import java.util.List;
import java.util.function.BiConsumer;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaRacuna;
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
		show(Konstante.KONF_DODAVANJE, "");
	}
	
	public static void show(String mode, String highlightedId) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Racun> racuni = Racun.findAll();
			List<Klijent> klijenti = Klijent.find("select k from Klijent k order by k.tipKlijenta desc, k.nazivKlijenta, k.przKlijenta, k.imeKlijenta").fetch();
			List<Banka> banke = Banka.findAll();
			List<Valuta> valute = Valuta.find("select v from Valuta v order by v.zvanicnaSifra").fetch();
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(racuni, klijenti, banke, valute);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(String brojRacuna, Long klijent, Long banka, Long valuta) {
		ValidacijaRacuna.validate(validation, brojRacuna, klijent, banka, valuta);
		
		if(validation.hasErrors()) {
			validation.keep();
			/*validation.errorsMap().forEach(new BiConsumer<String, List<play.data.validation.Error>>() {

				@Override
				public void accept(String t, List<play.data.validation.Error> u) {
					System.out.println("Greska: " + t);
					System.out.println(u.get(0).message());
				}
				
			});*/
			show(Konstante.KONF_DODAVANJE, "");
		} else {
			String dijelovi[] = brojRacuna.split("-");
			int kontrolneCifre = Integer.parseInt(dijelovi[2]);
			long glavnina = Long.parseLong(dijelovi[0] + dijelovi[1] + dijelovi[2]) - kontrolneCifre;
			System.out.println("Glavnina: " + glavnina);
			System.out.println("Kontrolne cifre: " + kontrolneCifre);
			System.out.println("Glavnina mod 97: " + (glavnina % 97));
			System.out.println("Provjera: " + ( 98 - (glavnina%97)));
			if(kontrolneCifre != (98 - (glavnina % 97))) {
				validation.addError("brojRacuna", "Nije validan po modelu 97");
			}
			Klijent _klijent = Klijent.findById(klijent);
			if(_klijent==null) {
				validation.addError("klijent", "Nepostojeci klijent");
			}
			Banka _banka = Banka.findById(banka);
			if(_banka==null) {
				validation.addError("banka", "Nepostojeca banka");
			}
			if(!_banka.sifraBanke.equals(dijelovi[0])) {
				validation.addError("banka", "Sifra banke ne odgovara strukturi broja racuna");
			}
			Valuta _valuta = Valuta.findById(valuta);
			if(_valuta==null) {
				validation.addError("valuta", "Nepostojeca valuta");
			}
			if(validation.hasErrors()) {
				validation.keep();
				show(Konstante.KONF_DODAVANJE, "");
			} else {
				java.util.Date sada = new java.util.Date();
				Date datumOtvaranja = new Date(sada.getTime());
				Racun racun = new Racun(brojRacuna, datumOtvaranja, true);
				racun.valuta = _valuta;
				racun.banka = _banka;
				racun.klijent = _klijent;
				racun.save();
				show(Konstante.KONF_DODAVANJE, racun.id.toString());
			}
			
		}
	}

	public static void delete(Long id) {
		Racun racun = Racun.findById(id);
		if(racun!=null) {
			racun.delete();
			String highlightedId = "";
			Racun prviVeci = Racun.find("byIdGreaterThan", id).first();
			if(prviVeci!=null) {
				highlightedId = prviVeci.id.toString();
			} else {
				Racun prviManji = Racun.find("select r from Racun r where r.id < ? order by id desc", id).first();
				if(prviManji!=null) {
					highlightedId = prviManji.id.toString();
				}
			}
			show(flash.get(KonstanteSesije.MODE), highlightedId);
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
