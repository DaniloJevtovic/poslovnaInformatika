package controllers;

import java.util.List;

import models.AnalitikaIzvoda;
import models.MedjubankarskiPrenos;
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
	
	public static void create(String idStavke, AnalitikaIzvoda analitikeIzvoda, MedjubankarskiPrenos medjubankarskiPrenos){
		StavkaKliringa stavkaKliringa = new StavkaKliringa(idStavke, analitikeIzvoda, medjubankarskiPrenos);
		stavkaKliringa.save();
		show("add");
	}
	
	public static void edit(Long id, String idStavke, AnalitikaIzvoda analitikaIzvoda, MedjubankarskiPrenos medjubankarskiPrenos){
		StavkaKliringa stavkaKliringa = StavkaKliringa.findById(id);
		stavkaKliringa.idStavke = idStavke;
		stavkaKliringa.analitikaIzvoda = analitikaIzvoda;
		stavkaKliringa.medjubankarskiPrenos = medjubankarskiPrenos;
		stavkaKliringa.save();
		show("edit");
	}
	
	public static void filter(String idStavke, AnalitikaIzvoda analitikaIzvoda, MedjubankarskiPrenos medjubankarskiPrenos){
		List<StavkaKliringa> stavkeKliringa = StavkaKliringa.find("byIdStavkeLikeAndAnalitikaIzvodaLikeAndMedjubankarskiPrenosLike", 
				"%" + idStavke + "%", "%" + analitikaIzvoda + "%", "%" + medjubankarskiPrenos + "%").fetch();
		String mode = "edit";
		renderTemplate("StavkeKliringa/show.html", stavkeKliringa, mode);
	}
	
	public static void delete(Long id){
		StavkaKliringa stavkaKliringa = StavkaKliringa.findById(id);
		stavkaKliringa.delete();
		show("edit");
	}

}
