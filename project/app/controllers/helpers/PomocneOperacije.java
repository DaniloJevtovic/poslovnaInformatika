package controllers.helpers;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import models.DnevnoStanjeRacuna;
import models.Klijent;
import models.Racun;

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
	
	public static DnevnoStanjeRacuna posljednjeDnevnoStanje(Racun racun) {
		DnevnoStanjeRacuna dsr  = DnevnoStanjeRacuna.find("select dsr from DnevnoStanjeRacuna dsr where dsr.racun = ? order by dsr.datumPrometa desc", racun).first();
		return dsr;
	}
	
	public static DnevnoStanjeRacuna danasnjeDnevnoStanje(Racun racun) {
		Date danas = getStartOfDay(new Date());
		DnevnoStanjeRacuna dsr  = DnevnoStanjeRacuna.find("select dsr from DnevnoStanjeRacuna dsr where dsr.racun = ? and dsr.datumPrometa = ?", racun, danas).first();
		if(dsr==null) {
			DnevnoStanjeRacuna prethodno = posljednjeDnevnoStanje(racun);
			if(prethodno!=null) {
				dsr = new DnevnoStanjeRacuna(racun, danas, prethodno.novoStanje, BigDecimal.ZERO, BigDecimal.ZERO, prethodno.novoStanje);
			} else {
				dsr = new DnevnoStanjeRacuna(racun, danas, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
			}
		}
		return dsr;
	}
	
	public static String nazivKlijenta(Klijent k) {
		if(k.tipKlijenta.equals("P")) {
			return k.nazivKlijenta;
		} else {
			return k.imeKlijenta + " " + k.przKlijenta;
		}
	}
}
