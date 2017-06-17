package controllers.session;

import play.mvc.Scope.Flash;
import play.mvc.Scope.Session;

public class KonstanteSesije {
	public static final String MODE = "mode";
	public static final String HIGHLIGHTED_ID = "highlightedId";
	
	public static final String KONF_DODAVANJE = "add";
	public static final String KONF_IZMJENA = "edit";
	public static final String KONF_PRETRAGA = "search";
	public static final String[] DOZVOLJENE_KONFIGURACIJE = 
		{ KONF_DODAVANJE, KONF_IZMJENA, KONF_PRETRAGA };
	
	public static void fillFlash(Flash flash, String mode, String highlightedId) {
		flash.clear();
		flash.put(MODE, mode);
		if((highlightedId!=null) && (!highlightedId.equals(""))) {
			flash.put(HIGHLIGHTED_ID, highlightedId);
		} else {
			flash.remove(HIGHLIGHTED_ID);
		}
		
	}
	public static void resetSession(Flash flash) {
		flash.clear();
		flash.put(MODE, KONF_IZMJENA);
	}
	
	
	public static void deleteMode(Flash flash, String highlightedId) {
		flash.put(HIGHLIGHTED_ID, highlightedId);
	}
}
