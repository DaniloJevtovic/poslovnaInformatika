package controllers;

import java.util.*;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.helpers.QueryBuilder;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaDrzave;
import controllers.validation.ValidacijaNaseljenogMjesta;
import models.Drzava;
import models.NaseljenoMesto;
import play.mvc.*;

public class NaseljenaMesta extends Controller{

	public static void showDefault() {
		KonstanteSesije.clearFlashConfig(flash);
		if (!KonstanteSesije.filterIsValid(flash, Konstante.IME_ENTITETA_NASELJENO_MESTO,
				KonstanteSesije.FILTRI_NASELJENA_MJESTA)) {
			KonstanteSesije.clearFlashFilter(flash);
		}
		flash.keep();
		show(Konstante.KONF_DODAVANJE, "");
	}

	public static void show(String mode, String highlightedId) {
		if (PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<NaseljenoMesto> naseljenaMjesta;
			
			if(Konstante.IME_ENTITETA_MEDJUBANKARSKI_PRENOS.equals(flash.get(KonstanteSesije.FILTER_ENTITY))){
				String query = "";
				switch (flash.get(KonstanteSesije.FILTER_ENTITY)) {
				case Konstante.IME_ENTITETA_DRZAVA:
					query = "select n from NaseljenoMjesto n where n.drzava.id = ?";
					break;
				}
				naseljenaMjesta = NaseljenoMesto.find(query, flash.get(KonstanteSesije.FILTER_ID)).fetch();
			} else {
				naseljenaMjesta = NaseljenoMesto.findAll();
			}
			List<Drzava> drzave = Drzava.findAll();
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(mode, naseljenaMjesta, drzave);
			
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}

	public static void create(String oznaka, String naziv, String postanskiBroj, Long drzava) {
		ValidacijaNaseljenogMjesta.validate(validation, oznaka, naziv, postanskiBroj);
		Drzava _drzava=null;
		if(drzava!=null) {
			_drzava = Drzava.findById(drzava);
			if(_drzava==null) {
				validation.addError("drzava", "Drzava nije pronadjena");
			}
		}
		if (validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_DODAVANJE, "");
		}
		
		NaseljenoMesto naseljenoMjesto = new NaseljenoMesto(oznaka, naziv, postanskiBroj);
		naseljenoMjesto.drzava = _drzava;
		naseljenoMjesto.save();
		show(Konstante.KONF_DODAVANJE, naseljenoMjesto.id.toString());
		
	}

	public static void edit(Long id, String oznaka, String naziv, String postanskiBroj, Long drzava) {
		ValidacijaNaseljenogMjesta.validate(validation, oznaka, naziv, postanskiBroj);
		Drzava _drzava = null;

		if (drzava != null) {
			_drzava = Drzava.findById(drzava);
			if (_drzava == null)
				validation.addError("drzava", "Drzava nije pronadjena!");
		}

		if (validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_DODAVANJE, "");

			NaseljenoMesto naseljenoMesto = NaseljenoMesto.findById(id);
			if (naseljenoMesto != null) {
				naseljenoMesto.oznaka = oznaka;
				naseljenoMesto.naziv = naziv;
				naseljenoMesto.postanskiBroj = postanskiBroj;
				naseljenoMesto.drzava = _drzava;
				naseljenoMesto.save();
				show(Konstante.KONF_IZMJENA, id.toString());
			} else {
				notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_NASELJENO_MESTO, id));
			}
		}
		show(Konstante.KONF_IZMJENA, id.toString());
	}

	public static void filter(Long id, String oznaka, String naziv, String postanskiBroj, Drzava drzava) {
		flash.clear();
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.buildLikeQuery("Oznaka", oznaka);
		queryBuilder.buildLikeQuery("Naziv", naziv);
		queryBuilder.buildLikeQuery("PostanskiBroj", postanskiBroj);
		queryBuilder.buildLikeQuery("Drzava", drzava.toString());

		List<NaseljenoMesto> naseljenaMjesta = NaseljenoMesto.find(queryBuilder.getQuery(), queryBuilder.getParams()).fetch();
		renderTemplate("NaseljenaMesta/show.html", Konstante.KONF_IZMJENA, naseljenaMjesta);
	}

	public static void delete(Long id) {
		NaseljenoMesto naseljenoMesto = NaseljenoMesto.findById(id);
		naseljenoMesto.delete();
		show("edit", "");
	}
	
	
	/*
	public NaseljenaMesta() {
		// TODO Auto-generated constructor stub
	}
	
	public static void show(String mode){
		List<NaseljenoMesto> naseljenaMesta = NaseljenoMesto.findAll();
		List<Drzava> drzave = Drzava.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(naseljenaMesta, drzave, mode);
	}
	
	public static void create(String oznaka, String naziv, String postanskiBroj, Long drzava){
		Drzava _drzava = Drzava.findById(drzava);
		NaseljenoMesto naseljenoMesto = new NaseljenoMesto(oznaka, naziv, postanskiBroj, _drzava);
		naseljenoMesto.save();
		show("add");
	}
	
	public static void edit(Long id, String oznaka, String naziv, String postanskiBroj, Long drzava){
		NaseljenoMesto naseljenoMesto = NaseljenoMesto.findById(id);
		naseljenoMesto.oznaka = oznaka;
		naseljenoMesto.naziv = naziv;
		naseljenoMesto.postanskiBroj = postanskiBroj;
		Drzava _drzava = Drzava.findById(drzava);
		naseljenoMesto.drzava = _drzava;
		naseljenoMesto.save();
		show("edit");
	}
	
	public static void filter(String oznaka, String naziv, String postanskiBroj, Long drzava) {
		List<NaseljenoMesto> naseljenaMesta = NaseljenoMesto.find("byOznakaLikeAndNazivLikeAndPostanskiBrojLikeAndDrzava_id", "%"+ oznaka +"%", "%"+ naziv +"%", "%"+ postanskiBroj +"%", drzava).fetch();
		List<Drzava> drzave = Drzava.findAll();
		String mode = "edit";
		renderTemplate("NaseljenaMesta/show.html", naseljenaMesta, mode, drzave);
	}
	
	public static void delete(Long id){
		NaseljenoMesto naseljenoMesto = NaseljenoMesto.findById(id);
		naseljenoMesto.delete();
		show("edit");
	}
	
	public static void nextForm(Long drzava_id) {
		List<NaseljenoMesto> naseljenaMesta = NaseljenoMesto.find("byDrzava_id", drzava_id).fetch();
		List<Drzava> drzave = new ArrayList<Drzava>();		
		drzave.add(Drzava.findById(drzava_id));
		String mode = "edit";
		renderTemplate("NaseljenaMesta/show.html", naseljenaMesta, drzave, mode);
	}
	*/

}
