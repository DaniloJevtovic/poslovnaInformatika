package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import models.AnalitikaIzvoda;
import play.mvc.Controller;

/*
 * Radi Danilo
 */
public class AnalitikeIzvoda extends Controller{

	public AnalitikeIzvoda() {
		// TODO Auto-generated constructor stub
	}
	
	public static void show(String mode){
		List<AnalitikaIzvoda> analitikeIzvoda = AnalitikaIzvoda.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(analitikeIzvoda, mode);
	}
	
	public static void create(Long brojStavke, String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			String datumPrijema, String datumValute, String racunDuznika, Integer modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, Integer modelOdobrenja, String pozNaBrojOdobrenja, Boolean hitno, Long iznos,
			Integer tipGreske, Character status){
		
		//datum prijema
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datumP = null;
		
		try {
			datumP = dateFormat.parse(datumPrijema);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		//datum valute
		DateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
		Date datumV = null;
		
		try {
			datumV = dateFormat1.parse(datumValute);
		} catch (Exception e) {
			System.out.println("Greska!");
		}

		AnalitikaIzvoda analitikaIzvoda = new AnalitikaIzvoda(brojStavke, duzNalogodavac, svrhaPlacanja, povjerPrimalac, 
				datumP, datumV, racunDuznika, modelZaduzenja, pozNaBrojZaduzenja, racunPovjerioca, modelOdobrenja,
				pozNaBrojOdobrenja, hitno, iznos, tipGreske, status);
		
		analitikaIzvoda.save();		
		show("add");
	}
	
	public static void edit(Long id, Long brojStavke, String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			String datumPrijema, String datumValute, String racunDuznika, Integer modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, Integer modelOdobrenja, String pozNaBrojOdobrenja, Boolean hitno, Long iznos,
			Integer tipGreske, Character status){
		
		// datum prijema
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datumP = null;

		try {
			datumP = dateFormat.parse(datumPrijema);
		} catch (Exception e) {
			System.out.println("Greska!");
		}

		// datum valute
		DateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
		Date datumV = null;

		try {
			datumV = dateFormat1.parse(datumValute);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		AnalitikaIzvoda analitikaIzvoda = AnalitikaIzvoda.findById(id);
		analitikaIzvoda.brojStavke = brojStavke;
		analitikaIzvoda.duzNalogodavac = duzNalogodavac;
		analitikaIzvoda.svrhaPlacanja = svrhaPlacanja;
		analitikaIzvoda.povjerPrimalac = povjerPrimalac;
		analitikaIzvoda.datumPrijema = datumP;
		analitikaIzvoda.datumValute = datumV;
		analitikaIzvoda.racunDuznika = racunDuznika;
		analitikaIzvoda.modelZaduzenja = modelZaduzenja;
		analitikaIzvoda.pozNaBrojZaduzenja = pozNaBrojZaduzenja;
		analitikaIzvoda.racunPovjerioca = racunPovjerioca;
		analitikaIzvoda.modelOdobrenja = modelOdobrenja;
		analitikaIzvoda.pozNaBrojOdobrenja = pozNaBrojOdobrenja;
		analitikaIzvoda.hitno = hitno;
		analitikaIzvoda.iznos = iznos;
		analitikaIzvoda.tipGreske = tipGreske;
		analitikaIzvoda.status = status;
		
		analitikaIzvoda.save();
		show("edit");
	}
	
	public static void filter(String brojStavke, String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			String datumPrijema, String datumValute, String racunDuznika, String modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, String modelOdobrenja, String pozNaBrojOdobrenja, String hitno, String iznos,
			String tipGreske, String status){
		
		List<AnalitikaIzvoda> analitikeIzvoda = AnalitikaIzvoda.find("byBrojStavkeLikeAndDuzNalogodavacLikeAndSvrhaPlacanjaLike"
				+ "AndPovjerPrimalacLikeAndRacunDuznikaLikeAndModelZaduzenjaLikeAndPozNaBrojZaduzenjaLikeAndRacunPovjeriocaLike"
				+ "AndModelOdobrenjaLikeAndPozNaBrojOdobrenjaLikeAndIznosLikeAndTipGreskeLikeAndStatusLike", 
				"%" + brojStavke + "%", "%" + duzNalogodavac + "%", "%" + svrhaPlacanja + "%", "%" + povjerPrimalac + "%",
				"%" + racunDuznika + "%", "%" + modelZaduzenja + "%", "%" + pozNaBrojZaduzenja + "%", "%" + racunPovjerioca + "%",
				"%" + modelOdobrenja + "%", "%" + pozNaBrojOdobrenja + "%", "%" + iznos + "%",
				"%" + tipGreske + "%", "%" + status + "%").fetch();
		
		String mode = "edit";
		renderTemplate("AnalitikeIzvoda/show.html", analitikeIzvoda, mode);
	}
	
	public static void delete(Long id){
		AnalitikaIzvoda analitikaIzvoda = AnalitikaIzvoda.findById(id);
		analitikaIzvoda.delete();
		show("edit");
	}

}
