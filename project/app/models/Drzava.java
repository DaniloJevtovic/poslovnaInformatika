package models;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import models.constants.KonstanteDrzave;
import play.data.validation.Max;
import play.db.jpa.Model;

@Entity
public class Drzava extends Model {
	@Column(unique=true, nullable=false, length=KonstanteDrzave.MAKSIMALNA_DUZINA_OZNAKA)
	public String oznaka;
	@Column(unique=false, nullable=false, length=KonstanteDrzave.MAKSIMALNA_DUZINA_NAZIV)
	public String naziv;
	
	@OneToMany(mappedBy = "drzava")
	public List<NaseljenoMesto> naseljenaMesta;
	
	@OneToMany(mappedBy = "drzava")
	public List<Valuta> valute;
	
	public Drzava(String oznaka, String naziv){
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
	}
	
	public Drzava(Long id, String oznaka, String naziv){
		super();
		this.id = id;
		this.oznaka = oznaka;
		this.naziv = naziv;
	}
}
