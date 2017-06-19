package controllers;

import java.util.List;

import models.Drzava;
import models.KursUValuti;
import models.VrstaPlacanja;
import play.mvc.Controller;

/*
 * Nikola radi
 */
public class VrstePlacanja extends Controller {

	public VrstePlacanja() {
		// TODO Auto-generated constructor stub
	}
	public static void show(String mode,Long selected){
		List<VrstaPlacanja> placanje = VrstaPlacanja.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(placanje, mode,selected);
	}
	
	public static void create(String oznakaVrste, String nazivVrstePlacanja){

		VrstaPlacanja placanje = new VrstaPlacanja(oznakaVrste, nazivVrstePlacanja);
		placanje.save();		
		show("add",placanje.id);
	}
	
	public static void edit(long id,String oznakaVrste, String nazivVrstePlacanja){
		
		VrstaPlacanja placanje = VrstaPlacanja.findById(id);
		placanje.oznakaVrste=oznakaVrste;
		placanje.nazivVrstePlacanja=nazivVrstePlacanja;
		
		placanje.save();
		show("edit",placanje.id);
	}
	public static void filter(Long id,String oznakaVrste, String nazivVrstePlacanja){
		List<VrstaPlacanja> placanje = VrstaPlacanja.find("byOznaka_vrsteLikeAndNaziv_vrste_placanjaLike", "%"+oznakaVrste+"%","%"+nazivVrstePlacanja+"%").fetch();
		String mode = "edit";
		renderTemplate("VrstePlacanja/show.html", placanje, mode);
	}

	public static void delete(Long id){
		VrstaPlacanja placanja= VrstaPlacanja.findById(id);
		placanja.delete();
		VrstaPlacanja prviVeci=VrstaPlacanja.find("byIdGreaterThan", id).first();
		   if(prviVeci!=null) {
			    show("edit", prviVeci.id);
			    return;
		   }
		   VrstaPlacanja prviManji=VrstaPlacanja.find("select k from VrstaPlacanja k where k.id < ? order by id desc", id).first();
				   if(prviManji!=null) {
					    show("edit", prviManji.id);
					    return;
				   }	   	
		   show("edit", null);
		  
	}

}
