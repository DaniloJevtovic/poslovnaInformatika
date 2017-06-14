package controllers;

import java.util.List;

import models.KursUValuti;
import play.mvc.Controller;

/*
 * Nikola radi
 */
public class KurseviUValuti extends Controller {

	public KurseviUValuti() {
		// TODO Auto-generated constructor stub
	}
	public static void show(String mode){
		List<KursUValuti> kursevi= KursUValuti.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(kursevi, mode);
	}
	public static void create(long kupovni, long srednji, long prodajni){

		KursUValuti kurs = new KursUValuti(kupovni,srednji,prodajni);
		kurs.save();		
		show("add");
	}
	
	public static void edit(Long id,long kupovni, long srednji, long prodajni){
		KursUValuti kurs = KursUValuti.findById(id);
		kurs.kupovni=kupovni;
		kurs.srednji=srednji;
		kurs.prodajni=prodajni;		
		kurs.save();
		show("edit");
	}

	

	
}
