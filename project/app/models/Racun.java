package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.constants.KonstanteRacuna;
import play.db.jpa.Model;

@Entity
public class Racun extends Model{

	@Column(unique=true, nullable=false, length=KonstanteRacuna.MAKSIMALNA_DUZINA_BROJ)
	public String brojRacuna;
	@Column(nullable=false)
	public Date datumOtvaranja;
	@Column(nullable=false)
	public boolean vazeci;
		
	@OneToMany(mappedBy = "racun")
	public List<Ukidanje> ukidanjaRacuna;
	
	@OneToMany(mappedBy = "racun")
	public List<DnevnoStanjeRacuna> dnevniIzvodiBanke;
	
	@ManyToOne(optional=false)
	public Valuta valuta;
	
	@ManyToOne(optional=false)
	public Banka banka;
	
	@ManyToOne(optional=false)
	public Klijent klijent;
	
	public Racun() {
		// TODO Auto-generated constructor stub
	}

	public Racun(String brojRacuna, Date datumOtvaranja, boolean vazeci) {
		super();
		this.brojRacuna = brojRacuna;
		this.datumOtvaranja = datumOtvaranja;
		this.vazeci = vazeci;
	}
}
