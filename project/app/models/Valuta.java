package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Valuta extends Model{

	public String zvanicnaSifra;
	public String nazivValute;
	
	@ManyToOne
	public Drzava drzava;
	
	@OneToMany(mappedBy = "osnovnaValuta")
	public List<KursUValuti> kurseviOsnovnaValuta;
	
	@OneToMany(mappedBy = "premaValuti")
	public List<KursUValuti> kurseviPremaValuti;
	
	@OneToMany(mappedBy = "valuta")
	public List<Racun> racuni;
	
	@OneToMany(mappedBy = "valuta")
	public List<AnalitikaIzvoda> analitikeIzvoda;
	
	public Valuta() {
		// TODO Auto-generated constructor stub
	}

	public Valuta(String zvanicnaSifra, String nazivValute) {
		super();
		this.zvanicnaSifra = zvanicnaSifra;
		this.nazivValute = nazivValute;
	}
}
