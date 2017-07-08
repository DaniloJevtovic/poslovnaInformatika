package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.helpers.QueryBuilder;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaDrzave;
import models.Drzava;
import play.mvc.Controller;

public class Drzave extends Controller{
	
	public Drzave() {
		// TODO Auto-generated constructor stub
	}
	
	public static void showDefault() {
		KonstanteSesije.resetSession(flash);
		show(Konstante.KONF_IZMJENA, "");
		
		//KonstanteSesije.clearFlashConfig(flash);
		//if(!KonstanteSesije.filterIsValid(flash, Konstante.IME_ENTITETA_DRZAVA, KonstanteSesije.FILTRI_DRZAVE)) {
			//KonstanteSesije.clearFlashFilter(flash);
		//}
		//flash.keep();
		//show(Konstante.KONF_DODAVANJE, "");
	}
	
	public static void show(String mode, String highlightedId){
		if (PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Drzava> drzave = Drzava.findAll();
			if (mode == null || mode.equals(""))
				mode = "edit";
			render(drzave, mode);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(String oznaka, String naziv){
		ValidacijaDrzave.validate(validation, oznaka, naziv);
		if(validation.hasErrors()) {
			show(Konstante.KONF_DODAVANJE, "");
		} else {
			Drzava drzava = new Drzava(oznaka, naziv);
			drzava.save();		
			show(Konstante.KONF_DODAVANJE, drzava.id.toString());
		}
	}
	
	public static void edit(Long id, String oznaka, String naziv){
		ValidacijaDrzave.validate(validation, oznaka, naziv);
		if(validation.hasErrors()) {
			show(Konstante.KONF_DODAVANJE, "");
		} else {
			Drzava drzava = Drzava.findById(id);
			if(drzava != null){
				drzava.oznaka = oznaka;
				drzava.naziv = naziv;
				drzava.save();
				show(Konstante.KONF_IZMJENA, id.toString());
			} else {
				notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_DRZAVA, id));
			}
		}
	}
	
	public static void filter(Long id, String oznaka, String naziv){
		flash.clear();
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.buildLikeQuery("Oznaka", oznaka);
		queryBuilder.buildLikeQuery("Naziv", naziv);
		List<Drzava> drzave = Drzava.find(queryBuilder.getQuery(), queryBuilder.getParams()).fetch();
		renderTemplate("Drzave/show.html", Konstante.KONF_IZMJENA, drzave);
	}
	
	public static void delete(Long id){
		Drzava drzava = Drzava.findById(id);
		drzava.delete();
		show("edit", "");
	}
	
	
	public static void nextNaseljenaMjesta(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_DRZAVA,
				Konstante.IME_ENTITETA_NASELJENO_MESTO,
				filterId);
		flash.keep();
		NaseljenaMesta.showDefault();
	}
	
	public static void nextValute(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_DRZAVA,
				Konstante.IME_ENTITETA_VALUTA,
				filterId);
		flash.keep();
		Valute.showDefault();
	}
	
	
	/*
	public static void delete(Long id){
		Drzava drzava = Drzava.findById(id);
		if(drzava!=null){
			drzava.delete();
			String highlightedId = "";
			Drzava prvaVeca = Drzava.find("byIdGreaterThan", id).first();
			if(prvaVeca!=null) {
				highlightedId = prvaVeca.id.toString();
			} else {
				Drzava prvaManja = Drzava.find("select d from Drzava d where d.id < ? order by id desc", id).first();
				if(prvaManja!=null) {
					highlightedId = prvaManja.id.toString();
				}
			}
			show(flash.get(KonstanteSesije.MODE), highlightedId);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_DRZAVA, id));
		}
	}
	*/
	
	/*
	public static void show(String mode){
		List<Drzava> drzave = Drzava.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(drzave, mode);
	}
	
	
	public static void create(String oznaka, String naziv){

		Drzava drzava = new Drzava(oznaka, naziv);
		drzava.save();		
		show("add", "");
	}
	
	
	public static void edit(Long id, String oznaka, String naziv){
		Drzava drzava = Drzava.findById(id);
		drzava.oznaka = oznaka;
		drzava.naziv = naziv;
		drzava.save();
		show("edit", "");
	}
	
	public static void filter(Long id, String oznaka, String naziv){
		List<Drzava> drzave = Drzava.find("byOznakaLikeAndNazivLike", "%" + oznaka + "%", "%" + naziv + "%").fetch();
		String mode = "edit";
		renderTemplate("Drzave/show.html", drzave, mode);
	}
	
	
	public static void delete(Long id){
		Drzava drzava = Drzava.findById(id);
		drzava.delete();
		show("edit", "");
	}
	*/
	
}
