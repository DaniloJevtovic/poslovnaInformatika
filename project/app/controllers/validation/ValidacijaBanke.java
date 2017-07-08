package controllers.validation;

import models.constants.KonstaneBanke;
import models.constants.KonstanteDrzave;
import play.data.validation.Validation;

public class ValidacijaBanke {

	public static final String SIFRA_REGEX = "[A-Z]{3}";
	public static final int PIB_MINIMUM = 10000001;
	public static final int PIB_MAKSIMUM = 99999999;
	public static final String NAZIV_REGEX = "([a-zA-Z]|č|Č|ć|Ć|đ|Đ|š|Š|ž|Ž| )*";
	//public static final String ADRESA_REGEX = "([a-zA-Z]|č|Č|ć|Ć|đ|Đ|š|Š|ž|Ž| )*";
	
	
	public static void validate(Validation validation, String sifraBanke, String PIBBanke, 
			String nazivBanke, String adresaBanke,String telefonBanke, String emailBanke, 
			String webBanke, String faksBanke) {
		
		//validacija sifre
		validation.required(sifraBanke);
		validation.match(sifraBanke, ValidacijaBanke.SIFRA_REGEX);
		validation.maxSize(sifraBanke, KonstaneBanke.MAKSIMALNA_DUZINA_SIFRA);
		
		//validacija PIB
		validation.required(PIBBanke);
		validation.minSize(PIB_MINIMUM, ValidacijaBanke.PIB_MINIMUM);
		validation.maxSize(PIB_MAKSIMUM, ValidacijaBanke.PIB_MAKSIMUM);
		
		//validacija naziv
		validation.required(nazivBanke);
		validation.maxSize(nazivBanke, KonstaneBanke.MAKSIMALNA_DUZINA_NAZIV);
		validation.match(nazivBanke, ValidacijaBanke.NAZIV_REGEX);
		
		//validacija adresa
		validation.required(adresaBanke);
		validation.maxSize(adresaBanke, KonstaneBanke.MAKSIMALNA_DUZINA_ADRESA);
		
		//validacija telefon
		validation.required(telefonBanke);
		validation.maxSize(telefonBanke, KonstaneBanke.MAKSIMALNA_DUZINA_TELEFON);
		
		//validacija email
		validation.maxSize(emailBanke, KonstaneBanke.MAKSIMALNA_DUZINA_EMAIL);
		validation.email(emailBanke);
		
		//validacija web
		validation.maxSize(webBanke, KonstaneBanke.MAKSIMALNA_DUZINA_WEB);
		validation.url(webBanke);
		
		//validacija faks
		validation.maxSize(faksBanke, KonstaneBanke.MAKSIMALNA_DUZINA_FAKS);
		
	}
	
	public ValidacijaBanke() {
		// TODO Auto-generated constructor stub
	}

}
