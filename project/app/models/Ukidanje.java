package models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Ukidanje extends Model {

	public Date datumUkidanja;
	public String sredSePrenNaRacun;
	
	@ManyToOne
	public Racun racun;
	
	public Ukidanje() {
		// TODO Auto-generated constructor stub
	}

	public Ukidanje(Date datumUkidanja, String sredSePrenNaRacun) {
		super();
		this.datumUkidanja = datumUkidanja;
		this.sredSePrenNaRacun = sredSePrenNaRacun;
	}
}
