package controllers.session;

import controllers.helpers.Konstante;
import play.mvc.Scope.Flash;
import play.mvc.Scope.Session;

public class KonstanteSesije {
	public static final String MODE = "mode";
	public static final String HIGHLIGHTED_ID = "highlightedId";
	
	public static final String TARGET_ENTITY = "targetEntity";
	public static final String FILTER_ENTITY = "filterEntity";
	public static final String FILTER_ID = "filterId";
	
	public static final String[] FILTRI_ANALITIKE_IZVODA = {Konstante.IME_ENTITETA_VALUTA, Konstante.IME_ENTITETA_VRSTA_PLACANJA
			, Konstante.IME_ENTITETA_NASELJENO_MESTO, Konstante.IME_ENTITETA_DNEVNO_STANJE_U_KORIST, Konstante.IME_ENTITETA_DNEVNO_STANJE_NA_TERET};
	public static final String[] FILTRI_DNEVNOG_STANJA_RACUNA = {Konstante.IME_ENTITETA_RACUN};
	public static final String[] FILTRI_RACUNA = {Konstante.IME_ENTITETA_BANKA, Konstante.IME_ENTITETA_KLIJENT, Konstante.IME_ENTITETA_VALUTA};
	public static final String[] FILTRI_VALUTE = {Konstante.IME_ENTITETA_DRZAVA};
	public static final String[] FILTRI_KURSNE_LISTE = {Konstante.IME_ENTITETA_BANKA};
	public static final String[] FILTRI_KURSA_U_VALUTI = {Konstante.IME_ENTITETA_KURSNA_LISTA, Konstante.IME_OSNOVNA_VALUTA, Konstante.IME_PREMA_VALUTI};

	public static final String[] FILTRI_UKIDANJA = {Konstante.IME_ENTITETA_RACUN};

	
	public static final String[] FILTRI_DRZAVE = {Konstante.IME_ENTITETA_DRZAVA};
	public static final String[] FILTRI_NASELJENA_MJESTA = {Konstante.IME_ENTITETA_NASELJENO_MESTO};
	public static final String[] FILTRI_MEDJUBANKARSKI_PRENOSI = {Konstante.IME_ENTITETA_MEDJUBANKARSKI_PRENOS};
	public static final String[] FILTRI_BANKE = {Konstante.IME_ENTITETA_BANKA};
	
	public static final String[] DOZVOLJENE_KONFIGURACIJE = 
		{ Konstante.KONF_DODAVANJE, Konstante.KONF_IZMJENA, Konstante.KONF_PRETRAGA };
	
	public static void fillFlash(Flash flash, String mode, String highlightedId) {
		clearFlashConfig(flash);
		flash.put(MODE, mode);
		if((highlightedId!=null) && (!highlightedId.equals(""))) {
			flash.put(HIGHLIGHTED_ID, highlightedId);
		} else {
			flash.remove(HIGHLIGHTED_ID);
		}
	}
	
	public static void clearFlashConfig(Flash flash) {
		flash.remove(MODE);
		flash.remove(HIGHLIGHTED_ID);
	}
	
	public static void clearFlashFilter(Flash flash) {
		flash.remove(TARGET_ENTITY);
		flash.remove(FILTER_ENTITY);
		flash.remove(FILTER_ID);
	}
	
	public static boolean filterIsValid(Flash flash, String target, String[] allowedFilters ) {
		boolean result = false;
		if(target.equals(flash.get(TARGET_ENTITY))) {
			String filterEntity = flash.get(FILTER_ENTITY);
			for(int i = 0; i < allowedFilters.length; i++) {
				if(filterEntity.equals(allowedFilters[i])) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	public static void resetSession(Flash flash) {
		flash.clear();
		flash.put(MODE, Konstante.KONF_IZMJENA);
	}
	
	public static void insertFlashFilter(Flash flash, String filter, String target, String id) {
		clearFlashFilter(flash);
		flash.put(TARGET_ENTITY, target);
		flash.put(FILTER_ENTITY, filter);
		flash.put(FILTER_ID, id);
	}
	
}
