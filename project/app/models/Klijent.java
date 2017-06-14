package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import models.constants.KonstanteKlijenta;
import play.db.jpa.Model;

@Entity
public class Klijent extends Model {
	@Column(nullable=false, length=KonstanteKlijenta.MAKSIMALNA_DUZINA_TIP)
	public String tipKlijenta;
	@Column(length=KonstanteKlijenta.MAKSIMALNA_DUZINA_NAZIV)
	public String nazivKlijenta;
	@Column(nullable=false, length=KonstanteKlijenta.MAKSIMALNA_DUZINA_IME)
	public String imeKlijenta;
	@Column(nullable=false, length=KonstanteKlijenta.MAKSIMALNA_DUZINA_PREZIME)
	public String przKlijenta;
	@Column(nullable=false, length=KonstanteKlijenta.MAKSIMALNA_DUZINA_TELEFON)
	public String telKlijenta;
	@Column(nullable=false, length=KonstanteKlijenta.MAKSIMALNA_DUZINA_ADRESA)
	public String adrKlijenta;
	@Column(length=KonstanteKlijenta.MAKSIMALNA_DUZINA_WEB)
	public String webKlijenta;
	@Column(length=KonstanteKlijenta.MAKSIMALNA_DUZINA_EMAIL)
	public String emailKlijenta;
	@Column(length=KonstanteKlijenta.MAKSIMALNA_DUZINA_TELEFON)
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
