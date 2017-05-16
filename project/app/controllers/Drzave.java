package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import models.Drzava;
import play.mvc.Controller;

public class Drzave extends Controller{
	
	public Drzave() {
		// TODO Auto-generated constructor stub
	}
	
	public static void show(String mode){
		List<Drzava> drzave = Drzava.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(drzave, mode);
	}
	
	public static void create(String oznaka, String naziv){

		Drzava drzava = new Drzava(oznaka, naziv);
		drzava.save();		
		show("add");
	}
	
	public static void edit(Long id, String oznaka, String naziv){
		Drzava drzava = Drzava.findById(id);
		drzava.oznaka = oznaka;
		drzava.naziv = naziv;
		drzava.save();
		show("edit");
	}
	
	public static void filter(Long id, String oznaka, String naziv){
		List<Drzava> drzave = Drzava.find("byOznakaLikeAndNazivLike", "%" + oznaka + "%", "%" + naziv + "%").fetch();
		String mode = "edit";
		renderTemplate("Drzave/show.html", drzave, mode);
	}
	
	public static void delete(Long id){
		Drzava drzava = Drzava.findById(id);
		drzava.delete();
		show("edit");
	}
	
}
