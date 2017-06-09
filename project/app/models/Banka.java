package models;

import play.db.jpa.Model;

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
