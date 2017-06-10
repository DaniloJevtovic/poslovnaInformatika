package models;

import play.db.jpa.Model;

public class KursUValuti extends Model {

	public int redniBroj;
	public long kupovni;
	public long srednji;
	public long prodajni;
	
	public KursUValuti() {
		// TODO Auto-generated constructor stub
	}
	
	public KursUValuti(int redniBroj, long kupovni, long srednji, long prodajni) {
		super();
		this.redniBroj = redniBroj;
		this.kupovni = kupovni;
		this.srednji = srednji;
		this.prodajni = prodajni;
	}
}
