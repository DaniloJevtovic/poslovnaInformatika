package controllers.poslovna_obrada;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.example.instrumenti_placanja.SredstvoPlacanja;

class Biljeznik {
	public static final String UVLAKA = "    ";
	public static final String PORUKA_USPJEH = "Obrada uspjesna.";
	public static final String PORUKA_NEUSPJEH = "Obrada neuspjesna. Razlog : ";
	public static final String PORUKA_PREKID = "Obrada prekinuta. Razlog: ";
	public static final String PORUKA_UPOZORENJE = "Upozorenje: ";
	public static final String PORUKA_GRESKA = "Neotklonjiva greska: ";
	public static final String PORUKA_POCETAK = "Poslovna obrada zapoceta.";
	public static final String PORUKA_KRAJ = "Poslovna obrada zavrsena.";
	public static final String PORUKA_CITANJE = "Citam podatke iz xml datoteke.";
	public static final String PORUKA_PROCITANO_KRAJ = "Citanje xml datoteke zavrseno.";
	public static final String PORUKA_PROCITANO = "Procitano je ukupno: %1$d instrumenata.";
	
	public static final String TIP_NALOG_ZA_UPLATU = "Nalog za uplatu";
	public static final String TIP_NALOG_ZA_ISPLATU = "Nalog za isplatu";
	public static final String TIP_NALOG_ZA_PRENOS = "Nalog za prenos";
	
	private String izlaz;
	private SimpleDateFormat formatDatuma;
	
	public Biljeznik() {
		izlaz = "";
		formatDatuma = new SimpleDateFormat("dd.MM.yyyy");
	}
	
	public void stampajSredstvoPlacanja(SredstvoPlacanja sp, String tip) {
		dodajLiniju("Tip sredstva placanja : " + tip);
		dodajLiniju("Naziv duznika         : " + sp.getNazivDuznika());
		dodajLiniju("Naziv povjerioca      : " + sp.getNazivPovjerioca());
		dodajLiniju("Iznos                 : " + sp.getIznos());
	}
	
	public void dodajTekst(String text) {
		izlaz += text;
	}
	
	public void dodajLiniju() {
		izlaz += System.lineSeparator();
	}
	
	public void dodajLiniju(String linija) {
		izlaz += linija + System.lineSeparator();
	}
	
	public String getIzlaz() {
		return izlaz;
	}
	
	public void dodajLinijuBrojObrade(int redniBroj, int ukupno) {
		dodajLiniju("Obrada instrumenta placanja " + redniBroj + ". od " + ukupno + ".");
	}
	
	public void dodajPocetnuLiniju() {
		dodajLiniju(PORUKA_POCETAK);
	}
	
	public void dodajLinijuCitanje() {
		dodajLiniju(PORUKA_CITANJE);
	}
	
	public void dodajLinijuKraj() {
		dodajLiniju(PORUKA_KRAJ);
	}
	
	public void dodajRazlogPrekida(String razlog) {
		dodajLiniju(PORUKA_PREKID + razlog);
	}
	
	public void dodajRazlogNeuspjeha(String razlog) {
		dodajLiniju(PORUKA_NEUSPJEH + razlog);
	}
	
	public void dodajRazlogNeotklonjiveGreske(String razlog) {
		dodajLiniju(PORUKA_GRESKA + razlog);
	}

	public void dodajLinijuProcitano(int size) {
		dodajLiniju(PORUKA_PROCITANO_KRAJ);
		dodajLiniju(String.format(PORUKA_PROCITANO, size));
	}
	
	public void dodajLinijuDanasnjiDatum(Date danas) {
		dodajLiniju("Danasnji datum: '" + formatDatuma.format(danas) + "'.");
	}
	
	public void dodajLinijuBrojRacuna(String brojRacuna) {
		dodajLiniju("Broj racuna: - '" + brojRacuna + "'.");
	}
	
	public void dodajTekstNepoznatoSredstvoPlacanja() {
		dodajRazlogNeuspjeha("Nepoznata vrsta instrumenta placanja");
		dodajLiniju("Poznate vrste: Nalog za uplatu, Nalog za isplatu, Nalog za prenos");
	}
	
	public void dodajTekstNeispravanDatumPrijema(Date datumPrijema, Date danas) {
		dodajRazlogNeuspjeha("Neispravan datum prijema - '" + formatDatuma.format(datumPrijema) + "'.");
		dodajLiniju("Datum prijema ne moze da bude u buducnosti");
		dodajLinijuDanasnjiDatum(danas);
	}
	
	public void dodajTekstDatumIzvrsenjaUProslosti(Date datumIzvrsenja, Date danas) {
		dodajRazlogNeuspjeha("Neispravan datum izvrsenja - '" + formatDatuma.format(datumIzvrsenja) + "'.");
		dodajLiniju("Datum izvrsenja ne moze da bude u proslosti");
		dodajLinijuDanasnjiDatum(danas);
	}
	
	public void dodajTekstDatumIzvrsenjaUBuducnosti(Date datumIzvrsenja, Date danas) {
		dodajRazlogPrekida("Datum izvrsenja nije nastupio.");
		dodajLiniju("Instrument treba da se izvrsi na dan : '" + formatDatuma.format(datumIzvrsenja) + "'.");
		dodajLinijuDanasnjiDatum(danas);
	}
	
	public void dodajTekstNeispravnaSifraPlacanja(String sifraPlacanja) {
		dodajRazlogNeuspjeha("Neispravna sifra placanja - '" + sifraPlacanja + "'.");
	}
	
	public void dodajTekstNeispravanBrojRacunaDuznika(String brojRacuna) {
		dodajRazlogNeuspjeha("Neispravan broj racuna duznika - '" + brojRacuna + "'.");
	}
	
	public void dodajTekstNevazeciRacunDuznika(String brojRacuna) {
		dodajRazlogNeuspjeha("Racun duznika - '" + brojRacuna + "' je nevazeci.");
	}
	
	public void dodajTekstNeispravnaValutaRacunaDuznika(String brojRacuna, String valutaRacuna, String valutaInstrumenta) {
		dodajRazlogNeuspjeha("Valuta placanja racuna duznika - " + valutaInstrumenta + "'.");
		dodajLiniju("Valuta racuna - '" + valutaRacuna + "'.");
		dodajLinijuBrojRacuna(brojRacuna);
	}
	
	public void dodajTekstNeispravanBrojRacunaPovjerioca(String brojRacuna) {
		dodajRazlogNeuspjeha("Neispravan broj racuna povjerioca - '" + brojRacuna + "'.");
	}
	
	public void dodajTekstNevazeciRacunPovjerioca(String brojRacuna) {
		dodajRazlogNeuspjeha("Racun povjerioca - '" + brojRacuna + "' je nevazeci.");
	}
	
	public void dodajTekstNeispravnaValutaRacunaPovjerioca(String brojRacuna, String valutaRacuna, String valutaInstrumenta) {
		dodajRazlogNeuspjeha("Valuta placanja racuna povjerioca - " + valutaInstrumenta + "'.");
		dodajLiniju("Valuta racuna - '" + valutaRacuna + "'.");
		dodajLinijuBrojRacuna(brojRacuna);
	}
	
	public void dodajTekstNedovoljanIznos(String brojRacuna, BigDecimal trazeni, BigDecimal postojeci) {
		dodajRazlogNeuspjeha("Ne postoji dovoljna kolicina sredstava na racunu povjerioca.");
		dodajLiniju("Postojece stanje: " + postojeci);
		dodajLiniju("Trazeni iznos   : " + trazeni);
		dodajLinijuBrojRacuna(brojRacuna);
	}
	
	public void dodajLinijuUpozorenja(String linija) {
		dodajLiniju(PORUKA_UPOZORENJE + linija);
	}
	
	public void dodajTekstNazivDuznika(String navedeniNaziv, String nazivIzEvidencije) {
		dodajLinijuUpozorenja("Naziv navedeni naziv duznika ne odgovara nazivu duznika u evidenciji");
		dodajLiniju("Bice evidentiran naziv iz evidenicje");
		dodajLiniju("Navedeni naziv      : " + navedeniNaziv);
		dodajLiniju("Naziv iz evidencije : " + nazivIzEvidencije);
	}
	
	public void dodajTekstNazivPovjerioca(String navedeniNaziv, String nazivIzEvidencije) {
		dodajLinijuUpozorenja("Naziv navedeni naziv povjerioca ne odgovara nazivu povjerioca u evidenciji");
		dodajLiniju("Bice evidentiran naziv iz evidenicje");
		dodajLiniju("Navedeni naziv      : " + navedeniNaziv);
		dodajLiniju("Naziv iz evidencije : " + nazivIzEvidencije);
	}
	
	public void dodajLinijuUspjeh() {
		dodajLiniju(PORUKA_USPJEH);
	}
}