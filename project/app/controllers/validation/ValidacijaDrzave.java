package controllers.validation;

import models.constants.KonstanteDrzave;
import models.constants.KonstanteKlijenta;
import models.constants.KonstanteValute;
import play.data.validation.Validation;

public class ValidacijaDrzave {

	public static final String OZNAKA_REGEX = "[A-Z]{3}";
	public static final String NAZIV_REGEX = "([a-zA-Z]|č|Č|ć|Ć|đ|Đ|š|Š|ž|Ž| )*";
	
	public static void validate(Validation validation, String oznaka, String naziv) {
		//validacija oznake
		validation.required(oznaka);
		validation.maxSize(oznaka, KonstanteDrzave.MAKSIMALNA_DUZINA_OZNAKA);
		validation.match(oznaka, OZNAKA_REGEX);
		//validacija naziva
		validation.required(naziv);
		validation.maxSize(naziv, KonstanteDrzave.MAKSIMALNA_DUZINA_NAZIV);
		validation.match(naziv, NAZIV_REGEX);
	}
	
	public ValidacijaDrzave() {
		// TODO Auto-generated constructor stub
	}

}
