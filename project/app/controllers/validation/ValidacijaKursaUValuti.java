package controllers.validation;

import java.math.BigDecimal;

import play.data.validation.Validation;

public class ValidacijaKursaUValuti {
	public static final double ZERO = 0.0;
	
	public static void validate(Validation validation, Long kursnaLista,
			Long osnovnaValuta, Long premaValuti, BigDecimal kupovni,
			BigDecimal srednji, BigDecimal prodajni) {
		validation.required(kursnaLista);
		validation.required(osnovnaValuta);
		validation.required(premaValuti);
		validation.required(srednji);
		
		validation.min(kupovni, ZERO);
		validation.min(srednji, ZERO);
		validation.min(prodajni, ZERO);
		
	}
	
	public static void validate(Validation validation, 
			BigDecimal kupovni, BigDecimal srednji, 
			BigDecimal prodajni) {
		validation.required(srednji);
		validation.min(kupovni, ZERO);
		validation.min(srednji, ZERO);
		validation.min(prodajni, ZERO);
	}
}
