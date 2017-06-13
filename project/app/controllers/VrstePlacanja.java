package controllers;

import java.util.List;

import models.VrstaPlacanja;
import play.mvc.Controller;

/*
 * Nikola radi
 */
public class VrstePlacanja extends Controller {

	public VrstePlacanja() {
		// TODO Auto-generated constructor stub
	}
	public static void show(String mode){
		List<VrstaPlacanja> placanje = VrstaPlacanja.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(placanje, mode);
	}
	
	public static void create(int oznakaVrste, String nazivVrstePlacanja){

		VrstaPlacanja placanje = new VrstaPlacanja(oznakaVrste, nazivVrstePlacanja);
		placanje.save();		
		show("add");
	}
	
	public static void edit(long id,int oznakaVrste, String nazivVrstePlacanja){
		
		VrstaPlacanja placanje = VrstaPlacanja.findById(id);
		placanje.oznakaVrste=oznakaVrste;
		placanje.nazivVrstePlacanja=nazivVrstePlacanja;
		
		placanje.save();
		show("edit");
	}
	

}
