package controllers;

import java.util.List;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.helpers.QueryBuilder;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaKlijenta;
import controllers.validation.ValidacijaVrstePlacanja;
import models.Drzava;
import models.Klijent;
import models.KursUValuti;
import models.KursnaLista;
import models.Valuta;
import models.VrstaPlacanja;
import play.mvc.Controller;

/*
 * Nikola radi
 */
public class VrstePlacanja extends Controller {

	public VrstePlacanja() {
		// TODO Auto-generated constructor stub
	}
	
	public static void showDefault() {
		KonstanteSesije.clearFlashConfig(flash);
		KonstanteSesije.clearFlashFilter(flash);
		flash.keep();
		show(Konstante.KONF_IZMJENA, "");
	}
	
	public static void show(String mode, String highlightedId){
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<VrstaPlacanja> placanje = VrstaPlacanja.findAll();
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(placanje);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(String oznakaVrste, String nazivVrstePlacanja){
		ValidacijaVrstePlacanja.validate(validation, oznakaVrste,nazivVrstePlacanja);
		if(validation.hasErrors()) {
			show(Konstante.KONF_DODAVANJE, "");
		} else {
			VrstaPlacanja vrstaPlacanja=new VrstaPlacanja(oznakaVrste,nazivVrstePlacanja);
			vrstaPlacanja.save();
			show(Konstante.KONF_DODAVANJE, vrstaPlacanja.id.toString());
		}
	}
	
	public static void edit(Long id,String oznakaVrste, String nazivVrstePlacanja){
		ValidacijaVrstePlacanja.validate(validation, oznakaVrste,nazivVrstePlacanja);
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_IZMJENA, id.toString());
		} else {
			VrstaPlacanja vrstaPlacanja = VrstaPlacanja.findById(id);
			if(vrstaPlacanja!=null) {
				vrstaPlacanja.oznakaVrste=oznakaVrste;
				vrstaPlacanja.nazivVrstePlacanja=nazivVrstePlacanja;
				vrstaPlacanja.save();
				show(Konstante.KONF_IZMJENA, id.toString());
			} else {
				notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_VRSTA_PLACANJA, id));
			}
		}
	}
	public static void filter(Long id,String oznakaVrste, String nazivVrstePlacanja){
		QueryBuilder queryBuilder=new QueryBuilder();
		queryBuilder.buildLikeQuery("OznakaVrste", oznakaVrste);
		queryBuilder.buildLikeQuery("NazivVrstePlacanja",nazivVrstePlacanja);
		List<VrstaPlacanja> placanje = VrstaPlacanja.find(queryBuilder.getQuery(),queryBuilder.getParams()).fetch();
		renderTemplate("VrstePlacanja/show.html",Konstante.KONF_IZMJENA, placanje);
	}

	public static void delete(Long id){
		VrstaPlacanja placanje = VrstaPlacanja.findById(id);
		if(placanje!=null) {
			placanje.delete();
			String highlightedId = "";
			VrstaPlacanja prviVeci = VrstaPlacanja.find("byIdGreaterThan", id).first();
			if(prviVeci!=null) {
				highlightedId = prviVeci.id.toString();
			} else {
				VrstaPlacanja prviManji = VrstaPlacanja.find("select vp from VrstaPlacanja vp where vp.id < ? order by id desc", id).first();
				if(prviManji!=null) {
					highlightedId = prviManji.id.toString();
				}
			}
			show(flash.get(KonstanteSesije.MODE), highlightedId);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_VRSTA_PLACANJA, id));
		}

	}

	public static void nextAnalitikeIzvoda(String filterId) {
		if(filterId==null || "".equals(filterId)) {
			throw new IllegalStateException();
		}
		KonstanteSesije.insertFlashFilter(
				flash,
				Konstante.IME_ENTITETA_VRSTA_PLACANJA,
				Konstante.IME_ENTITETA_ANALITIKA_IZVODA,
				filterId);
		flash.keep();
		AnalitikeIzvoda.showDefault();
	}
}
