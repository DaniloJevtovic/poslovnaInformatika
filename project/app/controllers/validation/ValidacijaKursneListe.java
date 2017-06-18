package controllers.validation;

import java.sql.Date;

import play.data.validation.Validation;

public class ValidacijaKursneListe {
	public static final int BROJ_MINIMUM = 1;
	public static final int BROJ_MAKSIMUM = 999;
	public static void validate(Validation validation, Integer brojKursneListe,
			Date datumKursneListe, Date primSeOd, Date primSeDo) {
		validation.required(brojKursneListe);
		validation.minSize(brojKursneListe, BROJ_MINIMUM);
		validation.maxSize(brojKursneListe, BROJ_MAKSIMUM);
		validation.required(datumKursneListe);
		validation.required(primSeOd);
		validation.required(primSeDo);
		validation.future(primSeDo, datumKursneListe);
		validation.future(primSeDo, primSeOd);
	}
}
