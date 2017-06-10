package models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class MedjubankarskiPrenos extends Model {

	public String idKliringa;
	public Date datumKliringa;
	public String swiftKodBankeDuz;
	public String racBankeDuz;
	public String swiftKodBankePovj;
	public String racBankePovj;
	public long ukupanIznos;
	public String vrstaPrenosa;
	
	@OneToMany(mappedBy = "medjubankarskiPrenos")
	public List<StavkaKliringa> stavkeKliringa;

	public MedjubankarskiPrenos() {
		// TODO Auto-generated constructor stub
	}

	public MedjubankarskiPrenos(String idKliringa, Date datumKliringa, String swiftKodBankeDuz, String racBankeDuz,
			String swiftKodBankePovj, String racBankePovj, long ukupanIznos, String vrstaPrenosa) {
		super();
		this.idKliringa = idKliringa;
		this.datumKliringa = datumKliringa;
		this.swiftKodBankeDuz = swiftKodBankeDuz;
		this.racBankeDuz = racBankeDuz;
		this.swiftKodBankePovj = swiftKodBankePovj;
		this.racBankePovj = racBankePovj;
		this.ukupanIznos = ukupanIznos;
		this.vrstaPrenosa = vrstaPrenosa;
	}
}
