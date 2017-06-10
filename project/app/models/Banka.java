package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Banka extends Model {

	public int idBanke;
	public String sifraBanke;
	public String PIBBanke;
	public String nazivBanke;
	public String adresaBanke;
	public String telefonBanke;
	public String emailBanke;
	public String webBanke;
	public String faksBanke;
	
	@OneToMany(mappedBy = "banka")
	public List<KursnaLista> kursnaLista;
	
	@OneToMany(mappedBy = "banka")
	public List<Racun> racun;
	
	public Banka() {
		// TODO Auto-generated constructor stub
	}
	
	public Banka(int idBanke, String sifraBanke, String pIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke) {
		super();
		this.idBanke = idBanke;
		this.sifraBanke = sifraBanke;
		PIBBanke = pIBBanke;
		this.nazivBanke = nazivBanke;
		this.adresaBanke = adresaBanke;
		this.telefonBanke = telefonBanke;
		this.emailBanke = emailBanke;
		this.webBanke = webBanke;
		this.faksBanke = faksBanke;
	}

}
