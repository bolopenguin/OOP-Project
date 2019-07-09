package europeBanks.spring.util;

import java.lang.reflect.Method;
/**
 * Classe astratta che contiene i metodi di supporto alle operazioni di filtraggio dei dati
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */
public class FilterTools {
	/**
	 * Metodo che restituisce una stringa su cui poi verra' fatto un confronto per effettuare il filtraggio dei dati
	 * @param field campo che si deve prendere 
	 * @param value valore che si deve formattare in maniera corretta
	 * @param b oggetto budget a cui ci stiamo riferendo
	 * @return
	 * @throws Exception
	 */
	public static String getFilterString (String field, String value, Object b)  throws Exception{
		//si costruisce il getter a partire dal campo richiesto
		Method m = b.getClass().getMethod("get"+field.substring(0, 1).toUpperCase()+field.substring(1),null);
		
		String tmp = null;
		// se si lavora con dei double
		if(Double.class.isInstance(m.invoke(b))) {
			// si traduce il double restituito dal getter e lo si traduce in stringa
			tmp = Double.toString((Double)m.invoke(b));
			/*
			* si prende il valore lo si trasforma in double e poi di nuovo in stringa
			* questo processo è necessario perché se viene inserito un value 0 per i double questo non viene
			* interpretato poi come double durante il confronto
			*/
			value = Double.toString(Double.parseDouble(value));
		}
		// se si lavora con dei int
		else if(Integer.class.isInstance(m.invoke(b)))
			tmp = Integer.toString((Integer)m.invoke(b));
		// se si lavora con delle stringhe
		else tmp =(String) m.invoke(b);
		
		return tmp;
	}

}
