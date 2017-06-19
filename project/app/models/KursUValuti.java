package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class KursUValuti extends Model {

	public String redniBroj;
	public BigDecimal kupovni;
	public BigDecimal srednji;
	public BigDecimal prodajni;
	
	@ManyToOne
	public Valuta osnovnaValuta;
	
	@ManyToOne
	public Valuta premaValuti;
	
	
	public KursUValuti() {
		// TODO Auto-generated constructor stub
	}
	
	public KursUValuti(String redniBroj,BigDecimal kupovni, BigDecimal srednji, BigDecimal prodajni) {
		super();
		this.redniBroj=redniBroj;
		this.kupovni = kupovni;
		this.srednji = srednji;
		this.prodajni = prodajni;
	}
}
