package models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class AnalitikaIzvoda extends Model {

	public String duzNalogodavac;		//duznik - nalogodavac
	public String svrhaPlacanja;
	public String povjerPrimalac;		//povjerilac - primalac
	public Date datumPrijema;
	public Date datumValute;
	public String racunDuznika;
	public Integer modelZaduzenja;
	public String pozNaBrojZaduzenja;
	public String racunPovjerioca;
	public Integer modelOdobrenja;
	public String pozNaBrojOdobrenja;
	public Boolean hitno;
	public BigDecimal iznos;
	public Integer tipGreske;
	public String status;
	
	@ManyToOne
	public VrstaPlacanja vrstaPlacanja;
	
	@ManyToOne
	public NaseljenoMesto naseljenoMjesto;
	
	@ManyToOne
	public Valuta valuta;
	
	@ManyToOne
	public DnevnoStanjeRacuna dnevnoStanjeUKorist;
	
	@ManyToOne
	public DnevnoStanjeRacuna dnevnoStanjeNaTeret;
	
	@OneToMany(mappedBy = "analitikaIzvoda")
	public List<StavkaKliringa> stavkeKliringa;
	
	public AnalitikaIzvoda() {
		// TODO Auto-generated constructor stub
	}

	public AnalitikaIzvoda(String duzNalogodavac, String svrhaPlacanja, String povjerPrimalac,
			Date datumPrijema, Date datumValute, String racunDuznika, Integer modelZaduzenja, String pozNaBrojZaduzenja,
			String racunPovjerioca, Integer modelOdobrenja, String pozNaBrojOdobrenja, Boolean hitno, BigDecimal iznos,
			Integer tipGreske, String status) {
		super();
		this.duzNalogodavac = duzNalogodavac;
		this.svrhaPlacanja = svrhaPlacanja;
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
