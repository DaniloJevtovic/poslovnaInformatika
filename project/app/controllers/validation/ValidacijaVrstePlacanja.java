package controllers.validation;

import models.constants.KonstanteKlijenta;
import play.data.validation.Validation;

public class ValidacijaVrstePlacanja {

	public static final int DUZINA_VRSTE_PLACANJA=120;
	public static final String REGEX="[0-9]{3}";
	
	public static void validate(Validation validation,String oznakaVrste, String nazivVrstePlacanja) {
		validation.required(oznakaVrste);
		validation.required(nazivVrstePlacanja);
		validation.match(oznakaVrste, REGEX);
		validation.maxSize(nazivVrstePlacanja, DUZINA_VRSTE_PLACANJA);
	}

	
}
