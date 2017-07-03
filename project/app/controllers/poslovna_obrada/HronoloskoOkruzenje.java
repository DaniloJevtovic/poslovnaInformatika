package controllers.poslovna_obrada;

import java.util.Date;

public abstract class HronoloskoOkruzenje {
	public abstract Date danasnjiDan();
	public abstract void pomjeriVrijeme(Date dan);
}
