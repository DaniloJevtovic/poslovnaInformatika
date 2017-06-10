package models;

import java.sql.Date;

import play.db.jpa.Model;

public class KursnaLista extends Model{

	public int idKursneListe;
	public Date datumKursneListe;
	public int brojKursneListe;
	public Date primSeOd;	//primjenjuje se od
	public Date primSeDo;	//primjenjuje se do
	
	public KursnaLista() {
		// TODO Auto-generated constructor stub
	}
	
	public KursnaLista(int idKursneListe, Date datumKursneListe, int brojKursneListe, Date primSeOd, Date primSeDo) {
		super();
		this.idKursneListe = idKursneListe;
		this.datumKursneListe = datumKursneListe;
		this.brojKursneListe = brojKursneListe;
		this.primSeOd = primSeOd;
		this.primSeDo = primSeDo;
	}
}
