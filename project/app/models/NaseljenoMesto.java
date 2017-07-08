package models;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.constants.KonstanteNaseljenogMjesta;
import play.data.validation.Max;
import play.db.jpa.Model;

@Entity
public class NaseljenoMesto extends Model {

	public String oznaka;
	@Column(unique=false, nullable=false, length=KonstanteNaseljenogMjesta.MAKSIMALNA_DUZINA_NAZIV)
	public String naziv;
	@Column(unique=false, nullable=false, length=KonstanteNaseljenogMjesta.MAKSIMALNA_DUZINA_PTT)
	public String postanskiBroj;
	
	@ManyToOne
	public Drzava drzava;
	
	@OneToMany(mappedBy = "naseljenoMjesto")
	public List<AnalitikaIzvoda> analitikeIzvoda;
	
	public NaseljenoMesto(String oznaka, String naziv, String postanskiBroj, Drzava drzava) {
		// TODO Auto-generated constructor stub
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.postanskiBroj = postanskiBroj;
		this.drzava = drzava;
	}
	
	public NaseljenoMesto(String oznaka, String naziv, String pb) {
		// TODO Auto-generated constructor stub
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.postanskiBroj = pb;
	}

}
