package controllers;

import java.math.BigDecimal;
import java.util.List;

import models.Drzava;
import models.KursUValuti;
import models.Ukidanje;
import models.VrstaPlacanja;
import play.mvc.Controller;

/*
 * Nikola radi
 */
public class KurseviUValuti extends Controller {

	public KurseviUValuti() {
		// TODO Auto-generated constructor stub
	}
	public static void show(String mode,Long selected){
		List<KursUValuti> kursevi= KursUValuti.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(kursevi, mode,selected);
	}
	public static void create(String redniBroj,BigDecimal kupovni, BigDecimal srednji, BigDecimal prodajni){

		KursUValuti kurs = new KursUValuti(redniBroj,kupovni,srednji,prodajni);
		kurs.save();		
		show("add",kurs.id);
	}
	
	public static void edit(Long id,String redniBroj,BigDecimal kupovni, BigDecimal srednji, BigDecimal prodajni){
		KursUValuti kurs = KursUValuti.findById(id);
		kurs.redniBroj=redniBroj;
		kurs.kupovni=kupovni;
		kurs.srednji=srednji;
		kurs.prodajni=prodajni;		
		kurs.save();
		show("edit",kurs.id);
	}

	public static void filter(Long id,String redniBroj){
		List<KursUValuti> kurs = KursUValuti.find("byidLikeAndredniBrojLikeProdajniLike", "%" + id + "%", "%" + redniBroj + "%").fetch();
		String mode = "edit";
		renderTemplate("KurseviUValuti/show.html", kurs, mode);
	}
	public static void delete(Long id){
		KursUValuti kurs = KursUValuti.findById(id);
		kurs.delete();
		KursUValuti prviVeci=KursUValuti.find("byIdGreaterThan", id).first();
		   if(prviVeci!=null) {
			    show("edit", prviVeci.id);
			    return;
		   }
		   KursUValuti prviManji=KursUValuti.find("select k from KursUValuti k where k.id < ? order by id desc", id).first();
				   if(prviManji!=null) {
					    show("edit", prviManji.id);
					    return;
				   }	   	
		   show("edit", null);
		  
	}

	
}
