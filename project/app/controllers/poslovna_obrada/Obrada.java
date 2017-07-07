package controllers.poslovna_obrada;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.example.instrumenti_placanja.InstrumentiPlacanja;
import org.example.instrumenti_placanja.NalogZaIsplatu;
import org.example.instrumenti_placanja.NalogZaPrenos;
import org.example.instrumenti_placanja.NalogZaUplatu;
import org.example.instrumenti_placanja.SredstvoPlacanja;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import controllers.helpers.PomocneOperacije;
import controllers.validation.ValidacijaKlijenta;
import models.AnalitikaIzvoda;
import models.DnevnoStanjeRacuna;
import models.Klijent;
import models.MedjubankarskiPrenos;
import models.Racun;
import models.StavkaKliringa;
import models.VrstaPlacanja;

public class Obrada {
	private XmlObradjivac xmlObradjivac;
	private Biljeznik biljeznik;
	private HronoloskoOkruzenje okruzenje;
	private ValidacijaSredstvaPlacanja validacija;
	
	private class ValidacijaSredstvaPlacanja {
		
		public boolean provjeriDatumPrijema(SredstvoPlacanja sp) {
			Date danasPocetak = okruzenje.danasnjiDan();
			Date danasKraj = PomocneOperacije.getEndOfDay(danasPocetak);
			if(sp.getDatumPrijema().toGregorianCalendar().getTime().after(danasKraj)) {
				biljeznik.dodajTekstNeispravanDatumPrijema(
						sp.getDatumPrijema().toGregorianCalendar().getTime(),
						danasPocetak);
				return false;
			}
			return true;
		}
		
		public boolean provjeriDatumIzvrsenja(SredstvoPlacanja sp) {
			Date danasPocetak = okruzenje.danasnjiDan();
			Date danasKraj = PomocneOperacije.getEndOfDay(danasPocetak);
			Date datumIzvrsenja = PomocnikZaUpite.datumZaSredstvo(sp);
			if(datumIzvrsenja.before(danasPocetak)) {
				biljeznik.dodajTekstDatumIzvrsenjaUProslosti(datumIzvrsenja, danasPocetak);
				return false; 
			}
			if(datumIzvrsenja.after(danasKraj)) {
				biljeznik.dodajTekstDatumIzvrsenjaUBuducnosti(datumIzvrsenja, danasPocetak);
				return false;
			}
			return true;
		}
		
		public boolean provjeriVrstuPlacanja(VrstaPlacanja vrstaPlacanja, String sifraPlacanja) {
			if(vrstaPlacanja == null) {
				biljeznik.dodajTekstNeispravnaSifraPlacanja(sifraPlacanja);
				return false;
			}
			return true;
		}
		
		public boolean provjeriRacunDuznika(String brojRacuna, Racun racunDuznika, String sifraValute) {
			if(racunDuznika == null) {
				biljeznik.dodajTekstNeispravanBrojRacunaDuznika(brojRacuna);
				return false;
			}
			if(!racunDuznika.vazeci) {
				biljeznik.dodajTekstNevazeciRacunDuznika(racunDuznika.brojRacuna);
				return false;
			}
			if(!racunDuznika.valuta.zvanicnaSifra.equals(sifraValute)) {
				biljeznik.dodajTekstNeispravnaValutaRacunaDuznika(brojRacuna, racunDuznika.valuta.zvanicnaSifra, sifraValute);
				return false;
			}
			return true;
		}
		
		public boolean provjeriRacunPovjerioca(String brojRacuna, Racun racunPovjerioca, String sifraValute) {
			if(racunPovjerioca == null) {
				biljeznik.dodajTekstNeispravanBrojRacunaPovjerioca(brojRacuna);
				return false;
			}
			if(!racunPovjerioca.valuta.zvanicnaSifra.equals(sifraValute)) {
				biljeznik.dodajTekstNevazeciRacunPovjerioca(racunPovjerioca.brojRacuna);
				return false;
			}
			if(!racunPovjerioca.vazeci) {
				biljeznik.dodajTekstNeispravnaValutaRacunaPovjerioca(brojRacuna, racunPovjerioca.valuta.zvanicnaSifra, sifraValute);
				return false;
			}
			return true;
		}
		
		public String nazivKlijentaRacuna(Racun racun) {
			Klijent klijent = racun.klijent;
			if(klijent.tipKlijenta.equals(ValidacijaKlijenta.FIZICKO_LICE)) {
				return klijent.imeKlijenta + " " + klijent.przKlijenta;
			} else {
				return klijent.nazivKlijenta;
			}
		}
		
		public boolean provjeriPosljednjeDnevnoStanjeDuznika(
				DnevnoStanjeRacuna posljednjeStanjeDuznika, BigDecimal iznos) {
			if(posljednjeStanjeDuznika.novoStanje.compareTo(iznos) < 0) {
				biljeznik.dodajTekstNedovoljanIznos(
					posljednjeStanjeDuznika.racun.brojRacuna, 
					iznos, 
					posljednjeStanjeDuznika.novoStanje);
				return false;
			}
			return true;
		}
	}
	
	public Obrada() {
		xmlObradjivac = new XmlObradjivac();
		biljeznik = new Biljeznik();
		validacija = new ValidacijaSredstvaPlacanja();
	}
	
	public void obradi(File xmlPodaci) {
		InstrumentiPlacanja instrumenti;
		biljeznik.dodajPocetnuLiniju();
		biljeznik.dodajLinijuCitanje();
		biljeznik.dodajLiniju();
		try {
			instrumenti = xmlObradjivac.ucitajInstrumentePlacanja(xmlPodaci);
		} catch(JAXBException | SAXException e) {
			biljeznik.dodajLinijuKraj();
			biljeznik.dodajRazlogNeotklonjiveGreske("Datoteka nije u odgovarajucem formatu.");
			return;
		}
		
		biljeznik.dodajLinijuProcitano(instrumenti.getInstrumentPlacanja().size());
		biljeznik.dodajLiniju();
		
		List<SredstvoPlacanja> sredstvaPlacanja = instrumenti.getInstrumentPlacanja();
		
		if(PomocnikZaUpite.postojeDnevnaStanja()) {
			obradiSredstvaPlacanja(sredstvaPlacanja);
		} else {
			obradiUHronoloskomRedu(sredstvaPlacanja);
		}
	}

	private void obradiUHronoloskomRedu(List<SredstvoPlacanja> sredstvaPlacanja) {
		biljeznik.dodajLiniju("Upozorenje:");
		biljeznik.dodajLiniju("U bazi ne postoje dnevna stanja");
		biljeznik.dodajLiniju("Instrumenti placanja ce se obraditi u hronoloskom redu da bi bilo sto manje odbijenih");
		biljeznik.dodajLiniju("Usvaja se konvencija da je danasnji datum jednak datumu izvrsenja instrumenta placanja");
		biljeznik.dodajLiniju("Instrumenti placanja ciji datum valute nije unesen ce" + 
		" biti tretirani kao da su da je datum valute danasnji dan u realnom vremenu");
		biljeznik.dodajLiniju();
		
		ArrayList<SredstvoPlacanja> hronoloskiRed = new ArrayList<SredstvoPlacanja>(sredstvaPlacanja);
		hronoloskiRed.sort(new Comparator<SredstvoPlacanja>() {
			@Override
			public int compare(SredstvoPlacanja o1, SredstvoPlacanja o2) {
				Date d1 = PomocnikZaUpite.datumZaSredstvo(o1);
				Date d2 = PomocnikZaUpite.datumZaSredstvo(o2);
				
				return d1.compareTo(d2);
			}
		});
		okruzenje = new RezimProticanja(new Date());
		for (int i = 0; i < sredstvaPlacanja.size(); i++) {
			SredstvoPlacanja sp = sredstvaPlacanja.get(i);
			okruzenje.pomjeriVrijeme(PomocnikZaUpite.datumZaSredstvo(sp));
			biljeznik.dodajLinijuBrojObrade(i+1, sredstvaPlacanja.size());
			if (sp instanceof NalogZaUplatu) {
				obradiNalogZaUplatu((NalogZaUplatu) sp);
			} else if (sp instanceof NalogZaIsplatu) {
				obradiNalogZaIsplatu((NalogZaIsplatu) sp);
			} else if (sp instanceof NalogZaPrenos) {
				obradiNalogZaPrenos((NalogZaPrenos) sp);
			} else {
				biljeznik.dodajTekstNepoznatoSredstvoPlacanja();
			}
			biljeznik.dodajLiniju();
		}
	}

	private void obradiSredstvaPlacanja(List<SredstvoPlacanja> sredstvaPlacanja) {
		biljeznik.dodajLiniju("Upozorenje:");
		biljeznik.dodajLiniju("U bazi vec postoje dnevna stanja");
		biljeznik.dodajLiniju("Bice obradjeni samo instrumenti placanja ciji je datum valute jedna danasnjem danu");
		biljeznik.dodajLiniju("Instrumenti placanja ciji datum valute nije unesen ce biti tretirani kao da su da je datum valute danasnji dan");
		biljeznik.dodajLiniju();
		
		okruzenje = new RezimRealnogVremena();
		
		for(int i = 0; i < sredstvaPlacanja.size(); i++) {
			SredstvoPlacanja sp = sredstvaPlacanja.get(i); 
			biljeznik.dodajLinijuBrojObrade(i+1, sredstvaPlacanja.size());
			if(sp instanceof NalogZaUplatu) {
				obradiNalogZaUplatu((NalogZaUplatu)sp);
			} else if(sp instanceof NalogZaIsplatu) {
				obradiNalogZaIsplatu((NalogZaIsplatu)sp);
			} else if(sp instanceof NalogZaPrenos) {
				obradiNalogZaPrenos((NalogZaPrenos)sp);
			} else {
				biljeznik.dodajTekstNepoznatoSredstvoPlacanja();
				continue;
			}
			biljeznik.dodajLiniju();
		}
	}
	
	private void obradiNalogZaUplatu(NalogZaUplatu upl) {
		biljeznik.stampajSredstvoPlacanja(upl, Biljeznik.TIP_NALOG_ZA_UPLATU);
		
		if(!validacija.provjeriDatumPrijema(upl)) {
			return;
		}
		if(!validacija.provjeriDatumIzvrsenja(upl)) {
			return;
		}
		VrstaPlacanja vrstaPlacanja = 
				PomocnikZaUpite.pronadjiVrstuPoSifri(upl.getSifraPlacanja()+"");
		if(!validacija.provjeriVrstuPlacanja(vrstaPlacanja, upl.getSifraPlacanja()+"")) {
			return;
		}
		Racun racunPovjerioca = PomocnikZaUpite.pronadjiRacunPoBroju(upl.getRacunPovjerioca());
		if(!validacija.provjeriRacunPovjerioca(upl.getRacunPovjerioca(), racunPovjerioca, upl.getValuta())) {
			return;
		}
		DnevnoStanjeRacuna posljednjeStanjePovjerioca = PomocnikZaUpite.posljednjeDnevnoStanjeZaRacun(racunPovjerioca, okruzenje);
		
		AnalitikaIzvoda analitika = new AnalitikaIzvoda();
		
		analitika.datumPrijema = PomocneOperacije.getStartOfDay(
			upl.getDatumPrijema().toGregorianCalendar().getTime());
		analitika.datumValute = PomocnikZaUpite.datumZaSredstvo(upl);
		analitika.svrhaPlacanja = upl.getSvrhaPlacanja();
		analitika.iznos = upl.getIznos();
		analitika.hitno = false;
		analitika.naseljenoMjesto = null;
		analitika.vrstaPlacanja = vrstaPlacanja;
		
		analitika.duzNalogodavac = upl.getNazivDuznika();
		analitika.racunDuznika = null;
		analitika.modelZaduzenja = null;
		analitika.pozNaBrojZaduzenja = null;
		analitika.dnevnoStanjeNaTeret = null;
		
		String nazivPovjerioca = validacija.nazivKlijentaRacuna(racunPovjerioca);
		if(!nazivPovjerioca.equals(upl.getNazivPovjerioca())) {
			biljeznik.dodajTekstNazivPovjerioca(upl.getNazivPovjerioca(), nazivPovjerioca);
		}
		analitika.povjerPrimalac = nazivPovjerioca;
		analitika.racunPovjerioca = racunPovjerioca.brojRacuna;
		analitika.modelOdobrenja = (upl.getModelOdobrenja()!=null)?(upl.getModelOdobrenja().intValue()):(null);
		analitika.pozNaBrojOdobrenja = upl.getPozivNaBrojOdobrenja();
		analitika.dnevnoStanjeUKorist = posljednjeStanjePovjerioca;
		
		analitika.valuta = racunPovjerioca.valuta;
		
		sacuvajUBazu(null, null, racunPovjerioca, posljednjeStanjePovjerioca, analitika);
		
		biljeznik.dodajLinijuUspjeh();
	}
	
	private void obradiNalogZaIsplatu(NalogZaIsplatu isp) {
		biljeznik.stampajSredstvoPlacanja(isp, Biljeznik.TIP_NALOG_ZA_ISPLATU);
		
		if(!validacija.provjeriDatumPrijema(isp)) {
			return;
		}
		if(!validacija.provjeriDatumIzvrsenja(isp)) {
			return;
		}
		VrstaPlacanja vrstaPlacanja = 
				PomocnikZaUpite.pronadjiVrstuPoSifri(isp.getSifraPlacanja()+"");
		if(!validacija.provjeriVrstuPlacanja(vrstaPlacanja, isp.getSifraPlacanja()+"")) {
			return;
		}
		Racun racunDuznika = PomocnikZaUpite.pronadjiRacunPoBroju(isp.getRacunDuznika());
		if(!validacija.provjeriRacunDuznika(isp.getRacunDuznika(), racunDuznika, isp.getValuta())) {
			return;
		}
		DnevnoStanjeRacuna posljednjeStanjeDuznika = PomocnikZaUpite.posljednjeDnevnoStanjeZaRacun(racunDuznika, okruzenje);
		if(!validacija.provjeriPosljednjeDnevnoStanjeDuznika(posljednjeStanjeDuznika, isp.getIznos())) {
			return;
		}
		
		AnalitikaIzvoda analitika = new AnalitikaIzvoda();
		
		analitika.datumPrijema = PomocneOperacije.getStartOfDay(
				isp.getDatumPrijema().toGregorianCalendar().getTime());
		analitika.datumValute = PomocnikZaUpite.datumZaSredstvo(isp);
		analitika.svrhaPlacanja = isp.getSvrhaPlacanja();
		analitika.iznos = isp.getIznos();
		analitika.hitno = false;
		analitika.naseljenoMjesto = null;
		analitika.vrstaPlacanja = vrstaPlacanja;
		
		String nazivDuznika = validacija.nazivKlijentaRacuna(racunDuznika);
		if(!nazivDuznika.equals(isp.getNazivDuznika())) {
			biljeznik.dodajTekstNazivDuznika(isp.getNazivDuznika(), nazivDuznika);
		}
		analitika.duzNalogodavac = nazivDuznika;
		analitika.racunDuznika = isp.getRacunDuznika();
		analitika.modelZaduzenja = (isp.getModelZaduzenja()!=null)?(isp.getModelZaduzenja().intValue()):(null);
		analitika.pozNaBrojZaduzenja = isp.getPozivNaBrojZaduzenja();
		analitika.dnevnoStanjeNaTeret = posljednjeStanjeDuznika;
		
		analitika.povjerPrimalac = isp.getNazivPovjerioca();
		analitika.racunPovjerioca = null;
		analitika.modelOdobrenja = null;
		analitika.pozNaBrojOdobrenja = null;
		analitika.dnevnoStanjeUKorist = null;
		
		analitika.valuta = racunDuznika.valuta;
		
		sacuvajUBazu(racunDuznika, posljednjeStanjeDuznika, null, null, analitika);
		
		biljeznik.dodajLinijuUspjeh();
	}

	private void obradiNalogZaPrenos(NalogZaPrenos pre) {
		biljeznik.stampajSredstvoPlacanja(pre, Biljeznik.TIP_NALOG_ZA_PRENOS);
		
		if(!validacija.provjeriDatumPrijema(pre)) {
			return;
		}
		if(!validacija.provjeriDatumIzvrsenja(pre)) {
			return;
		}
		VrstaPlacanja vrstaPlacanja = 
				PomocnikZaUpite.pronadjiVrstuPoSifri(pre.getSifraPlacanja()+"");
		if(!validacija.provjeriVrstuPlacanja(vrstaPlacanja, pre.getSifraPlacanja()+"")) {
			return;
		}
		Racun racunDuznika = PomocnikZaUpite.pronadjiRacunPoBroju(pre.getRacunDuznika());
		if(!validacija.provjeriRacunDuznika(pre.getRacunDuznika(), racunDuznika, pre.getValuta())) {
			return;
		}
		DnevnoStanjeRacuna posljednjeStanjeDuznika = PomocnikZaUpite.posljednjeDnevnoStanjeZaRacun(racunDuznika, okruzenje);
		if(!validacija.provjeriPosljednjeDnevnoStanjeDuznika(posljednjeStanjeDuznika, pre.getIznos())) {
			return;
		}
		
		Racun racunPovjerioca = PomocnikZaUpite.pronadjiRacunPoBroju(pre.getRacunPovjerioca());
		if(!validacija.provjeriRacunPovjerioca(pre.getRacunPovjerioca(), racunPovjerioca, pre.getValuta())) {
			return;
		}
		
		DnevnoStanjeRacuna posljednjeStanjePovjerioca = PomocnikZaUpite.posljednjeDnevnoStanjeZaRacun(racunPovjerioca, okruzenje);
		
		if(racunDuznika.banka.id == racunPovjerioca.banka.id) {
			AnalitikaIzvoda analitika = new AnalitikaIzvoda();
			
			analitika.datumPrijema = PomocneOperacije.getStartOfDay(
					pre.getDatumPrijema().toGregorianCalendar().getTime());
			analitika.datumValute = PomocnikZaUpite.datumZaSredstvo(pre);
			analitika.svrhaPlacanja = pre.getSvrhaPlacanja();
			analitika.iznos = pre.getIznos();
			analitika.hitno = false;
			analitika.naseljenoMjesto = null;
			analitika.vrstaPlacanja = vrstaPlacanja;
			
			String nazivDuznika = validacija.nazivKlijentaRacuna(racunDuznika);
			if(!nazivDuznika.equals(pre.getNazivDuznika())) {
				biljeznik.dodajTekstNazivDuznika(pre.getNazivDuznika(), nazivDuznika);
			}
			analitika.duzNalogodavac = nazivDuznika;
			analitika.racunDuznika = racunDuznika.brojRacuna;
			analitika.modelZaduzenja = (pre.getModelZaduzenja()!=null)?(pre.getModelZaduzenja().intValue()):(null);
			analitika.pozNaBrojZaduzenja = pre.getPozivNaBrojZaduzenja();
			analitika.dnevnoStanjeNaTeret = posljednjeStanjeDuznika;
			
			String nazivPovjerioca = validacija.nazivKlijentaRacuna(racunPovjerioca);
			if(!nazivPovjerioca.equals(pre.getNazivPovjerioca())) {
				biljeznik.dodajTekstNazivPovjerioca(pre.getNazivPovjerioca(), nazivPovjerioca);
			}
			analitika.povjerPrimalac = nazivPovjerioca;
			analitika.racunPovjerioca = racunPovjerioca.brojRacuna;
			analitika.modelOdobrenja = (pre.getModelOdobrenja()!=null)?(pre.getModelOdobrenja().intValue()):(null);
			analitika.pozNaBrojOdobrenja = pre.getPozivNaBrojOdobrenja();
			analitika.dnevnoStanjeUKorist = posljednjeStanjePovjerioca;
			
			analitika.valuta = racunDuznika.valuta;
			
			sacuvajUBazu(racunDuznika, posljednjeStanjeDuznika, racunPovjerioca, posljednjeStanjePovjerioca, analitika);
			
			biljeznik.dodajLinijuUspjeh();
			
		} else {
			biljeznik.dodajRazlogNeuspjeha("Medjubankarski transfer nije implementiran.");
			//TODO generisati poruku o medjubankarskom transferu
		}
		
	}

	private void sacuvajUBazu(
			Racun racunDuznika,
			DnevnoStanjeRacuna posljednjeStanjeDuznika,
			Racun racunPovjerioca,
			DnevnoStanjeRacuna posljednjeStanjePovjerioca,
			AnalitikaIzvoda analitika) {
		if(racunDuznika!=null) {
			analitika.dnevnoStanjeNaTeret = posljednjeStanjeDuznika;
			posljednjeStanjeDuznika.dodajNaTaret(analitika.iznos);
			racunDuznika.save();
			posljednjeStanjeDuznika.save();
		}
		if(racunPovjerioca!=null) {
			analitika.dnevnoStanjeUKorist = posljednjeStanjePovjerioca;
			posljednjeStanjePovjerioca.dodajUKorist(analitika.iznos);
			racunPovjerioca.save();
			posljednjeStanjePovjerioca.save();
		}
		if((racunDuznika!=null) || (racunPovjerioca!=null)) {
			analitika.save();
			generisanjeNalogaZaMedjubankarskiTransfer(analitika);
		}
	}
	
	public String izvjestaj() {
		return biljeznik.getIzlaz();
	}
	
	public static void generisanjeNalogaZaMedjubankarskiTransfer(AnalitikaIzvoda analitikaIzvoda) {

		ArrayList<MedjubankarskiPrenos> medjubankarskiPrenosi = new ArrayList<>();

		// RTGS
		if (analitikaIzvoda.iznos.compareTo(new BigDecimal(250000)) > 0 || analitikaIzvoda.hitno.equals(true)) {

			MedjubankarskiPrenos medjubankarskiPrenos = new MedjubankarskiPrenos(analitikaIzvoda.datumPrijema,
					analitikaIzvoda.racunDuznika, analitikaIzvoda.racunPovjerioca, analitikaIzvoda.iznos.longValue(),
					"MT103 (RTGS)");

			medjubankarskiPrenos.save();
			medjubankarskiPrenosi.add(medjubankarskiPrenos);

			StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenos);
			stavkaKliringa.save();
		}

		// kliring
		else {
			MedjubankarskiPrenos medjubankarskiPrenos = new MedjubankarskiPrenos(analitikaIzvoda.datumPrijema,
					analitikaIzvoda.racunDuznika, analitikaIzvoda.racunPovjerioca, analitikaIzvoda.iznos.longValue(),
					"MT102 (KLIRING)");

			medjubankarskiPrenos.save();
			medjubankarskiPrenosi.add(medjubankarskiPrenos);

			StavkaKliringa stavkaKliringa = new StavkaKliringa(null, analitikaIzvoda, medjubankarskiPrenos);
			stavkaKliringa.save();
		}

	}
	
	public static void exportNalogaZaMedjubanakarskiPrenosUXml(){
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			//root element
			Document document = documentBuilder.newDocument();
			Element root = document.createElement("medjubankarski_prenosi");
			document.appendChild(root);
			
			List<MedjubankarskiPrenos> medjubankarskiPrenosi  = MedjubankarskiPrenos.findAll();
			
			for (MedjubankarskiPrenos medjubankarskiPrenos : medjubankarskiPrenosi) {
				
				Element nalog = document.createElement("nalog");
				nalog.setAttribute("rbr", medjubankarskiPrenos.getId().toString());
				
				Element datumPrenosa = document.createElement("datum_prenosa");
				datumPrenosa.setTextContent(medjubankarskiPrenos.datumKliringa.toString());
				nalog.appendChild(datumPrenosa);

				Element racunBanDuz = document.createElement("racun_banke_duznika");
				racunBanDuz.setTextContent(medjubankarskiPrenos.racBankeDuz.toString());
				nalog.appendChild(racunBanDuz);

				Element ukupanIznos = document.createElement("ukupan_iznos");
				ukupanIznos.setTextContent(medjubankarskiPrenos.ukupanIznos.toString());
				nalog.appendChild(ukupanIznos);
				
				Element vrstaPrenosa = document.createElement("vrsta_prenosa");
				vrstaPrenosa.setTextContent(medjubankarskiPrenos.vrstaPrenosa.toString());
				nalog.appendChild(vrstaPrenosa);
				
				root.appendChild(nalog);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File("./nalozi/medjubankarskiTransferi_export.xml"));
			
			transformer.transform(domSource, streamResult);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}	
	}
}
