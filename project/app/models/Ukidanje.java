package models;

import java.sql.Date;

import play.db.jpa.Model;

public class Ukidanje extends Model {

	public int idUkidanja;
	public Date datumUkidanja;
	public String sredSePrenNaRacun;
	
	public Ukidanje() {
		// TODO Auto-generated constructor stub
	}

	public Ukidanje(int idUkidanja, Date datumUkidanja, String sredSePrenNaRacun) {
		super();
		this.idUkidanja = idUkidanja;
		this.datumUkidanja = datumUkidanja;
		this.sredSePrenNaRacun = sredSePrenNaRacun;
	}
}
