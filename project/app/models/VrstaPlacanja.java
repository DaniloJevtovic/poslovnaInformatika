package models;

import javax.persistence.OneToMany;

import play.db.jpa.Model;

public class VrstaPlacanja extends Model {

	public int oznakaVrste;
	public String nazivVrstePlacanja;
	
	@OneToMany
	public AnalitikaIzvoda analitikaIzvoda;
	
	public VrstaPlacanja() {
		// TODO Auto-generated constructor stub
	}

	public VrstaPlacanja(int oznakaVrste, String nazivVrstePlacanja) {
		super();
		this.oznakaVrste = oznakaVrste;
		this.nazivVrstePlacanja = nazivVrstePlacanja;
	}

}
