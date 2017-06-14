package controllers.validation;

public class Validacije {
	
	
	public static final String PRAVNO_LICE = "P";
	public static final String FIZICKO_LICE = "F";
	public static final String TIPOVI_KLIJENTA[] = {PRAVNO_LICE, FIZICKO_LICE};
	public static final String TIP_KLIJENTA_REGEX = PRAVNO_LICE + "|" + FIZICKO_LICE;
	
	public static final int MAKSIMALNA_DUZINA_IME = 50;
	public static final int MAKSIMALNA_DUZINA_PREZIME = 50;
	
	
}
