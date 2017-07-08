package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import controllers.helpers.Konstante;
import controllers.session.KonstanteSesije;
import models.Banka;
import models.Drzava;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import play.db.DB;
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
	
	public static void create(String sifraBanke, String PIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke){

		Banka banka = new Banka(sifraBanke, PIBBanke, nazivBanke, adresaBanke, telefonBanke, emailBanke, webBanke, faksBanke);
		banka.save();		
		show("add");
	}
	
	public static void edit(Long id, String sifraBanke, String PIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke){
		
		Banka banka = Banka.findById(id);
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
	
	public static void nextKursneListe(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_BANKA,
				Konstante.IME_ENTITETA_KURSNA_LISTA,
				filterId);
		flash.keep();
		KursneListe.showDefault();
	}
	
	public static void nextRacuni(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_BANKA,
				Konstante.IME_ENTITETA_RACUN,
				filterId);
		flash.keep();
		Racuni.showDefault();
	}
	
	public static void reportPDF(Long id_banke) throws SQLException, JRException {
		String pdfDir = "public/GenPdf/";
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("id_banke", id_banke);
		
		//old conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poslovna", "isa", "nekasifra");
		Connection conn = DB.getConnection();
		
		Date now = new Date();
		String pdfName = "" + now.getTime() + (((Long)Math.round(Math.random() * 1000000))+1000000) + ".pdf";
		String pdfPath = pdfDir + pdfName;
		
		JasperPrint jprint = (JasperPrint)JasperFillManager.fillReport("jasper/spisak_racuna.jasper", params, conn);
		JasperExportManager.exportReportToPdfFile(jprint, pdfPath);
		
		redirect("/pdf/" + pdfName);
	}
	
}
