package models;

import java.util.*;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class DnevnoStanjeRacuna extends Model {

	public Long brojIzvoda;
	public Date datumPrometa;
	public Long predhodnoStanje;
	public Long prometUKorist;
	public Long prometNaTeret;
	public Long novoStanje;
	
	@ManyToOne
	public Racun racun;
	
	@OneToMany(mappedBy = "dnevnoStanjeRacuna")
	public List<AnalitikaIzvoda> analitikeIzvoda;
	
	public DnevnoStanjeRacuna() {
		// TODO Auto-generated constructor stub
	}
	
	public DnevnoStanjeRacuna(Long brojIzvoda, Racun racun, Date datumPrometa, Long predhodnoStanje, Long prometUKorist,
			Long prometNaTeret, Long novoStanje) {
		// TODO Auto-generated constructor stub
		super();
		this.brojIzvoda = brojIzvoda;
		this.racun = racun;
		this.datumPrometa = datumPrometa;
		this.predhodnoStanje = predhodnoStanje;
		this.prometUKorist = prometUKorist;
		this.prometNaTeret = prometNaTeret;
		this.novoStanje = novoStanje;
	}

}
