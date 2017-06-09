package models;

import play.db.jpa.Model;

public class Klijent extends Model {

	public String tipKlijenta;
	public String nazivKlijenta;
	public String imeKlijenta;
	public String prezimeKlijenta;
	public String telefonKlijenta;
	public String adresaKlijenta;
	public String webKlijenta;
	public String emailKlijenta;
	public String faksKlijenta;
	
	public Klijent() {
		// TODO Auto-generated constructor stub
	}
	
	public Klijent(String tipKlijenta, String nazivKlijenta, String imeKlijenta, String prezimeKlijenta,
			String telefonKlijenta, String adresaKlijenta, String webKlijenta, String emailKlijenta,
			String faksKlijenta) {
		super();
		this.tipKlijenta = tipKlijenta;
		this.nazivKlijenta = nazivKlijenta;
		this.imeKlijenta = imeKlijenta;
		this.prezimeKlijenta = prezimeKlijenta;
		this.telefonKlijenta = telefonKlijenta;
		this.adresaKlijenta = adresaKlijenta;
		this.webKlijenta = webKlijenta;
		this.emailKlijenta = emailKlijenta;
		this.faksKlijenta = faksKlijenta;
	}
	
	

}
