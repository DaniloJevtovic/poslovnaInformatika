package controllers.poslovna_obrada;

import java.util.Date;

import controllers.helpers.PomocneOperacije;

public class RezimRealnogVremena extends HronoloskoOkruzenje{

	@Override
	public Date danasnjiDan() {
		Date danas = new Date();
		return PomocneOperacije.getStartOfDay(danas);
	}

	@Override
	public void pomjeriVrijeme(Date dan) {
		
	}

}
