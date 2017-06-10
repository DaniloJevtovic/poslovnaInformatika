package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class StavkaKliringa extends Model{

	public String idStavke;
	
	@ManyToOne
	public MedjubankarskiPrenos medjubankarskiPrenos;
	
	@ManyToOne
	public AnalitikaIzvoda analitikaIzvoda;
	
	public StavkaKliringa() {
		// TODO Auto-generated constructor stub
	}

	public StavkaKliringa(String idStavke) {
		super();
		this.idStavke = idStavke;
	}
}
