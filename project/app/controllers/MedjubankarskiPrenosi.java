package controllers;

import java.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.MedjubankarskiPrenos;
import play.mvc.Controller;

/*
 * Danilo radi
 */
public class MedjubankarskiPrenosi extends Controller {

	public MedjubankarskiPrenosi() {
		// TODO Auto-generated constructor stub
	}
	
	public static void show(String mode){
		List<MedjubankarskiPrenos> medjubankarskiPrenosi = MedjubankarskiPrenos.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(medjubankarskiPrenosi, mode);
	}
	
	public static void create(String idKliringa, String datumKliringa, String swiftKodBankeDuz, String racBankeDuz,
			String swiftKodBankePovj, String racBankePovj, Long ukupanIznos, String vrstaPrenosa){
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datum = null;
		
		try {
			datum = dateFormat.parse(datumKliringa);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		MedjubankarskiPrenos medjubankarskiPrenos = new MedjubankarskiPrenos(idKliringa, datum, swiftKodBankeDuz, racBankeDuz, 
				swiftKodBankePovj, racBankePovj, ukupanIznos, vrstaPrenosa);
		medjubankarskiPrenos.save();		
		show("add");
	}
	
	public static void edit(Long id, String idKliringa, String datumKliringa, String swiftKodBankeDuz, String racBankeDuz,
			String swiftKodBankePovj, String racBankePovj, Long ukupanIznos, String vrstaPrenosa){
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datum = null;
		
		try {
			datum = dateFormat.parse(datumKliringa);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		MedjubankarskiPrenos medjubankarskiPrenos = MedjubankarskiPrenos.findById(id);
		medjubankarskiPrenos.idKliringa = idKliringa;
		medjubankarskiPrenos.datumKliringa = datum;
		medjubankarskiPrenos.swiftKodBankeDuz = swiftKodBankeDuz;
		medjubankarskiPrenos.racBankeDuz = racBankeDuz;
		medjubankarskiPrenos.swiftKodBankePovj = racBankePovj;
		medjubankarskiPrenos.ukupanIznos = ukupanIznos;
		medjubankarskiPrenos.vrstaPrenosa = vrstaPrenosa;

		medjubankarskiPrenos.save();
		show("edit");
	}
	
	public static void filter(String idKliringa, String datumKliringa, String swiftKodBankeDuz, String racBankeDuz,
			String swiftKodBankePovj, String racBankePovj, String ukupanIznos, String vrstaPrenosa){
		
		List<MedjubankarskiPrenos> medjubankarskiPrenosi = MedjubankarskiPrenos.find("byIdKliringaLikeAndDatumKliringaLikeAndSwiftKodBankeDuzLikeAndRacBankeDuzLikeAndSwiftKodBankePovjLikeAndRacBankePovjLikeAndUkupanIznosLikeAndVrstaPrenosaLike",
				"%" + idKliringa + "%", "%" + datumKliringa + "%", "%" + swiftKodBankeDuz + "%", "%" + racBankeDuz + "%",
				"%" + swiftKodBankePovj + "%", "%" + racBankePovj + "%", "%" + ukupanIznos + "%", "%" + vrstaPrenosa + "%").fetch();
		String mode = "edit";
		renderTemplate("MedjubankarskiPrenosi/show.html", medjubankarskiPrenosi, mode);
	}
	
	public static void delete(Long id){
		MedjubankarskiPrenos medjubankarskiPrenos = MedjubankarskiPrenos.findById(id);
		medjubankarskiPrenos.delete();
		show("edit");
	}

}
