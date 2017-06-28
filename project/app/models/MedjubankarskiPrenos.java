package models;

import java.util.*;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class MedjubankarskiPrenos extends Model {

	public Date datumKliringa;
	//public String swiftKodBankeDuz;
	public String racBankeDuz;
	//public String swiftKodBankePovj;
	public String racBankePovj;
	public Long ukupanIznos;
	public String vrstaPrenosa;
	
	@OneToMany(mappedBy = "medjubankarskiPrenos")
	public List<StavkaKliringa> stavkeKliringa;

	public MedjubankarskiPrenos() {
		// TODO Auto-generated constructor stub
	}

	public MedjubankarskiPrenos(Date datumKliringa, String racBankeDuz, String racBankePovj, 
			Long ukupanIznos, String vrstaPrenosa) {
		super();
		this.datumKliringa = datumKliringa;
		//this.swiftKodBankeDuz = swiftKodBankeDuz;
		this.racBankeDuz = racBankeDuz;
		//this.swiftKodBankePovj = swiftKodBankePovj;
		this.racBankePovj = racBankePovj;
		this.ukupanIznos = ukupanIznos;
		this.vrstaPrenosa = vrstaPrenosa;
	}
}
