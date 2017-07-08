package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import models.constants.KonstaneBanke;
import play.db.jpa.Model;

@Entity
public class Banka extends Model {

	@Column(unique=false, nullable=false, length=KonstaneBanke.MAKSIMALNA_DUZINA_SIFRA)
	public String sifraBanke;
	@Column(unique=false, nullable=false, length=KonstaneBanke.SIRINA_PIB)
	public String PIBBanke;
	@Column(unique=false, nullable=false, length=KonstaneBanke.MAKSIMALNA_DUZINA_NAZIV)
	public String nazivBanke;
	@Column(unique=false, nullable=false, length=KonstaneBanke.MAKSIMALNA_DUZINA_ADRESA)
	public String adresaBanke;
	@Column(unique=true, nullable=false, length=KonstaneBanke.MAKSIMALNA_DUZINA_TELEFON)
	public String telefonBanke;
	@Column(unique=false, nullable=false, length=KonstaneBanke.MAKSIMALNA_DUZINA_EMAIL)
	public String emailBanke;
	@Column(unique=true, nullable=false, length=KonstaneBanke.MAKSIMALNA_DUZINA_WEB)
	public String webBanke;
	@Column(unique=true, nullable=false, length=KonstaneBanke.MAKSIMALNA_DUZINA_FAKS)
	public String faksBanke;
	
	@OneToMany(mappedBy = "banka")
	public List<KursnaLista> kursnaLista;
	
	@OneToMany(mappedBy = "banka")
	public List<Racun> racun;
	
	public Banka() {
		// TODO Auto-generated constructor stub
	}
	
	public Banka(String sifraBanke, String PIBBanke, String nazivBanke, String adresaBanke,
			String telefonBanke, String emailBanke, String webBanke, String faksBanke) {
		super();
		this.sifraBanke = sifraBanke;
		this.PIBBanke = PIBBanke;
		this.nazivBanke = nazivBanke;
		this.adresaBanke = adresaBanke;
		this.telefonBanke = telefonBanke;
		this.emailBanke = emailBanke;
		this.webBanke = webBanke;
		this.faksBanke = faksBanke;
	}
}
