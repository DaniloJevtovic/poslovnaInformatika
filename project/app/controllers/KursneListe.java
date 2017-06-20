package controllers;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import controllers.helpers.Konstante;
import controllers.helpers.PomocneOperacije;
import controllers.helpers.QueryBuilder;
import controllers.session.KonstanteSesije;
import controllers.validation.ValidacijaKursneListe;
import models.Banka;
import models.KursnaLista;
import models.Valuta;
import play.data.binding.As;
import play.mvc.Controller;

/*
 * Stevan radi
 */
public class KursneListe extends Controller {

	public KursneListe() {
		// prazno
	}
	
	public static void showDefault() {
		if(!KonstanteSesije.filterIsValid(flash, Konstante.IME_ENTITETA_KURSNA_LISTA, KonstanteSesije.FILTRI_KURSNE_LISTE)) {
			KonstanteSesije.resetSession(flash);
		}
		show(Konstante.KONF_IZMJENA, "");
	}
	
	public static void show(String mode, String highlightedId) {
		if(PomocneOperacije.konfiguracijaJeDozvoljena(mode)) {
			List<KursnaLista> kursneListe;
			if(Konstante.IME_ENTITETA_KURSNA_LISTA.equals(flash.get(KonstanteSesije.FILTER_ENTITY))) {
				String query = "";
				switch(flash.get(KonstanteSesije.FILTER_ENTITY)) {
				case Konstante.IME_ENTITETA_BANKA:
					query = "select kl from KursnaLista kl where kl.banka.id = ?";
					break;
				}
				kursneListe = Valuta.find(query, flash.get(KonstanteSesije.FILTER_ID)).fetch();
			} else {
				kursneListe = KursnaLista.findAll();
			}
			List<Banka> banke = Banka.findAll();
			KonstanteSesije.fillFlash(flash, mode, highlightedId);
			render(mode, kursneListe, banke);
		} else {
			throw new IllegalArgumentException(PomocneOperacije.porukaNevazecaKonfiguracija(mode));
		}
	}
	
	public static void create(Date datumKursneListe, int brojKursneListe, Date primSeOd, Date primSeDo, Long banka) {
		ValidacijaKursneListe.validate(validation, brojKursneListe, datumKursneListe, primSeOd, primSeDo);
		Banka _banka = Banka.findById(banka);
		if(_banka==null) {
			validation.addError("banka", "Banka nije pronadjena");
		}
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_DODAVANJE, "");
		}
		KursnaLista kursnaLista = new KursnaLista(brojKursneListe, datumKursneListe, primSeOd, primSeDo);
		kursnaLista.banka = _banka;
		kursnaLista.save();
		show(Konstante.KONF_DODAVANJE, kursnaLista.id.toString());
	}
	
	public static void edit(Long id, Date datumKursneListe, int brojKursneListe, Date primSeOd, Date primSeDo) {
		ValidacijaKursneListe.validate(validation, brojKursneListe, datumKursneListe, primSeOd, primSeDo);
		if(validation.hasErrors()) {
			validation.keep();
			show(Konstante.KONF_IZMJENA, id.toString());
		}
		KursnaLista kursnaLista = KursnaLista.findById(id);
		if(kursnaLista!=null) {
			kursnaLista.datumKursneListe = datumKursneListe;
			kursnaLista.brojKursneListe = brojKursneListe;
			kursnaLista.primSeOd = primSeOd;
			kursnaLista.primSeDo = primSeDo;
			kursnaLista.save();
			show(Konstante.KONF_IZMJENA, id.toString());
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KURSNA_LISTA, id));
		}
	}
	
	public static void delete(Long id) {
		KursnaLista kursnaLista = KursnaLista.findById(id);
		if(kursnaLista != null) {
			kursnaLista.delete();
			String highlightedId = "";
			KursnaLista prvaVeca = KursnaLista.find("byIdGreaterThan", id).first();
			if(prvaVeca!=null) {
				highlightedId = prvaVeca.id.toString();
			} else {
				KursnaLista prvaManja = KursnaLista.find("select kl from KursnaLista kl where kl.id < ? order by id desc", id).first();
				if(prvaManja!=null) {
					highlightedId = prvaManja.id.toString();
				}
			}
			show(flash.get(KonstanteSesije.MODE), highlightedId);
		} else {
			notFound(PomocneOperacije.porukaNijePronadjen(Konstante.IME_ENTITETA_KURSNA_LISTA, id));
		}
	}
	
	public static void filter(
			Integer brojKursneListeManjeJednako, 
			Integer brojKursneListeVeceJednako,
			Date datumKursneListeManjeJednako,
			Date datumKursneListeVeceJednako,
			Date primSeOdManjeJednako,
			Date primSeOdVeceJednako,
			Date primSeDoManjeJednako,
			Date primSeDoVeceJednako) {
		flash.clear();
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.buildLessThanEqualsQuery("BrojKursneListe", brojKursneListeManjeJednako);
		queryBuilder.buildGreaterThanEqualsQuery("BrojKursneListe", brojKursneListeVeceJednako);
		queryBuilder.buildLessThanEqualsQuery("DatumKursneListe", datumKursneListeManjeJednako);
		queryBuilder.buildGreaterThanEqualsQuery("DatumKursneListe", datumKursneListeVeceJednako);
		queryBuilder.buildLessThanEqualsQuery("PrimSeOd", primSeOdManjeJednako);
		queryBuilder.buildGreaterThanEqualsQuery("PrimSeOd", primSeOdVeceJednako);
		queryBuilder.buildLessThanEqualsQuery("PrimSeDo", primSeDoManjeJednako);
		queryBuilder.buildGreaterThanEqualsQuery("PrimSeDo", primSeDoVeceJednako);
		List<KursnaLista> kursneListe = KursnaLista.find(queryBuilder.getQuery(), queryBuilder.getParams()).fetch();
		renderTemplate("KursneListe/show.html", kursneListe);
	}

}
