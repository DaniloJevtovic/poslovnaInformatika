package models;

import play.db.jpa.Model;

public class Valuta extends Model{

	public int idValute;
	public String zvanicnaSifra;
	public String nazivValute;
	
	public Valuta() {
		// TODO Auto-generated constructor stub
	}

	public Valuta(int idValute, String zvanicnaSifra, String nazivValute) {
		super();
		this.idValute = idValute;
		this.zvanicnaSifra = zvanicnaSifra;
		this.nazivValute = nazivValute;
	}
}
