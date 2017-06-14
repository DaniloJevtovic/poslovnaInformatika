package controllers;

import java.util.List;

import models.StavkaKliringa;
import play.mvc.Controller;

/*
 * Danilo radi
 */
public class StavkeKliringa extends Controller {

	public StavkeKliringa() {
		// TODO Auto-generated constructor stub
	}
	
	public static void show(String mode){
		List<StavkaKliringa> stavkeKliringa = StavkaKliringa.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(stavkeKliringa, mode);
	}
	
	public static void create(String idStavke){
		StavkaKliringa stavkaKliringa = new StavkaKliringa(idStavke);
		stavkaKliringa.save();
		show("add");
	}
	
	public static void edit(Long id, String idStavke){
		StavkaKliringa stavkaKliringa = StavkaKliringa.findById(id);
		stavkaKliringa.idStavke = idStavke;
		stavkaKliringa.save();
		show("edit");
	}
	
	public static void filter(String idStavke){
		List<StavkaKliringa> stavkeKliringa = StavkaKliringa.find("byIdStavkeLike", "%" + idStavke + "%").fetch();
		String mode = "edit";
		renderTemplate("StavkeKliringa/show.html", stavkeKliringa, mode);
	}
	
	public static void delete(Long id){
		StavkaKliringa stavkaKliringa = StavkaKliringa.findById(id);
		stavkaKliringa.delete();
		show("edit");
	}

}
