package controllers.validation;

import play.data.validation.Validation;

public class ValidacijaRacuna {
	public static final String BROJ_REGEX = "[0-9]{3}-[0-9]{13}-[0-9]{2}";
	
	public static void validate(Validation validation, String brojRacuna, 
			Long racun_klijent_id, Long racun_banka_id, Long racun_valuta_id) {
		validation.required(brojRacuna);
		validation.match(brojRacuna, BROJ_REGEX);
		validation.required(racun_klijent_id);
		validation.required(racun_banka_id);
		validation.required(racun_valuta_id);
	}
	
	public static void validate(Validation validation, Long racun_klijent_id,
			Long racun_banka_id, Long racun_valuta_id) {
		validation.required(racun_klijent_id);
		validation.required(racun_banka_id);
		validation.required(racun_valuta_id);
	}
}
