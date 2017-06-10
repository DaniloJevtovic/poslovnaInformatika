package models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Racun extends Model{

	public int idRacuna;
	public String brojRacuna;
	public Date datumOtvaranja;
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

	public Racun(int idRacuna, String brojRacuna, Date datumOtvaranja, boolean vazeci) {
		super();
		this.idRacuna = idRacuna;
		this.brojRacuna = brojRacuna;
		this.datumOtvaranja = datumOtvaranja;
		this.vazeci = vazeci;
	}
}
