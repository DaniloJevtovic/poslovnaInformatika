package controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import models.AnalitikaIzvoda;
import models.Banka;
import models.DnevnoStanjeRacuna;
import models.Klijent;
import models.Racun;
import models.Ukidanje;
import models.Valuta;
import play.mvc.Controller;
import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.helpers.QueryBuilder;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaRacuna;

/*
 * Stevan radi
 */
public class Racuni extends Controller {

	public Racuni() {
		//prazno
	}
	
	public static void showDefault() {
		KonstanteSesije.clearFlashConfig(flash);
		if(!KonstanteSesije.filterIsValid(flash, Konstante.IME_ENTITETA_RACUN, KonstanteSesije.FILTRI_RACUNA)) {
			KonstanteSesije.clearFlashFilter(flash);
		}
		flash.keep();
		show(Konstante.KONF_DODAVANJE, "");
	}
	
	public static void show(String mode, String highlightedId) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Racun> racuni;
			if(Konstante.IME_ENTITETA_RACUN.equals(flash.get(KonstanteSesije.TARGET_ENTITY))) {
				String query = "";
				switch(flash.get(KonstanteSesije.FILTER_ENTITY)) {
				case Konstante.IME_ENTITETA_BANKA:
					query = "select r from Racun r where r.banka.id = ?";
					break;
				case Konstante.IME_ENTITETA_KLIJENT:
					query = "select r from Racun r where r.klijent.id = ?";
					break;
				case Konstante.IME_ENTITETA_VALUTA:
					query = "select r from Racun r where r.valuta.id = ?";
					break;
				}
				long filterId = Long.parseLong(flash.get(KonstanteSesije.FILTER_ID)); 
				racuni = Racun.find(query, filterId).fetch();
			} else {
				racuni = Racun.findAll();
			}
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
	
	public static void filter(String brojRacuna, Date datumOtvaranjaManjeJednako,
			Date datumOtvaranjaVeceJednako, boolean vazeci) {
		flash.clear();
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.buildLikeQuery("BrojRacuna", brojRacuna);
		queryBuilder.buildLessThanEqualsQuery("DatumOtvaranja", datumOtvaranjaManjeJednako);
		queryBuilder.buildGreaterThanEqualsQuery("DatumOtvaranja", datumOtvaranjaVeceJednako);
		queryBuilder.buildSimpleQuery("Vazeci", vazeci);
		List<Racun> racuni = Racun.find(queryBuilder.getQuery(), queryBuilder.getParams()).fetch();
		renderTemplate("Racuni/show.html", Konstante.KONF_IZMJENA, racuni);
	}
	
	public static void nextDnevnaStanja(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_RACUN,
				Konstante.IME_ENTITETA_DNEVNO_STANJE_RACUNA,
				filterId);
		flash.keep();
		DnevnaStanjaRacuna.showDefault();
	}
	
	public static void nextUkidanja(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_RACUN,
				Konstante.IME_ENTITETA_UKIDANJE,
				filterId);
		flash.keep();
		Ukidanja.showDefault();
	}
	
	public static void ukini(Long id,String brojRacuna) {
		Racun racun=Racun.findById(id);
		if(racun!=null) {
			if(racun.vazeci) {
				ValidacijaRacuna.validateAccountNumber(brojRacuna);
				DnevnoStanjeRacuna posZaRacun = PomocneOperacije.danasnjeDnevnoStanje(racun);
				Date datumUkidanja= PomocneOperacije.getStartOfDay(new Date());
				if(posZaRacun==null) {
					racun.vazeci=false;
					racun.save();
					Ukidanje ukidanje=new Ukidanje(datumUkidanja,brojRacuna);
					ukidanje.racun = racun;
					ukidanje.save();
				} else {
					Racun naKojiSePrenosi = Racun.find("byBrojRacuna", brojRacuna).first();
					if(naKojiSePrenosi==null) {
						throw new NullPointerException();
					}
					if(racun.valuta.id != naKojiSePrenosi.valuta.id) {
						throw new IllegalStateException();
					}
					if(racun.banka.id != naKojiSePrenosi.banka.id) {
						throw new IllegalStateException();
					}
					if(!naKojiSePrenosi.vazeci) {
						throw new IllegalStateException();
					}
					DnevnoStanjeRacuna posZaNaKojiSePrenosi = PomocneOperacije.danasnjeDnevnoStanje(naKojiSePrenosi);
					BigDecimal iznos = posZaRacun.novoStanje;
					AnalitikaIzvoda prenosna = new AnalitikaIzvoda(
						PomocneOperacije.nazivKlijenta(racun.klijent), 
						"Prenos zbog zatvaranja",
						PomocneOperacije.nazivKlijenta(naKojiSePrenosi.klijent),
						datumUkidanja,
						datumUkidanja,
						racun.brojRacuna,
						null,
						null,
						naKojiSePrenosi.brojRacuna,
						
						null,
						null,
						false,
						iznos,
						null,
						null
					);
					prenosna.dnevnoStanjeNaTeret = posZaRacun;
					prenosna.dnevnoStanjeUKorist = posZaNaKojiSePrenosi;
					posZaRacun.dodajNaTaret(iznos);
					posZaNaKojiSePrenosi.dodajUKorist(iznos);
					racun.vazeci = false;
					racun.save();
					posZaRacun.save();
					posZaNaKojiSePrenosi.save();
					prenosna.save();
					Ukidanje ukidanje=new Ukidanje(datumUkidanja,brojRacuna);
					ukidanje.racun = racun;
					ukidanje.save();
				}
				
			}
			show(flash.get(KonstanteSesije.MODE), id.toString());
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_RACUN, id));
		}
		
	}
}
