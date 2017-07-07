package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaKursaUValuti;
import models.KursUValuti;
import models.KursnaLista;
import models.Ukidanje;
import models.Valuta;
import play.mvc.Controller;

/*
 * Nikola radi
 */
public class KurseviUValuti extends Controller {

	public KurseviUValuti() {
		// TODO Auto-generated constructor stub
	}
	
	public static void showDefault() {
		KonstanteSesije.clearFlashConfig(flash);
		if(!KonstanteSesije.filterIsValid(flash, Konstante.IME_ENTITETA_KURS_U_VALUTI, KonstanteSesije.FILTRI_KURSA_U_VALUTI)) {
			KonstanteSesije.clearFlashFilter(flash);
		}
		flash.keep();
		show(Konstante.KONF_IZMJENA, "");
	}
	
	public static void show(String mode, String highlightedId){
		List<KursUValuti> kursevi;
		if(Konstante.IME_ENTITETA_KURS_U_VALUTI.equals(flash.get(KonstanteSesije.TARGET_ENTITY))) {
			String query = "";
			switch(flash.get(KonstanteSesije.FILTER_ENTITY)) {
			case Konstante.IME_ENTITETA_KURSNA_LISTA:
				query = "select k from KursUValuti k where k.kursnaLista.id = ? order by kursnaLista.banka, kursnaLista.brojKursneListe, osnovnaValuta, premaValuti";
				break;
			case Konstante.IME_OSNOVNA_VALUTA:
				query = "select k from KursUValuti k where k.osnovnaValuta.id = ? order by kursnaLista.banka, kursnaLista.brojKursneListe, osnovnaValuta, premaValuti";
				break;
			case Konstante.IME_PREMA_VALUTI:
				query = "select k from KursUValuti k where k.premaValuti.id = ? order by kursnaLista.banka, kursnaLista.brojKursneListe, osnovnaValuta, premaValuti";
				break;
			}
			long filterId = Long.parseLong(flash.get(KonstanteSesije.FILTER_ID)); 
			kursevi = KursUValuti.find(query, filterId).fetch();
		} else {
			kursevi = KursUValuti.find("select k from KursUValuti k order by kursnaLista.banka, kursnaLista.brojKursneListe, osnovnaValuta, premaValuti").fetch();
		}
		List<KursnaLista> kursneListe = KursnaLista.findAll();
		List<Valuta> valute = Valuta.find("select v from Valuta v order by v.zvanicnaSifra").fetch();
		KonstanteSesije.fillFlash(flash, mode, highlightedId);
		render(kursevi, kursneListe, valute);
	}
	public static void create(Long kursnaLista, Long osnovnaValuta, Long premaValuti, 
			BigDecimal kupovni, BigDecimal srednji, BigDecimal prodajni){
		ValidacijaKursaUValuti.validate(validation, kursnaLista, osnovnaValuta,
				premaValuti, kupovni, srednji, prodajni);
		KursnaLista _kursnaLista = KursnaLista.findById(kursnaLista);
		if(_kursnaLista == null) {
			validation.addError("kursnaLista", "Nepostojeca kursna lista");
		}
		Valuta _osnovnaValuta = Valuta.findById(osnovnaValuta);
		if(_osnovnaValuta == null) {
			validation.addError("osnovnaValuta", "Nepostojeca osnovna valuta");
		}
		Valuta _premaValuti = Valuta.findById(premaValuti);
		if(_premaValuti == null) {
			validation.addError("premaValuti", "Nepostojeca valuta prema");
		}
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_DODAVANJE, "");
		} else {
			KursUValuti kurs = new KursUValuti(kupovni,srednji,prodajni);
			kurs.kursnaLista = _kursnaLista;
			kurs.osnovnaValuta = _osnovnaValuta;
			kurs.premaValuti = _premaValuti;
			kurs.save();		
			show(Konstante.KONF_DODAVANJE, kurs.id.toString());
		}
	}
	
	public static void edit(Long id, BigDecimal kupovni, BigDecimal srednji, BigDecimal prodajni){
		KursUValuti kurs = KursUValuti.findById(id);
		if(kurs!=null) {
			ValidacijaKursaUValuti.validate(validation, kupovni, srednji, prodajni);
			if(validation.hasErrors()) {
				validation.keep();
				show(Konstante.KONF_IZMJENA, id.toString());
			} else {
				kurs.kupovni=kupovni;
				kurs.srednji=srednji;
				kurs.prodajni=prodajni;		
				kurs.save();
				show(Konstante.KONF_DODAVANJE,kurs.id.toString());
			}
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KURS_U_VALUTI, id));
		}
	}

	public static void filter(Long kursnaLista, Long osnovnaValuta, Long premaValuti){
		flash.clear();
		
		String query = "select kuv from KursUValuti kuv where ";
		String queryKursnaLista = "kuv.kursnaLista.id = ?";
		String queryOsnovnaValuta = "kuv.osnovnaValuta.id = ?";
		String queryPremaValuti = "kuv.premaValuti.id = ?";
		ArrayList<Long> paramsList = new ArrayList<Long>();
		if(kursnaLista!=null) {
			query += queryKursnaLista;
			paramsList.add(kursnaLista);
		}
		if(osnovnaValuta!=null) {
			query += ((paramsList.size()>0)?(" and "):(""));
			query += queryOsnovnaValuta;
			paramsList.add(osnovnaValuta);
		}
		if(premaValuti!=null) {
			query += ((paramsList.size()>0)?(" and "):(""));
			query += queryPremaValuti;
			paramsList.add(premaValuti);
		}
		List<KursUValuti> kursevi;
		if(paramsList.size() > 0) {
			kursevi = KursUValuti.find(query, paramsList.toArray()).fetch();
		} else {
			kursevi = KursUValuti.all().fetch();
		}
		renderTemplate("KurseviUValuti/show.html", Konstante.KONF_IZMJENA, kursevi);
	}
	public static void delete(Long id){
		KursUValuti kurs = KursUValuti.findById(id);
		if(kurs!=null) {
			kurs.delete();
			String highlightedId = "";
			KursUValuti prviVeci=KursUValuti.find("byIdGreaterThan", id).first();
			   if(prviVeci!=null) {
				    highlightedId = prviVeci.id.toString();
			   } else {
				   KursUValuti prviManji=KursUValuti.find("select k from KursUValuti k where k.id < ? order by id desc", id).first();
				   if(prviManji!=null) {
					    highlightedId = prviManji.id.toString();
				   }	   	
			   }
			   show(flash.get(KonstanteSesije.MODE), highlightedId);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KURS_U_VALUTI, id));
		}
		  
	}
}
