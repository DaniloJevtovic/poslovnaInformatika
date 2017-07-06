package controllers.poslovna_obrada;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.example.izvod_klijenta.DnevnaStanjaRacuna;
import org.example.izvod_klijenta.DnevnoStanjeRacuna;
import org.example.izvod_klijenta.FizickoLice;
import org.example.izvod_klijenta.IzvodKlijenta;
import org.example.izvod_klijenta.Klijent;
import org.example.izvod_klijenta.PravnoLice;
import org.example.izvod_klijenta.Racun;
import org.example.izvod_klijenta.Racuni;
import org.example.izvod_klijenta.Transakcija;
import org.example.izvod_klijenta.TransakcijaNaTeret;
import org.example.izvod_klijenta.TransakcijaUKorist;
import org.example.izvod_klijenta.Transakcije;

import models.AnalitikaIzvoda;

public class XmlPunilac {
	
	public static TransakcijaUKorist napuniUKorist(AnalitikaIzvoda analitika) throws DatatypeConfigurationException {
		TransakcijaUKorist uKorist = new TransakcijaUKorist();
		uKorist.setDatumPrijema(dateToXmlGregorianCalendar(analitika.datumPrijema));
		uKorist.setDuznik(analitika.duzNalogodavac);
		uKorist.setHitno(analitika.hitno);
		uKorist.setIdAnalitike(analitika.id);
		uKorist.setIznos(analitika.iznos);
		uKorist.setModelZaduzenja(analitika.modelZaduzenja);
		uKorist.setPozivNaBrojZaduzenja(analitika.pozNaBrojZaduzenja);
		uKorist.setRacunDuznika(analitika.racunDuznika);
		uKorist.setSifraPlacanja(analitika.vrstaPlacanja.oznakaVrste);
		uKorist.setStatus(analitika.status);
		uKorist.setSvrhaPlacanja(analitika.svrhaPlacanja);
		uKorist.setTipGreske(analitika.tipGreske);
		return uKorist;
	}
	
	public static TransakcijaNaTeret napuniNaTeret(AnalitikaIzvoda analitika) throws DatatypeConfigurationException {
		TransakcijaNaTeret naTeret = new TransakcijaNaTeret();
		naTeret.setDatumPrijema(dateToXmlGregorianCalendar(analitika.datumPrijema));
		naTeret.setPovjerilac(analitika.povjerPrimalac);
		naTeret.setHitno(analitika.hitno);
		naTeret.setIdAnalitike(analitika.id);
		naTeret.setIznos(analitika.iznos);
		naTeret.setModelOdobrenja(analitika.modelOdobrenja);
		naTeret.setPozivNaBrojOdobrenja(analitika.pozNaBrojOdobrenja);
		naTeret.setRacunPovjerioca(analitika.racunPovjerioca);
		naTeret.setSifraPlacanja(analitika.vrstaPlacanja.oznakaVrste);
		naTeret.setStatus(analitika.status);
		naTeret.setSvrhaPlacanja(analitika.svrhaPlacanja);
		naTeret.setTipGreske(analitika.tipGreske);
		return naTeret;
	}
	
	public static DnevnoStanjeRacuna napuniDnevnoStanje(models.DnevnoStanjeRacuna dsr) throws DatatypeConfigurationException {
		DnevnoStanjeRacuna xmlDsr = new DnevnoStanjeRacuna();
		xmlDsr.setDatum(dateToXmlGregorianCalendar(dsr.datumPrometa));
		xmlDsr.setIdDnevnogStanja(dsr.id);
		xmlDsr.setNovoStanje(dsr.novoStanje);
		xmlDsr.setPrethodnoStanje(dsr.predhodnoStanje);
		xmlDsr.setPrometNaTeret(dsr.prometNaTeret);
		xmlDsr.setPrometUKorist(dsr.prometUKorist);
		
		Transakcije transakcije = new Transakcije();
		xmlDsr.setTransakcije(transakcije);
		
		List<Transakcija> xmlListaTransakcija = transakcije.getTransakcija();
		
		for (AnalitikaIzvoda uKorist : dsr.analitikeUKorist) {
			TransakcijaUKorist transUKorist = napuniUKorist(uKorist);
			xmlListaTransakcija.add(transUKorist);
		}
		for (AnalitikaIzvoda naTeret : dsr.analitikeNaTeret) {
			TransakcijaNaTeret transNaTeret = napuniNaTeret(naTeret);
			xmlListaTransakcija.add(transNaTeret);
		}
		return xmlDsr;
	}
	
	
	public static Racun napuniRacun(models.Racun racun) throws DatatypeConfigurationException {
		Racun xmlRacun = new Racun();
		xmlRacun.setBanka(racun.banka.nazivBanke);
		xmlRacun.setBrojRacuna(racun.brojRacuna);
		xmlRacun.setDatumOtvaranja(dateToXmlGregorianCalendar(racun.datumOtvaranja));
		xmlRacun.setIdRacuna(racun.id);
		xmlRacun.setValuta(racun.valuta.zvanicnaSifra);
		xmlRacun.setVazeci(racun.vazeci);
		
		//dodavanje dnevnih stanja
		DnevnaStanjaRacuna xmlDnevnaStanja = new DnevnaStanjaRacuna();
		xmlRacun.setDnevnaStanja(xmlDnevnaStanja);
		
		//lista dnevnih stanja
		List<DnevnoStanjeRacuna> xmlListaDnevnihStanja = xmlDnevnaStanja.getDnevnoStanje();
		
		for (models.DnevnoStanjeRacuna dsr : racun.dnevniIzvodiBanke) {
			DnevnoStanjeRacuna xmlDsr = napuniDnevnoStanje(dsr);
			xmlListaDnevnihStanja.add(xmlDsr);
		}
		return xmlRacun;
	}
	
	public static Klijent napuniKlijenta(models.Klijent klijent) {
		if(klijent.tipKlijenta.equals("P")) {
			PravnoLice pLice = new PravnoLice();
			pLice.setAdresa(klijent.adrKlijenta);
			pLice.setEmail(klijent.emailKlijenta);
			pLice.setFaks(klijent.faksKlijenta);
			pLice.setIdKlijenta(klijent.id);
			pLice.setIme(klijent.imeKlijenta);
			pLice.setPrezime(klijent.przKlijenta);
			pLice.setNaziv(klijent.nazivKlijenta);
			pLice.setPib(klijent.pibKlijenta);
			pLice.setTelefon(klijent.telKlijenta);
			pLice.setWeb(klijent.webKlijenta);
			return pLice;
		} else {
			FizickoLice fLice = new FizickoLice();
			fLice.setAdresa(klijent.adrKlijenta);
			fLice.setEmail(klijent.emailKlijenta);
			fLice.setFaks(klijent.faksKlijenta);
			fLice.setIdKlijenta(klijent.id);
			fLice.setIme(klijent.imeKlijenta);
			fLice.setPrezime(klijent.przKlijenta);
			fLice.setPib(klijent.pibKlijenta);
			fLice.setTelefon(klijent.telKlijenta);
			fLice.setWeb(klijent.webKlijenta);
			return fLice;
		}
	}
	
	public static XMLGregorianCalendar dateToXmlGregorianCalendar(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
	}
	
	public static IzvodKlijenta napuniXML(models.Klijent klijent, Date from, Date to) throws DatatypeConfigurationException {
		IzvodKlijenta xmlIzvod = new IzvodKlijenta();
		
		//dodavanje datuma od
		xmlIzvod.setDatumOd(dateToXmlGregorianCalendar(from));
		
		//dodavanje datuma do
		xmlIzvod.setDatumDo(dateToXmlGregorianCalendar(to));
		
		//dodavanje klijenta
		xmlIzvod.setKlijent(napuniKlijenta(klijent));
		
		//dodavanje racuna
		Racuni xmlRacuni = new Racuni();
		xmlIzvod.setRacuni(xmlRacuni);
		
		//lista racuna unutar klase racuni
		List<Racun> xmlListaRacuna = xmlRacuni.getRacun();
		
		//punjenje racuna
		for(models.Racun racun : klijent.racuni) {
			Racun xmlRacun = napuniRacun(racun);
			xmlListaRacuna.add(xmlRacun);
		}
		
		return xmlIzvod;
	}
}
