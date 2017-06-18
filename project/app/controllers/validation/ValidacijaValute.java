package controllers.validation;

import models.constants.KonstanteValute;
import play.data.validation.Validation;

public class ValidacijaValute {
	public static final String SIFRA_REGEX = "[A-Z]{3}";
	public static final String NAZIV_REGEX = "([a-zA-Z]|č|Č|ć|Ć|đ|Đ|š|Š|ž|Ž| )*";
	
	public static void validate(Validation validation, String zvanicnaSifra, String nazivValute) {
		validation.required(zvanicnaSifra);
		validation.match(zvanicnaSifra, SIFRA_REGEX);
		validation.required(nazivValute);
		validation.match(nazivValute, NAZIV_REGEX);
		validation.maxSize(nazivValute, KonstanteValute.MAKSIMALNA_DUZINA_NAZIV);
	}
}
