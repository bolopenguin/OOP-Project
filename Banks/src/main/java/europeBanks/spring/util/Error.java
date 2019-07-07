package europeBanks.spring.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** 
 * Classe che contiene i metodi per inviare i messaggi di errori all'utente in caso si stata fatta una richiesta errata
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

public class Error {
	/**
	 * metodo che invia un errore nel caso in cui si sia inserita una proprieta' sbagliata/non esistente
	 * @param property
	 * @throws Exception
	 */
	public static void noPropertyMsg(String property) throws Exception {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find the input data: " + property);
	}
	/**
	 * metodo che invia un errore nel caso in cui si sia inserito un valore sbagliato/non coerente con il campo property a cui si riferisce
	 * @throws Exception
	 */
	public static void noParamsMsg() throws Exception {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input parameters are wrong");
	}


}
