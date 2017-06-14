package models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Racun extends Model{

	@Column(unique=true, nullable=false, length=18)
	public long brojRacuna;
	@Column(nullable=false)
	public Date datumOtvaranja;
	@Column(nullable=false)
	public boolean vazeci;
		
	@OneToMany(mappedBy = "racun")
	public List<Ukidanje> ukidanjaRacuna;
	
	@OneToMany(mappedBy = "racun")
	public List<DnevnoStanjeRacuna> dnevniIzvodiBanke;
	
	@ManyToOne
	public Valuta valuta;
	
	@ManyToOne
	public Banka banka;
	
	@ManyToOne
	public Klijent klijent;
	
	public Racun() {
		// TODO Auto-generated constructor stub
	}

	public Racun(Long brojRacuna, Date datumOtvaranja, boolean vazeci) {
		super();
		this.brojRacuna = brojRacuna;
		this.datumOtvaranja = datumOtvaranja;
		this.vazeci = vazeci;
	}
}
