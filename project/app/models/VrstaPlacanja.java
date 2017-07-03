package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class VrstaPlacanja extends Model {
	
	@Column(unique=true, nullable=false, length=3)
	public String oznakaVrste;
	@Column(nullable=false, length=120)
	public String nazivVrstePlacanja;
	
	@OneToMany(mappedBy = "vrstaPlacanja")
	public List<AnalitikaIzvoda> analitikeIzvoda;

	
	public VrstaPlacanja() {
		// TODO Auto-generated constructor stub
	}

	public VrstaPlacanja(String oznakaVrste, String nazivVrstePlacanja) {
		super();
		this.oznakaVrste = oznakaVrste;
		this.nazivVrstePlacanja = nazivVrstePlacanja;
	}

}
