package models;

import java.sql.Date;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

public class AnalitikaIzvoda extends Model {

	public long brojStavke;
	public String duzNalogodavac;
	public String svrhaPlacanja;
	public String povjerPrimalac;
	public Date datumPrijema;
	public Date datumValute;
	public String racunDuznika;
	public int modelZaduzenja;
	public String pozNaBrojZaduzenja;
	public String racunPovjerioca;
	public int modelOdobrenja;
	public String pozNaBrojOdobrenja;
	public boolean hitno;
	public long iznos;
	public int tipGreske;
	public char status;
	
	@ManyToOne
	public VrstaPlacanja vrstaPlacanja;
	
	@ManyToOne
	public NaseljenoMesto naseljenoMesto;
	
	@ManyToOne
	public Valuta valuta;
	
	@ManyToOne
	public DnevnoStanjeRacuna dnevnoStanjeRacuna;
	
	@OneToMany(mappedBy = "analitikaIzvoda")
	public List<StavkaKliringa> stavkeKliringa;
	
	
	public AnalitikaIzvoda() {
		// TODO Auto-generated constructor stub
	}
	
	public AnalitikaIzvoda(long brojStavke, String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			Date datumPrijema, Date datumValute, String racunDuznika, int modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, int modelOdobrenja, String pozNaBrojOdobrenja, boolean hitno, long iznos,
			int tipGreske, char status) {
		super();
		this.brojStavke = brojStavke;
		this.duzNalogodavac = duzNalogodavac;
		this.povjerPrimalac = povjerPrimalac;
		this.datumPrijema = datumPrijema;
		this.datumValute = datumValute;
		this.racunDuznika = racunDuznika;
		this.modelZaduzenja = modelZaduzenja;
		this.pozNaBrojZaduzenja = pozNaBrojZaduzenja;
		this.racunPovjerioca = racunPovjerioca;
		this.modelOdobrenja = modelOdobrenja;
		this.pozNaBrojOdobrenja = pozNaBrojOdobrenja;
		this.hitno = hitno;
		this.iznos = iznos;
		this.tipGreske = tipGreske;
		this.status = status;
	}


}
