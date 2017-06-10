package controllers;

import java.util.List;

import models.Banka;
import play.mvc.Controller;

/*
 * Radi Danilo
 */
public class Banke extends Controller {

	public Banke() {
		// TODO Auto-generated constructor stub
	}

	public static void show(String mode){
		List<Banka> banke = Banka.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(banke, mode);
	}
	
	public static void create(int idBanke, String sifraBanke, String pIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke){

		Banka banka = new Banka(idBanke, sifraBanke, pIBBanke, nazivBanke, adresaBanke, telefonBanke, emailBanke, webBanke, faksBanke);
		banka.save();		
		show("add");
	}
	
	public static void edit(int idBanke, String sifraBanke, String pIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke){
		
		Banka banka = Banka.findById(idBanke);
		banka.idBanke = idBanke;
		banka.sifraBanke = sifraBanke;
		banka.PIBBanke = pIBBanke;
		banka.nazivBanke = nazivBanke;
		banka.adresaBanke = adresaBanke;
		banka.telefonBanke = telefonBanke;
		banka.emailBanke = emailBanke;
		banka.webBanke = webBanke;
		banka.faksBanke = faksBanke;
		
		banka.save();
		show("edit");
	}
	
	public static void filter(int idBanke, String sifraBanke, String pIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke){
		List<Banka> banke = Banka.find("byidBankeLikeAndsifraBankeLike", "%" + idBanke + "%", "%" + sifraBanke + "%").fetch();
		String mode = "edit";
		renderTemplate("Drzave/show.html", banke, mode);
	}
	
	public static void delete(Long id){
		Banka banka = Banka.findById(id);
		banka.delete();
		show("edit");
	}
	
}
