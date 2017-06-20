package controllers.validation;

import java.util.Date;

import play.data.validation.Validation;

public class ValidacijaKursneListe {
	public static final int BROJ_MINIMUM = 1;
	public static final int BROJ_MAKSIMUM = 999;
	public static void validate(Validation validation, Integer brojKursneListe,
			Date datumKursneListe, Date primSeOd, Date primSeDo) {
		validation.required(brojKursneListe);
		validation.min(brojKursneListe, BROJ_MINIMUM);
		validation.max(brojKursneListe, BROJ_MAKSIMUM);
		validation.required(datumKursneListe);
		validation.required(primSeOd);
		validation.required(primSeDo);
		validation.future(primSeDo, datumKursneListe);
		validation.future(primSeDo, primSeOd);
	}
}
