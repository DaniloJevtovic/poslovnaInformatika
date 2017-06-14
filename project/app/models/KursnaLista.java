package models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class KursnaLista extends Model{

	public Date datumKursneListe;
	public int brojKursneListe;
	public Date primSeOd;	//primjenjuje se od
	public Date primSeDo;	//primjenjuje se do
	
	@ManyToOne
	public Banka banka;
	
	public KursnaLista() {
		// TODO Auto-generated constructor stub
	}
	
	public KursnaLista(Date datumKursneListe, int brojKursneListe, Date primSeOd, Date primSeDo) {
		super();
		this.datumKursneListe = datumKursneListe;
		this.brojKursneListe = brojKursneListe;
		this.primSeOd = primSeOd;
		this.primSeDo = primSeDo;
	}
}
