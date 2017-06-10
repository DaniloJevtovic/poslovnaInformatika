package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Klijent extends Model {

	public String tipKlijenta;
	public String nazivKlijenta;
	public String imeKlijenta;
	public String przKlijenta;
	public String telKlijenta;
	public String adrKlijenta;
	public String webKlijenta;
	public String emailKlijenta;
	public String faksKlijenta;
	
	@OneToMany(mappedBy = "klijent")
	public List<Racun> racuni;
	
	
	public Klijent() {
		// TODO Auto-generated constructor stub
	}
	
	public Klijent(String tipKlijenta, String nazivKlijenta, String imeKlijenta, String przKlijenta,
			String telKlijenta, String adrKlijenta, String webKlijenta, String emailKlijenta,
			String faksKlijenta) {
		super();
		this.tipKlijenta = tipKlijenta;
		this.nazivKlijenta = nazivKlijenta;
		this.imeKlijenta = imeKlijenta;
		this.przKlijenta = przKlijenta;
		this.telKlijenta = telKlijenta;
		this.adrKlijenta = adrKlijenta;
		this.webKlijenta = webKlijenta;
		this.emailKlijenta = emailKlijenta;
		this.faksKlijenta = faksKlijenta;
	}
	
	

}
