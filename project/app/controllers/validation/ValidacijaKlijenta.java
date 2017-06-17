package controllers.validation;

import models.constants.KonstanteKlijenta;
import play.data.validation.Validation;

public class ValidacijaKlijenta {
	public static final String PRAVNO_LICE = "P";
	public static final String FIZICKO_LICE = "F";
	public static final String TIPOVI_KLIJENTA[] = { PRAVNO_LICE, FIZICKO_LICE };
	public static final String TIP_REGEX = PRAVNO_LICE + "|" + FIZICKO_LICE;

	public static final String NAZIV_REGEX = "([a-zA-Z]|č|Č|ć|Ć|đ|Đ|š|Š|ž|Ž| )+";

	public static final int PIB_MINIMUM = 10000001;
	public static final int PIB_MAKSIMUM = 99999999;

	public static final String IME_PREZIME_REGEX = "([A-Z]|Č|Ć|Đ|Š|Ž)([a-z]|č|ć|đ|š|ž)*";

	public static void validate(Validation validation, String tipKlijenta, String nazivKlijenta, Integer pibKlijenta,
			String imeKlijenta, String przKlijenta, String telKlijenta, String adrKlijenta, String webKlijenta,
			String emailKlijenta, String faksKlijenta) {
		// validacije tipa
		validation.required(tipKlijenta);
		validation.match(tipKlijenta, ValidacijaKlijenta.TIP_REGEX);
		// validacije naziva
		if (tipKlijenta.equals(ValidacijaKlijenta.PRAVNO_LICE)) {
			// naziv i pib su obavezni za pravno lice
			validation.required(nazivKlijenta);
			validation.required(pibKlijenta);

		}
		validation.maxSize(nazivKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_NAZIV);
		validation.match(nazivKlijenta, NAZIV_REGEX);
		// ostatak validacije pib; i fizicko lice moze, a ne mora da ga ima
		// ali u svakom slucaju treba da bude validan
		validation.min(pibKlijenta, PIB_MINIMUM);
		validation.max(pibKlijenta, PIB_MAKSIMUM);
		// validacije imena
		validation.required(imeKlijenta);
		validation.maxSize(imeKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_IME);
		validation.match(imeKlijenta, IME_PREZIME_REGEX);
		// validacije prezimena
		validation.required(przKlijenta);
		validation.maxSize(przKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_PREZIME);
		validation.match(przKlijenta, IME_PREZIME_REGEX);
		// validacije broja telefona
		validation.required(telKlijenta);
		validation.maxSize(telKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_TELEFON);
		validation.phone(telKlijenta);
		// validacije adrese
		validation.required(adrKlijenta);
		validation.maxSize(adrKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_ADRESA);
		// validacije web adrese
		validation.maxSize(webKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_WEB);
		validation.url(webKlijenta);
		// validacije emaila
		validation.maxSize(emailKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_EMAIL);
		validation.email(emailKlijenta);
		// validacije faksa
		validation.maxSize(faksKlijenta, KonstanteKlijenta.MAKSIMALNA_DUZINA_TELEFON);
		validation.phone(faksKlijenta);
	}
}
