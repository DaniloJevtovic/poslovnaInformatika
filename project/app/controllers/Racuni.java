package controllers;

import java.util.Date;
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
	
	public static void create(Long klijent, Long banka, Long valuta) {
		ValidacijaRacuna.validate(validation, klijent, banka, valuta);
		Klijent _klijent = Klijent.findById(klijent);
		if(_klijent==null) {
			validation.addError("klijent", "Nepostojeci klijent");
		}
		Banka _banka = Banka.findById(banka);
		if(_banka==null) {
			validation.addError("banka", "Nepostojeca banka");
		}
		Valuta _valuta = Valuta.findById(valuta);
		if(_valuta==null) {
			validation.addError("valuta", "Nepostojeca valuta");
		}
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_DODAVANJE, "");
		} else {
			if(validation.hasErrors()) {
				validation.keep();
				show(Konstante.KONF_DODAVANJE, "");
			} else {
				String _sifraBankePocetna = _banka.sifraBanke;
				StringBuilder _sifraBanke = new StringBuilder(_sifraBankePocetna);
				while(_sifraBanke.charAt(0)=='0') {
					_sifraBanke.delete(0, 0);
				}
				StringBuilder _brojRacuna = new StringBuilder(((Long)Racun.count("banka = ?", _banka)).toString());
				while(_brojRacuna.length()<13) {
					_brojRacuna.insert(0, "0");
				}
				String _brojRacunaPravi = _brojRacuna.toString();
				long glavnina = Long.parseLong(_sifraBanke.toString() + _brojRacunaPravi) * 100;
				long kontrolneCifre = 98 - (glavnina % 97);
				String brojRacuna = _sifraBankePocetna + "-" + _brojRacunaPravi + "-" + kontrolneCifre; 
				Date sada = new Date();
				Racun racun = new Racun(brojRacuna, sada, true);
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
