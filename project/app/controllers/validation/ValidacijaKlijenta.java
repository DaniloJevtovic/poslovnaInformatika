package controllers.validation;

public class ValidacijaKlijenta {
	public static final String PRAVNO_LICE = "P";
	public static final String FIZICKO_LICE = "F";
	public static final String TIPOVI_KLIJENTA[] = {PRAVNO_LICE, FIZICKO_LICE};
	public static final String TIP_REGEX = PRAVNO_LICE + "|" + FIZICKO_LICE;
	
	public static final String NAZIV_REGEX = "([a-zA-Z]|č|Č|ć|Ć|đ|Đ|š|Š|ž|Ž| )+";
	
	public static final String IME_PREZIME_REGEX = "([A-Z]|Č|Ć|Đ|Š|Ž)([a-z]|č|ć|đ|š|ž)*";
}
