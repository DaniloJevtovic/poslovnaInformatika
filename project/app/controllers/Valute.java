package controllers;

import java.util.List;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import models.Valuta;
import play.mvc.Controller;

/*
 * Stevan radi
 */
public class Valute extends Controller {

	public Valute() {
		// prazno
	}
	
	public static void showDefault() {
		show(Konstante.KONF_IZMJENA);
	}
	
	public static void show(String mode) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<Valuta> valute = Valuta.findAll();
			render(mode, valute);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(String zvanicnaSifra, String nazivValute) {
		validation.required(zvanicnaSifra);
		validation.match(zvanicnaSifra, "[A-Z]{3}");
		validation.match(nazivValute, "([a-zA-Z]|č|Č|ć|Ć|đ|Đ|š|Š|ž|Ž| )*");
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_IZMJENA);
		}
		Valuta valuta = new Valuta(zvanicnaSifra, nazivValute);
		valuta.save();
		show(Konstante.KONF_DODAVANJE);
	}

	public static void edit(Long id, String zvanicnaSifra, String nazivValute) {
		validation.required(zvanicnaSifra);
		validation.match(zvanicnaSifra, "[A-Z]{3}");
		validation.match(nazivValute, "([a-zA-Z]|č|Č|ć|Ć|đ|Đ|š|Š|ž|Ž| )*");
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_IZMJENA);
		}
		Valuta valuta = Valuta.findById(id);
		if(valuta!=null) {
			valuta.zvanicnaSifra = zvanicnaSifra;
			valuta.nazivValute = nazivValute;
			valuta.save();
			show(Konstante.KONF_IZMJENA);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_VALUTA, id));
		}
	}
	
	public static void delete(Long id) {
		Valuta valuta = Valuta.findById(id);
		if(valuta!=null) {
			valuta.delete();
			show(Konstante.KONF_IZMJENA);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_VALUTA, id));
		}
	}
	
	public static void filter(String zvanicnaSifra, String nazivValute) {
		List<Valuta> valute = Valuta.find("byZvanicnaSifraAndNazivValute", zvanicnaSifra, nazivValute).fetch();
		renderTemplate("Valute/show.html", Konstante.KONF_IZMJENA, valute);
	}
}
