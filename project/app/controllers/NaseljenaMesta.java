package controllers;

import java.util.*;

import models.Drzava;
import models.NaseljenoMesto;
import play.mvc.*;

public class NaseljenaMesta extends Controller{

	public NaseljenaMesta() {
		// TODO Auto-generated constructor stub
	}
	
	public static void show(String mode){
		List<NaseljenoMesto> naseljenaMesta = NaseljenoMesto.findAll();
		List<Drzava> drzave = Drzava.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(naseljenaMesta, drzave, mode);
	}
	
	public static void create(String oznaka, String naziv, String postanskiBroj, Long drzava){
		Drzava _drzava = Drzava.findById(drzava);
		NaseljenoMesto naseljenoMesto = new NaseljenoMesto(oznaka, naziv, postanskiBroj, _drzava);
		naseljenoMesto.save();
		show("add");
	}
	
	public static void edit(Long id, String oznaka, String naziv, String postanskiBroj, Long drzava){
		NaseljenoMesto naseljenoMesto = NaseljenoMesto.findById(id);
		naseljenoMesto.oznaka = oznaka;
		naseljenoMesto.naziv = naziv;
		naseljenoMesto.postanskiBroj = postanskiBroj;
		Drzava _drzava = Drzava.findById(drzava);
		naseljenoMesto.drzava = _drzava;
		naseljenoMesto.save();
		show("edit");
	}
	
	public static void filter(String oznaka, String naziv, String postanskiBroj, Long drzava) {
		List<NaseljenoMesto> naseljenaMesta = NaseljenoMesto.find("byOznakaLikeAndNazivLikeAndPostanskiBrojLikeAndDrzava_id", "%"+ oznaka +"%", "%"+ naziv +"%", "%"+ postanskiBroj +"%", drzava).fetch();
		List<Drzava> drzave = Drzava.findAll();
		String mode = "edit";
		renderTemplate("NaseljenaMesta/show.html", naseljenaMesta, mode, drzave);
	}
	
	public static void delete(Long id){
		NaseljenoMesto naseljenoMesto = NaseljenoMesto.findById(id);
		naseljenoMesto.delete();
		show("edit");
	}
	
	public static void nextForm(Long drzava_id) {
		List<NaseljenoMesto> naseljenaMesta = NaseljenoMesto.find("byDrzava_id", drzava_id).fetch();
		List<Drzava> drzave = new ArrayList<Drzava>();		
		drzave.add(Drzava.findById(drzava_id));
		String mode = "edit";
		renderTemplate("NaseljenaMesta/show.html", naseljenaMesta, drzave, mode);
	}

}
