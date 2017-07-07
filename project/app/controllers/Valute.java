package controllers;

import java.util.List;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.helpers.QueryBuilder;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaValute;
import models.Drzava;
import models.Klijent;
import models.Racun;
import models.Valuta;
import play.mvc.Controller;

/*
 * Stevan radi
 */
public class Valute extends Controller {

	public Valute() {
		// prazno
	}
	
	public static void showDefault() {
		if(!KonstanteSesije.filterIsValid(flash, Konstante.IME_ENTITETA_VALUTA, KonstanteSesije.FILTRI_VALUTE)) {
			KonstanteSesije.resetSession(flash);
		}
		show(Konstante.KONF_IZMJENA, "");
	}
	
	public static void show(String mode, String highlightedId) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Valuta> valute;
			if(Konstante.IME_ENTITETA_VALUTA.equals(flash.get(KonstanteSesije.FILTER_ENTITY))) {
				String query = "";
				switch(flash.get(KonstanteSesije.FILTER_ENTITY)) {
				case Konstante.IME_ENTITETA_DRZAVA:
					query = "select v from Valuta v where v.drzava.id = ?";
					break;
				}
				valute = Valuta.find(query, flash.get(KonstanteSesije.FILTER_ID)).fetch();
			} else {
				valute = Valuta.findAll();
			}
			List<Drzava> drzave = Drzava.findAll();
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(mode, valute, drzave);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(String zvanicnaSifra, String nazivValute, Long drzava) {
		ValidacijaValute.validate(validation, zvanicnaSifra, nazivValute);
		Drzava _drzava=null;
		if(drzava!=null) {
			_drzava = Drzava.findById(drzava);
			if(_drzava==null) {
				validation.addError("drzava", "Drzava nije pronadjena");
			}
		}
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_DODAVANJE, "");
		}
		Valuta valuta = new Valuta(zvanicnaSifra, nazivValute);
		valuta.drzava = _drzava;
		valuta.save();
		show(Konstante.KONF_DODAVANJE, valuta.id.toString());
		
	}

	public static void edit(Long id, String zvanicnaSifra, String nazivValute, Long drzava) {
		ValidacijaValute.validate(validation, zvanicnaSifra, nazivValute);
		Drzava _drzava=null;
		if(drzava!=null) {
			_drzava = Drzava.findById(drzava);
			if(_drzava==null) {
				validation.addError("drzava", "Drzava nije pronadjena");
			}
		}
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_IZMJENA, id.toString());
		}
		Valuta valuta = Valuta.findById(id);
		if(valuta!=null) {
			valuta.zvanicnaSifra = zvanicnaSifra;
			valuta.nazivValute = nazivValute;
			valuta.drzava = _drzava;
			valuta.save();
			show(Konstante.KONF_IZMJENA, id.toString());
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_VALUTA, id));
		}
	}
	
	public static void delete(Long id) {
		Valuta valuta = Valuta.findById(id);
		if(valuta!=null) {
			valuta.delete();
			String highlightedId = "";
			Valuta prvaVeca = Valuta.find("byIdGreaterThan", id).first();
			if(prvaVeca!=null) {
				highlightedId = prvaVeca.id.toString();
			} else {
				Valuta prvaManja = Valuta.find("select v from Valuta v where v.id < ? order by id desc", id).first();
				if(prvaManja!=null) {
					highlightedId = prvaManja.id.toString();
				}
			}
			show(flash.get(KonstanteSesije.MODE), highlightedId);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_VALUTA, id));
		}
	}
	
	public static void filter(String zvanicnaSifra, String nazivValute) {
		flash.clear();
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.buildLikeQuery("ZvanicnaSifra", zvanicnaSifra);
		queryBuilder.buildLikeQuery("NazivValute", nazivValute);
		List<Valuta> valute = Valuta.find(queryBuilder.getQuery(), queryBuilder.getParams()).fetch();
		renderTemplate("Valute/show.html", Konstante.KONF_IZMJENA, valute);
	}
	
	public static void nextKurseviUValutiOsnovna(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_OSNOVNA_VALUTA,
				Konstante.IME_ENTITETA_KURS_U_VALUTI,
				filterId);
		flash.keep();
		KurseviUValuti.showDefault();
	}
	
	public static void nextKurseviUValutiPrema(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_PREMA_VALUTI,
				Konstante.IME_ENTITETA_KURS_U_VALUTI,
				filterId);
		flash.keep();
		KurseviUValuti.showDefault();
	}
	
	public static void nextRacuni(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_VALUTA,
				Konstante.IME_ENTITETA_RACUN,
				filterId);
		flash.keep();
		Racuni.showDefault();
	}
	
	public static void nextAnalitike(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_VALUTA,
				Konstante.IME_ENTITETA_ANALITIKA_IZVODA,
				filterId);
		flash.keep();
		AnalitikeIzvoda.showDefault();
	}
	
}
