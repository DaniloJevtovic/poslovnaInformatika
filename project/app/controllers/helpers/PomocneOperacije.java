package controllers.helpers;

import java.util.Calendar;
import java.util.Date;

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
	/*
	 * Vraca DateTime podesen na isti datum ali
	 * u 00:00:00
	 */
	public static Date getEndOfDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
	    cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
	    cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
	    cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}
	
	
	/*
	 * Vraca DateTime podesen na isti datum ali
	 * u 23:59:59
	 */
	public static Date getStartOfDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
	    cal.set(Calendar.MINUTE,      cal.getMinimum(Calendar.MINUTE));
	    cal.set(Calendar.SECOND,      cal.getMinimum(Calendar.SECOND));
	    cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}
}
