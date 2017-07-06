package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

import org.example.izvod_klijenta.IzvodKlijenta;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.helpers.QueryBuilder;
import controllers.poslovna_obrada.XmlPunilac;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaKlijenta;
import models.Klijent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import play.mvc.Controller;

/*
 * Stevan radi
 */
public class Klijenti extends Controller {

	public Klijenti() {
		//prazno
	}
	
	public static void showDefault() {
		KonstanteSesije.resetSession(flash);
		show(Konstante.KONF_IZMJENA, "");
	}
	
	public static void show(String mode, String highlightedId) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Klijent> klijenti = Klijent.findAll();
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(klijenti);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(String tipKlijenta, String nazivKlijenta, Integer pibKlijenta, String imeKlijenta, 
			String przKlijenta, String telKlijenta, String adrKlijenta, String webKlijenta, String emailKlijenta,
			String faksKlijenta) {
		ValidacijaKlijenta.validate(validation, tipKlijenta, nazivKlijenta,
				pibKlijenta, imeKlijenta, przKlijenta, telKlijenta, adrKlijenta,
				webKlijenta, emailKlijenta, faksKlijenta);
		if(validation.hasErrors()) {
			show(Konstante.KONF_DODAVANJE, "");
		} else {
			Klijent klijent = new Klijent(tipKlijenta, nazivKlijenta, pibKlijenta,
					imeKlijenta, przKlijenta, telKlijenta, adrKlijenta, webKlijenta,
					emailKlijenta, faksKlijenta);
			klijent.save();
			show(Konstante.KONF_DODAVANJE, klijent.id.toString());
		}
	}
	
	public static void edit(Long id, String tipKlijenta, String nazivKlijenta, Integer pibKlijenta, String imeKlijenta,
			String przKlijenta, String telKlijenta, String adrKlijenta, String webKlijenta, String emailKlijenta,
			String faksKlijenta) {
		ValidacijaKlijenta.validate(validation, tipKlijenta, nazivKlijenta,
				pibKlijenta, imeKlijenta, przKlijenta, telKlijenta, adrKlijenta,
				webKlijenta, emailKlijenta, faksKlijenta);
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_IZMJENA, id.toString());
		} else {
			Klijent klijent = Klijent.findById(id);
			if(klijent!=null) {
				klijent.tipKlijenta = tipKlijenta;
				klijent.nazivKlijenta = nazivKlijenta;
				klijent.imeKlijenta = imeKlijenta;
				klijent.przKlijenta = przKlijenta;
				klijent.telKlijenta = telKlijenta;
				klijent.adrKlijenta = adrKlijenta;
				klijent.webKlijenta = webKlijenta;
				klijent.emailKlijenta = emailKlijenta;
				klijent.faksKlijenta = faksKlijenta;
				klijent.save();
				show(Konstante.KONF_IZMJENA, id.toString());
			} else {
				notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KLIJENT, id));
			}
		}
	}
	
	public static void delete(Long id) {
		Klijent klijent = Klijent.findById(id);
		if(klijent!=null) {
			klijent.delete();
			String highlightedId = "";
			Klijent prviVeci = Klijent.find("byIdGreaterThan", id).first();
			if(prviVeci!=null) {
				highlightedId = prviVeci.id.toString();
			} else {
				Klijent prviManji = Klijent.find("select k from Klijent k where k.id < ? order by id desc", id).first();
				if(prviManji!=null) {
					highlightedId = prviManji.id.toString();
				}
			}
			show(flash.get(KonstanteSesije.MODE), highlightedId);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KLIJENT, id));
		}
	}
	
	public static void filter(String tipKlijenta, String nazivKlijenta,
			Integer pibManjeJednako, Integer pibVeceJednako, String imeKlijenta,
			String przKlijenta, String telKlijenta, String adrKlijenta,
			String webKlijenta, String emailKlijenta, String faksKlijenta) {
		flash.clear();
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.buildLikeQuery("TipKlijenta", tipKlijenta);
		queryBuilder.buildLikeQuery("NazivKlijenta", nazivKlijenta);
		queryBuilder.buildLessThanEqualsQuery("PibKlijenta", pibManjeJednako);
		queryBuilder.buildGreaterThanEqualsQuery("PibKlijenta", pibVeceJednako);
		queryBuilder.buildLikeQuery("ImeKlijenta", imeKlijenta);
		queryBuilder.buildLikeQuery("PrzKlijenta", przKlijenta);
		queryBuilder.buildLikeQuery("TelKlijenta", telKlijenta);
		queryBuilder.buildLikeQuery("AdrKlijenta", adrKlijenta);
		queryBuilder.buildLikeQuery("WebKlijenta", webKlijenta);
		queryBuilder.buildLikeQuery("EmailKlijenta", emailKlijenta);
		queryBuilder.buildLikeQuery("FaksKlijenta", faksKlijenta);
		List<Klijent> klijenti  = Klijent.find(queryBuilder.getQuery(), queryBuilder.getParams()).fetch();
		renderTemplate("Klijenti/show.html", Konstante.KONF_IZMJENA, klijenti);
	}
	
	public static void nextRacuni(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_KLIJENT,
				Konstante.IME_ENTITETA_RACUN,
				filterId);
		flash.keep();
		Racuni.showDefault();
	}
	
	public static void reportXML(Long id_klijenta, Date from, Date to) throws DatatypeConfigurationException, JAXBException, ParserConfigurationException {
		Klijent klijent = Klijent.findById(id_klijenta);
		IzvodKlijenta xmlIzvod = XmlPunilac.napuniXML(klijent, from, to);
		
		// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
		JAXBContext context = JAXBContext.newInstance("org.example.izvod_klijenta");
		// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
		Marshaller marshaller = context.createMarshaller();
		// Marshaller podesen da daje formatiran izlaz
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		StringWriter writer = new StringWriter();
		marshaller.marshal(xmlIzvod, writer);
		
		renderXml(writer.toString());
		
		
	}
	
	public static void reportPDF(Long id_klijenta, Date from, Date to) throws SQLException, JRException {
		String pdfDir = "public/GenPdf/";
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("id_klijenta", id_klijenta);
		params.put("datum_od", from);
		params.put("datum_do", to);
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poslovna", "isa", "nekasifra");
		
		Date now = new Date();
		String pdfName = "" + now.getTime() + (((Long)Math.round(Math.random() * 1000000))+1000000) + ".pdf";
		String pdfPath = pdfDir + pdfName;
		
		JasperPrint jprint = (JasperPrint)JasperFillManager.fillReport("jasper/izvod_banke.jasper", params, conn);
		JasperExportManager.exportReportToPdfFile(jprint, pdfPath);
		
		redirect("/pdf/" + pdfName);
	}
}
