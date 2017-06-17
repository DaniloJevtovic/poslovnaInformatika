package controllers;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import models.KursnaLista;
import models.Valuta;
import play.mvc.Controller;

/*
 * Stevan radi
 */
public class KursneListe extends Controller {

	public KursneListe() {
		// prazno
	}
	
	public static void showDefault() {
		show(Konstante.KONF_IZMJENA);
	}
	
	public static void show(String mode) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<KursnaLista> kursneListe = KursnaLista.findAll();
			render(mode, kursneListe);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(Date datumKursneListe, int brojKursneListe, Date primSeOd, Date primSeDo) {
		KursnaLista kursnaLista = new KursnaLista(brojKursneListe, datumKursneListe, primSeOd, primSeDo);
		kursnaLista.save();
		show(Konstante.KONF_DODAVANJE);
	}
	
	public static void edit(Long id, Date datumKursneListe, int brojKursneListe, Date primSeOd, Date primSeDo) {
		KursnaLista kursnaLista = KursnaLista.findById(id);
		if(kursnaLista!=null) {
			kursnaLista.datumKursneListe = datumKursneListe;
			kursnaLista.brojKursneListe = brojKursneListe;
			kursnaLista.primSeOd = primSeOd;
			kursnaLista.primSeDo = primSeDo;
			kursnaLista.save();
			show(Konstante.KONF_IZMJENA);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KURSNA_LISTA, id));
		}
	}
	
	public static void delete(Long id) {
		KursnaLista kursnaLista = KursnaLista.findById(id);
		if(kursnaLista != null) {
			kursnaLista.delete();
			show(Konstante.KONF_IZMJENA);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KURSNA_LISTA, id));
		}
	}
	
	public static void filter() {
		throw new NotImplementedException();
	}

}
