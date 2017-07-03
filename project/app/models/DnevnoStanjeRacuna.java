package models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class DnevnoStanjeRacuna extends Model {

	public Date datumPrometa;
	public BigDecimal predhodnoStanje;
	public BigDecimal prometUKorist;
	public BigDecimal prometNaTeret;
	public BigDecimal novoStanje;
	
	@ManyToOne
	public Racun racun;
	
	@OneToMany(mappedBy = "dnevnoStanjeUKorist")
	public List<AnalitikaIzvoda> analitikeUKorist;
	
	@OneToMany(mappedBy = "dnevnoStanjeNaTeret")
	public List<AnalitikaIzvoda> analitikeNaTeret;
	
	public DnevnoStanjeRacuna() {
		// TODO Auto-generated constructor stub
	}
	
	public DnevnoStanjeRacuna(Racun racun, Date datumPrometa, BigDecimal predhodnoStanje, BigDecimal prometUKorist,
			BigDecimal prometNaTeret, BigDecimal novoStanje) {
		// TODO Auto-generated constructor stub
		super();
		this.racun = racun;
		this.datumPrometa = datumPrometa;
		this.predhodnoStanje = predhodnoStanje;
		this.prometUKorist = prometUKorist;
		this.prometNaTeret = prometNaTeret;
		this.novoStanje = novoStanje;
	}
	
	private void sinhronizujNovoStanje() {
		novoStanje = predhodnoStanje.add(prometUKorist).subtract(prometNaTeret);
	}
	
	public void dodajUKorist(BigDecimal iznos) {
		prometUKorist = prometUKorist.add(iznos);
		sinhronizujNovoStanje();
	}
	
	public void dodajNaTaret(BigDecimal iznos) {
		prometNaTeret = prometNaTeret.add(iznos);
		sinhronizujNovoStanje();
	}

}
