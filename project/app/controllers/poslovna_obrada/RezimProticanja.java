package controllers.poslovna_obrada;

import java.util.Date;

import controllers.helpers.PomocneOperacije;

public class RezimProticanja extends HronoloskoOkruzenje{
	private Date danasnjiDan;
	
	public RezimProticanja(Date danasnjiDan) {
		this.danasnjiDan = PomocneOperacije.getStartOfDay(danasnjiDan);
	}
	
	@Override
	public Date danasnjiDan() {
		return danasnjiDan;
	}

	@Override
	public void pomjeriVrijeme(Date dan) {
		this.danasnjiDan = PomocneOperacije.getStartOfDay(dan);
	}

}
