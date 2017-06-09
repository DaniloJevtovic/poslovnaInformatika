package models;

import java.sql.Date;

import play.db.jpa.Model;
import sun.util.logging.resources.logging;

public class DnevnoStanjeRacuna extends Model {

	public Long brojIzvoda;
	public Date datumPrometa;
	public long predhodnoStanje;
	public long prometUKorist;
	public long prometNaTeret;
	public long novoStanje;
	
	public DnevnoStanjeRacuna(Long brojIzvoda, Date datumPrometa, long predhodnoStanje, long prometUKorist,
			long prometNaTeret, long novoStanje) {
		// TODO Auto-generated constructor stub
		super();
		this.brojIzvoda = brojIzvoda;
		this.datumPrometa = datumPrometa;
		this.predhodnoStanje = predhodnoStanje;
		this.prometUKorist = prometUKorist;
		this.prometNaTeret = prometNaTeret;
		this.novoStanje = novoStanje;
	}

}
