package models;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import play.data.validation.Max;
import play.db.jpa.Model;

@Entity
public class Drzava extends Model {
	
	public String oznaka;
	public String naziv;
	
	@OneToMany(mappedBy = "drzava")
	public List<NaseljenoMesto> naseljenaMesta;
	
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
