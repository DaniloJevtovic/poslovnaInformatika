package models;

import play.db.jpa.Model;

public class StavkaKliringa extends Model{

	public String idStavke;
	
	public StavkaKliringa() {
		// TODO Auto-generated constructor stub
	}

	public StavkaKliringa(String idStavke) {
		super();
		this.idStavke = idStavke;
	}
}
