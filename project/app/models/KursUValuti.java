package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class KursUValuti extends Model {


	public long kupovni;
	public long srednji;
	public long prodajni;
	
	@ManyToOne
	public Valuta osnovnaValuta;
	
	@ManyToOne
	public Valuta premaValuti;
	
	public KursUValuti() {
		// TODO Auto-generated constructor stub
	}
	
	public KursUValuti(long kupovni, long srednji, long prodajni) {
		super();
		this.kupovni = kupovni;
		this.srednji = srednji;
		this.prodajni = prodajni;
	}
}
