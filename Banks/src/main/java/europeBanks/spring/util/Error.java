package europeBanks.spring.util;

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


}
