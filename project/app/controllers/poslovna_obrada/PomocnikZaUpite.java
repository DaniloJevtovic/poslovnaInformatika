package controllers.poslovna_obrada;

import java.math.BigDecimal;
import java.util.Date;

import org.example.instrumenti_placanja.SredstvoPlacanja;

import controllers.helpers.PomocneOperacije;
import controllers.validation.ValidacijaKlijenta;
import models.AnalitikaIzvoda;
import models.DnevnoStanjeRacuna;
import models.Klijent;
import models.Racun;
import models.VrstaPlacanja;
import models.constants.KonstanteKlijenta;

class PomocnikZaUpite {
	public static boolean postojeDnevnaStanja() {
		return DnevnoStanjeRacuna.count() != 0;
	}
	
	public static Date datumZaSredstvo(SredstvoPlacanja sp) {
		 return (sp.getDatumIzvrsenja()!=null) ?
				(PomocneOperacije.getStartOfDay(sp.getDatumIzvrsenja().toGregorianCalendar().getTime())) : 
				(PomocneOperacije.getStartOfDay(new Date()));
	}
	
	public static DnevnoStanjeRacuna posljednjeDnevnoStanjeZaRacun(Racun racun, HronoloskoOkruzenje ho) {
		Date danas = ho.danasnjiDan();
		DnevnoStanjeRacuna dnevnoStanje = DnevnoStanjeRacuna.find("byRacunAndDatumPrometa", racun, danas).first();
		if(dnevnoStanje == null) {
			DnevnoStanjeRacuna prethodnoStanje = DnevnoStanjeRacuna.find(
					"select dsr from DnevnoStanjeRacuna dsr where dsr.racun = ? and dsr.datumPrometa < ? order by datumPrometa desc"
					, racun, danas).first();
			if(prethodnoStanje!=null) {
				dnevnoStanje = new DnevnoStanjeRacuna(
						racun, danas, prethodnoStanje.novoStanje,
						BigDecimal.ZERO, BigDecimal.ZERO, prethodnoStanje.novoStanje);
			} else {
				dnevnoStanje = new DnevnoStanjeRacuna(
						racun, danas, BigDecimal.ZERO, BigDecimal.ZERO, 
						BigDecimal.ZERO, BigDecimal.ZERO);
			}
		}
		return dnevnoStanje;
	}
	
	public static VrstaPlacanja pronadjiVrstuPoSifri(String sifraVrste) {
		return VrstaPlacanja.find("byOznakaVrste", sifraVrste).first();
	}
	
	public static Racun pronadjiRacunPoBroju(String brojRacuna) {
		return Racun.find("byBrojRacuna", brojRacuna).first();
	}
	
}
