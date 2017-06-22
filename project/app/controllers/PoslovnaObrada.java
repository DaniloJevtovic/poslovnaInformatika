package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.AnalitikaIzvoda;
import models.DnevnoStanjeRacuna;
import models.MedjubankarskiPrenos;
import models.Racun;
import models.StavkaKliringa;

public class PoslovnaObrada {

	public PoslovnaObrada() {
		// TODO Auto-generated constructor stub
	}
	
	//pronadji poslednje dnevno stanje racuna 
	public static Date poslednjeDnevnoStanjeRacuna(Racun racun){	
		List<DnevnoStanjeRacuna> dnevnaStanjaRacuna = 
				DnevnoStanjeRacuna.find("byRacunOrderByDatumPrometa", "%" + racun + "%").fetch();	//redoslijed po datumu prometa
		
		if(dnevnaStanjaRacuna.size() != 0)
			return dnevnaStanjaRacuna.get(dnevnaStanjaRacuna.size() - 1).datumPrometa;
		else
			return null;	
	}
	
	public static void azuriranjeDnevnogStanja(Racun racun, AnalitikaIzvoda analitikaIzvoda){
		Date poslednjiDatum = poslednjeDnevnoStanjeRacuna(racun);
		
		//promet u korist
		if(analitikaIzvoda.smjer == "korist"){
			if (poslednjiDatum == null) {	//novo dnevno stanje
				DnevnoStanjeRacuna dnevnoStanjeRacuna = new DnevnoStanjeRacuna(null, racun, analitikaIzvoda.datumPrijema,
						Long.valueOf(0), analitikaIzvoda.iznos, Long.valueOf(0), analitikaIzvoda.iznos);

				dnevnoStanjeRacuna.save();
				analitikaIzvoda.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
				
			} else {
				//trenutno stanje
				DnevnoStanjeRacuna dnevnoStanjeRacuna = (DnevnoStanjeRacuna) DnevnoStanjeRacuna
						.find("byDatumPrometa", "%" + racun + "%").fetch();
				
				dnevnoStanjeRacuna.prometUKorist += analitikaIzvoda.iznos;
				dnevnoStanjeRacuna.novoStanje += analitikaIzvoda.iznos;

				dnevnoStanjeRacuna.save();
				analitikaIzvoda.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
			}
		}
		
		//promet na teret
		else {
			//novo dnevno stanje
			if (poslednjiDatum == null) {
				DnevnoStanjeRacuna dnevnoStanjeRacuna = new DnevnoStanjeRacuna(null, racun, analitikaIzvoda.datumPrijema,
						Long.valueOf(0), -analitikaIzvoda.iznos, Long.valueOf(0), analitikaIzvoda.iznos);

				dnevnoStanjeRacuna.save();
				analitikaIzvoda.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
				
			} else {//trenutno stanje
				DnevnoStanjeRacuna dnevnoStanjeRacuna = (DnevnoStanjeRacuna) DnevnoStanjeRacuna
						.find("byDatumPrometa", "%" + racun + "%").fetch();
				
				dnevnoStanjeRacuna.prometUKorist += analitikaIzvoda.iznos;
				dnevnoStanjeRacuna.novoStanje -= analitikaIzvoda.iznos;

				dnevnoStanjeRacuna.save();
				analitikaIzvoda.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
			}
		}
		
	}
	
	public static void genNalogaZaMedjubankarskiTransfer(Racun racunPrimaoca, Racun racunDuznika, AnalitikaIzvoda analitikaIzvoda){
		//RTGS
		if(analitikaIzvoda.iznos > 250000){
			
			MedjubankarskiPrenos medjubankarskiPrenos;
			
			if(analitikaIzvoda.smjer == "korist"){
				medjubankarskiPrenos = new MedjubankarskiPrenos(null, analitikaIzvoda.datumPrijema, 
						null, racunDuznika.brojRacuna, null, racunPrimaoca.brojRacuna, analitikaIzvoda.iznos, "103");
				
				medjubankarskiPrenos.save();
				StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenos);
				stavkaKliringa.save();
			}
			else {
				medjubankarskiPrenos = new MedjubankarskiPrenos(null, analitikaIzvoda.datumPrijema, 
						null, racunDuznika.brojRacuna, null, racunPrimaoca.brojRacuna, -analitikaIzvoda.iznos, "103");
				
				medjubankarskiPrenos.save();
				StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenos);
				stavkaKliringa.save();
			}
		}
		
		//kliring
		else {
			List<MedjubankarskiPrenos> medjubankarskiPrenosi;
			
			if(analitikaIzvoda.smjer == "korist")
				medjubankarskiPrenosi = MedjubankarskiPrenos.find("", racunPrimaoca, "102").fetch();
			else
				medjubankarskiPrenosi = MedjubankarskiPrenos.find("", racunDuznika, "102").fetch();
			
			if(medjubankarskiPrenosi.size() == 0){
				MedjubankarskiPrenos medjubankarskiPrenos;	//pravim novi medjubankarski prenos
				
				if(analitikaIzvoda.smjer == "korist")
					medjubankarskiPrenos = new MedjubankarskiPrenos(null, analitikaIzvoda.datumPrijema, null, 
							racunDuznika.brojRacuna, null, racunPrimaoca.brojRacuna, analitikaIzvoda.iznos, "102");
				
				else 
					medjubankarskiPrenos = new MedjubankarskiPrenos(null, analitikaIzvoda.datumPrijema, null, 
							racunDuznika.brojRacuna, null, racunPrimaoca.brojRacuna, -analitikaIzvoda.iznos, "102");
					
				
				medjubankarskiPrenos.save();
				StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenos);
				stavkaKliringa.save();
			}
			
			else {
				if(analitikaIzvoda.smjer == "korist"){
					medjubankarskiPrenosi.get(0).ukupanIznos += analitikaIzvoda.iznos;
					medjubankarskiPrenosi.get(0).save();
					StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenosi.get(0));
					stavkaKliringa.save();
				}
				
				else {
					medjubankarskiPrenosi.get(0).ukupanIznos -= analitikaIzvoda.iznos;
					medjubankarskiPrenosi.get(0).save();
					StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenosi.get(0));
					stavkaKliringa.save();
				}
			}
			
		}
	}
	
	public static void ukidanjeRacuna(){
		
	}
	
	

}
