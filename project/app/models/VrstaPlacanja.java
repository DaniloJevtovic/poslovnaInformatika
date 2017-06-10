package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class VrstaPlacanja extends Model {

	public int oznakaVrste;
	public String nazivVrstePlacanja;
	
	@OneToMany(mappedBy = "vrstaPlacanja")
	public List<AnalitikaIzvoda> analitikeIzvoda;

	
	public VrstaPlacanja() {
		// TODO Auto-generated constructor stub
	}

	public VrstaPlacanja(int oznakaVrste, String nazivVrstePlacanja) {
		super();
		this.oznakaVrste = oznakaVrste;
		this.nazivVrstePlacanja = nazivVrstePlacanja;
	}

}
