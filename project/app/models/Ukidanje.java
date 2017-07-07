package models;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import play.db.jpa.Model;

@Entity
public class Ukidanje extends Model {
	
	@Column(nullable=false)
	public Date datumUkidanja;
	@Column(nullable=false)
	public String sredSePrenNaRacun;
	
	@ManyToOne(optional=false)
	public Racun racun;
	
	public Ukidanje() {
		// TODO Auto-generated constructor stub
	}

	public Ukidanje(Date datumUkidanja, String sredSePrenNaRacun) {
		super();
		this.datumUkidanja = datumUkidanja;
		this.sredSePrenNaRacun = sredSePrenNaRacun;
	}
}
