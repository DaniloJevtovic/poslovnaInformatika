package controllers.validation;

import controllers.helpers.Konstante;
import models.constants.KonstanteNaseljenogMjesta;
import play.data.validation.Validation;

public class ValidacijaNaseljenogMjesta {

	public ValidacijaNaseljenogMjesta() {
		// TODO Auto-generated constructor stub
	}
	
	public static final String OZNAKA_REGEX = "[A-Z]{3}";
	public static final String NAZIV_REGEX = "([a-zA-Z]|č|Č|ć|Ć|đ|Đ|š|Š|ž|Ž| )*";
	public static final String POSTANSKI_BROJ_REGEX = "([0-9]{5})";
	
	public static void validate(Validation validation, String oznaka, String naziv, String postanskiBroj) {
		//validacija oznake
		validation.required(oznaka);
		validation.maxSize(oznaka, KonstanteNaseljenogMjesta.MAKSIMALNA_DUZINA_OZNAKA);
		validation.match(oznaka, OZNAKA_REGEX);
		
		//validacija naziva
		validation.required(naziv);
		validation.maxSize(naziv, KonstanteNaseljenogMjesta.MAKSIMALNA_DUZINA_NAZIV);
		validation.match(naziv, NAZIV_REGEX);
		
		//validacija PTT
		validation.required(postanskiBroj);
		validation.minSize(postanskiBroj, KonstanteNaseljenogMjesta.MINIMALNA_DUZINA_PTT);
		validation.maxSize(postanskiBroj, KonstanteNaseljenogMjesta.MAKSIMALNA_DUZINA_NAZIV);
		validation.match(postanskiBroj, POSTANSKI_BROJ_REGEX);
	}

}
