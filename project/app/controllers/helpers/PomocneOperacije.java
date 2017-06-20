package controllers.helpers;

import models.Klijent;
import play.db.jpa.Model;

public class PomocneOperacije {
	//provjera da li string kojim se u kontroleru zadaje konfiguracija ima 
	//dozvoljenu vrijednost
	public static final boolean konfiguracijaJeDozvoljena(String mode) {
		for(int i = 0; i < Konstante.DOZVOLJENE_KONFIGURACIJE.length; i++) {
			if(mode.equals(Konstante.DOZVOLJENE_KONFIGURACIJE[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static final String porukaNijePronadjen(String imeEntiteta, Long id) {
		return "Primjerak entiteta '" + imeEntiteta + "' sa id '" + id + "' nije pronadjen";
	}
	
	public static final String porukaNevazecaKonfiguracija(String mode) {
		return "Neispravna konfiguracija '" + mode + "'";
	}
	
	public static final String dataToQueryParam(String data) {
		if(data!=null) {
			return "%" + data.trim().toLowerCase() + "%";
		} else {
			return "%%";
		}
	}
	
	public static final boolean isNullOrEmpty(String string) {
		if(string == null) {
			return true;
		}
		return (string.trim().equals(""));
	}
}
