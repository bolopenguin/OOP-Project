package europeBanks.spring.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** 
 * Classe per visualizzare un messaggio di errore nel caso in cui le richieste dell'utente non vadano a buon fine 
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

public class Error {
	/**
	 * metodo che invia un errore in caso di bad Request
	 * @param property
	 * @throws Exception
	 */
	public static void noPropertyMsg(String property) throws Exception {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find the input data: " + property);
	}
	
	public static void noParamsMsg() throws Exception {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input parameters are wrong");
	}


}
