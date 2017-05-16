package models;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Max;
import play.db.jpa.Model;

@Entity
public class NaseljenoMesto extends Model {

	public String oznaka;
	public String naziv;
	public String postanskiBroj;
	
	@ManyToOne
	public Drzava drzava;
	
	public NaseljenoMesto(String oznaka, String naziv, String postanskiBroj, Drzava drzava) {
		// TODO Auto-generated constructor stub
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.postanskiBroj = postanskiBroj;
		this.drzava = drzava;
	}
	
	public NaseljenoMesto(Long id, String oznaka, String naziv, String pb, Drzava drzava) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.postanskiBroj = pb;
		this.drzava = drzava;
	}

}
