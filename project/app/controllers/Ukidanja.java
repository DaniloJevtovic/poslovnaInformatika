package controllers;

import java.sql.Date;
import java.util.List;







import models.Drzava;
import models.Ukidanje;
import models.VrstaPlacanja;
import play.mvc.Controller;

/*
 * Nikola radi
 */
public class Ukidanja extends Controller {

	public Ukidanja() {
		// TODO Auto-generated constructor stub
	}
	public static void show(String mode,Long selected){
		List<Ukidanje> ukidanje = Ukidanje.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(ukidanje, mode,selected);
	}
	
	public static void create(Date datumUkidanja, String sredSePrenNaRacun){

		Ukidanje ukidanje = new Ukidanje(datumUkidanja, sredSePrenNaRacun);
		ukidanje.save();		
		show("add",ukidanje.id);
	}
	
	public static void edit(Long id, Date datumUkidanja, String sredSePrenNaRacun){
		Ukidanje ukidanje = Ukidanje.findById(id);
		ukidanje.datumUkidanja=datumUkidanja;
		ukidanje.sredSePrenNaRacun=sredSePrenNaRacun;
		ukidanje.save();
		show("edit",ukidanje.id);
	}
	
	public static void filter(Long id,Date datumUkidanja, String sredSePrenNaRacun){
		List<Ukidanje> ukidanje = Ukidanje.find("bydatumUkidanjaLikeAndsredSePrenNaRacunLike", "%" + datumUkidanja + "%", "%" + sredSePrenNaRacun + "%").fetch();
		String mode = "edit";
		renderTemplate("Ukidanja/show.html", ukidanje, mode);
	}
	
	public static void delete(Long id){
		Ukidanje ukidanje = Ukidanje.findById(id);
		ukidanje.delete();
		Ukidanje prviVeci=Ukidanje.find("byIdGreaterThan", id).first();
		   if(prviVeci!=null) {
			    show("edit", prviVeci.id);
			    return;
		   }
		   Ukidanje prviManji=Ukidanje.find("select k from Ukidanje k where k.id < ? order by id desc", id).first();
				   if(prviManji!=null) {
					    show("edit", prviManji.id);
					    return;
				   }	   	
		   show("edit", null);
		  
	}
}