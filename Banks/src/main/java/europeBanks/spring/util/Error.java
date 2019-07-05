package europeBanks.spring.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** 
 * Classe per visualizzare un messaggio di errore nel caso in cui le richieste dell'utente non vadano a buon fine 
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

public class Error {

	private String messageError;
	
	/**
	 * Costruttore
	 * 
	 * @param messageError e' il messaggio che rappresenta il tipo di errore incontrato
	 */

	public Error(String messageError) {
		super();
		this.messageError = messageError;
	}
	
	/**
	 *  getter del messaggio di errore
	 * @return
	 */
	public String getMessage() {
		return messageError;
	}
	
	public static void errorMsg(String property) throws Exception {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find the input property: " + property);
	}


}
