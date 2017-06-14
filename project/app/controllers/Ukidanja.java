package controllers;

import java.sql.Date;
import java.util.List;




import models.Ukidanje;
import play.mvc.Controller;

/*
 * Nikola radi
 */
public class Ukidanja extends Controller {

	public Ukidanja() {
		// TODO Auto-generated constructor stub
	}
	public static void show(String mode){
		List<Ukidanje> ukidanje = Ukidanje.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(ukidanje, mode);
	}
	
	public static void create(Date datumUkidanja, String sredSePrenNaRacun){

		Ukidanje ukidanje = new Ukidanje(datumUkidanja, sredSePrenNaRacun);
		ukidanje.save();		
		show("add");
	}
	
	public static void edit(Long id, Date datumUkidanja, String sredSePrenNaRacun){
		Ukidanje ukidanje = Ukidanje.findById(id);
		ukidanje.datumUkidanja=datumUkidanja;
		ukidanje.sredSePrenNaRacun=sredSePrenNaRacun;
		ukidanje.save();
		show("edit");
	}
}
