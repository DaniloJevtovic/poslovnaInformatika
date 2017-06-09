package models;

import java.sql.Date;

import play.db.jpa.Model;

public class Racuni extends Model{

	public int idRacuna;
	public String brojRacuna;
	public Date datumOtvaranja;
	public boolean vazeci;
	
	public Racuni() {
		// TODO Auto-generated constructor stub
	}

	public Racuni(int idRacuna, String brojRacuna, Date datumOtvaranja, boolean vazeci) {
		super();
		this.idRacuna = idRacuna;
		this.brojRacuna = brojRacuna;
		this.datumOtvaranja = datumOtvaranja;
		this.vazeci = vazeci;
	}
}
