package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.scene.control.DatePicker;
import models.DnevnoStanjeRacuna;
import play.mvc.Controller;

/*
 * Danilo radi
 */
public class DnevnaStanjaRacuna extends Controller {

	public DnevnaStanjaRacuna() {
		// TODO Auto-generated constructor stub
	}
	
	public static void show(String mode){
		List<DnevnoStanjeRacuna> dnevnaStanjaRacuna = DnevnoStanjeRacuna.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(dnevnaStanjaRacuna, mode);
	}
	
	public static void create(Long brojIzvoda, String datumPrometa, Long predhodnoStanje, Long prometUKorist,
			Long prometNaTeret, Long novoStanje){
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datum = null;
		
		try {
			datum = dateFormat.parse(datumPrometa);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		DnevnoStanjeRacuna dnevnoStanjeRacuna = new DnevnoStanjeRacuna(brojIzvoda, datum, predhodnoStanje, prometUKorist,
				prometNaTeret, novoStanje);
		dnevnoStanjeRacuna.save();		
		show("add");
	}
	
	public static void edit(Long id, Long brojIzvoda, String datumPrometa, Long predhodnoStanje, Long prometUKorist,
			Long prometNaTeret, Long novoStanje){
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date datum = null;
		
		try {
			datum = dateFormat.parse(datumPrometa);
		} catch (Exception e) {
			System.out.println("Greska!");
		}
		
		DnevnoStanjeRacuna dnevnoStanjeRacuna = DnevnoStanjeRacuna.findById(id);	
		dnevnoStanjeRacuna.brojIzvoda = brojIzvoda;
		dnevnoStanjeRacuna.datumPrometa = datum;
		dnevnoStanjeRacuna.predhodnoStanje = predhodnoStanje;
		dnevnoStanjeRacuna.prometUKorist = prometUKorist;
		dnevnoStanjeRacuna.prometNaTeret = prometNaTeret;
		dnevnoStanjeRacuna.novoStanje = novoStanje;

		dnevnoStanjeRacuna.save();
		show("edit");
	}
	
	public static void filter(String brojIzvoda, String datumPrometa, String predhodnoStanje, String prometUKorist,
			String prometNaTeret, String novoStanje){
		List<DnevnoStanjeRacuna> dnevnaStanjaRacuna =DnevnoStanjeRacuna.find("byBrojIzvodaLikeAndPredhodnoStanjeLikeAndPrometUKoristLikeAndPrometNaTeretLikeAndNovoStanjeLike", 
				"%" + brojIzvoda + "%", "%" + predhodnoStanje + "%", "%" + prometUKorist + "%", "%" + prometNaTeret + "%", "%" + novoStanje + "%").fetch();
		String mode = "edit";
		renderTemplate("DnevnaStanjaRacuna/show.html", dnevnaStanjaRacuna, mode);
	}
	
	public static void delete(Long id){
		DnevnoStanjeRacuna dnevnoStanjeRacuna = DnevnoStanjeRacuna.findById(id);
		dnevnoStanjeRacuna.delete();
		show("edit");
	}

}
