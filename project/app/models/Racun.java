package models;

import java.sql.Date;

import play.db.jpa.Model;

public class Racun extends Model{

	public int idRacuna;
	public String brojRacuna;
	public Date datumOtvaranja;
	public boolean vazeci;
	
	public Racun() {
		// TODO Auto-generated constructor stub
	}

	public Racun(int idRacuna, String brojRacuna, Date datumOtvaranja, boolean vazeci) {
		super();
		this.idRacuna = idRacuna;
		this.brojRacuna = brojRacuna;
		this.datumOtvaranja = datumOtvaranja;
		this.vazeci = vazeci;
	}
}
