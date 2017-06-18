package controllers;

import java.util.List;

import models.Banka;
import models.Drzava;
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
	
	public static void create(String idBanke, String sifraBanke, String PIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke){

		Banka banka = new Banka(idBanke, sifraBanke, PIBBanke, nazivBanke, adresaBanke, telefonBanke, emailBanke, webBanke, faksBanke);
		banka.save();		
		show("add");
	}
	
	public static void edit(Long id, String idBanke, String sifraBanke, String PIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke){
		
		Banka banka = Banka.findById(id);
		banka.idBanke = idBanke;
		banka.sifraBanke = sifraBanke;
		banka.PIBBanke = PIBBanke;
		banka.nazivBanke = nazivBanke;
		banka.adresaBanke = adresaBanke;
		banka.telefonBanke = telefonBanke;
		banka.emailBanke = emailBanke;
		banka.webBanke = webBanke;
		banka.faksBanke = faksBanke;
		
		banka.save();
		show("edit");
	}
	
	public static void filter(String idBanke, String sifraBanke, String PIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke){
		
		List<Banka> banke = Banka.find("byIdBankeLikeAndSifraBankeLikeAndPIBBankeLikeAndNazivBankeLikeAndAdresaBankeLike"
				+ "AndTelefonBankeLikeAndEmailBankeLikeAndWebBankeLikeAndFaksBankeLike", 
				"%" + idBanke + "%", "%" + sifraBanke + "%", "%" + PIBBanke + "%", "%" + nazivBanke + "%", "%" + adresaBanke + "%",
				"%" + telefonBanke + "%","%" + emailBanke + "%", "%" + webBanke + "%", "%" + faksBanke + "%").fetch();
	
		String mode = "edit";
		renderTemplate("Banke/show.html", banke, mode);
	}
	
	public static void delete(Long id){
		Banka banka = Banka.findById(id);
		banka.delete();
		show("edit");
	}
	
}
