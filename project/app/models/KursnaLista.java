package models;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


import models.constants.KonstanteKursneListe;
import play.db.jpa.Model;

@Entity
public class KursnaLista extends Model{
	@Column(nullable=false, unique=true, precision=KonstanteKursneListe.SIRINA_BROJA)
	public int brojKursneListe;
	@Column(nullable=false)
	public Date datumKursneListe;
	@Column(nullable=false)
	public Date primSeOd;	//primjenjuje se od
	@Column(nullable=false)
	public Date primSeDo;	//primjenjuje se do
	
	@ManyToOne(optional=false)
	public Banka banka;
	
	public KursnaLista() {
		// TODO Auto-generated constructor stub
	}
	
	public KursnaLista(int brojKursneListe, Date datumKursneListe, Date primSeOd, Date primSeDo) {
		super();
		this.brojKursneListe = brojKursneListe;
		this.datumKursneListe = datumKursneListe;	
		this.primSeOd = primSeOd;
		this.primSeDo = primSeDo;
	}
}
