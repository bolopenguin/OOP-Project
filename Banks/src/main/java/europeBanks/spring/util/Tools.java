package europeBanks.spring.util;

import java.lang.reflect.Method;
/**
 * Classe astratta che contiene i metodi di supporto alle operazioni di filtraggio dei dati
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */
public class Tools {
	/**
	 * Metodo che restituisce una stringa su cui poi verra' fatto un confronto per effettuare il filtraggio dei dati
	 * @param field campo che si deve prendere 
	 * @param b oggetto budget a cui ci stiamo riferendo
	 * @return
	 * @throws Exception
	 */
	public static String getFilterString (String field, Object b)  throws Exception{
		//si costruisce il getter a partire dal campo richiesto
		Method m = b.getClass().getMethod("get"+field.substring(0, 1).toUpperCase()+field.substring(1),null);
		
		String tmp = null;
		// se si lavora con dei double
		if(Double.class.isInstance(m.invoke(b))) {
			// si traduce il double restituito dal getter e lo si traduce in stringa
			tmp = Double.toString((Double)m.invoke(b));
		}
		// se si lavora con dei int
		else if(Integer.class.isInstance(m.invoke(b))) {
			tmp = Integer.toString((Integer)m.invoke(b));
		}
		// se si lavora con delle stringhe
		else tmp =(String) m.invoke(b);
		
		return tmp;
	}
	
	/**
	 * Metodo che prepara la stringa value per fare il confronto poi nei filtri logici
	 * @param field campo che si deve prendere 
	 * @param value valore che si deve formattare in maniera corretta
	 * @param b oggetto budget a cui ci stiamo riferendo
	 * @return
	 * @throws Exception
	 */
	public static String setValueString (String field, String value, Object b)  throws Exception{
		//si costruisce il getter a partire dal campo richiesto
		Method m = b.getClass().getMethod("get"+field.substring(0, 1).toUpperCase()+field.substring(1),null);

		// se si lavora con dei double
		if(Double.class.isInstance(m.invoke(b))) {
			/*
			* si prende il valore lo si trasforma in double e poi di nuovo in stringa
			* questo processo Ã¨ necessario perchÃ© se viene inserito un value 0 per i double questo non viene
			* interpretato poi come double durante il confronto
			*/
			value = Double.toString(Double.parseDouble(value));
		}
		// se si lavora con dei int
		else if(Integer.class.isInstance(m.invoke(b))) {
			value = Integer.toString(Integer.parseInt(value));
		}
		
		return value;
	}
	
	
	/**
	 * Metodo che controlla se il parametro passato sia uno di quelli presenti nel csv
	 * @param property proprieta' di cui controllare l'esistenza
	 * @return
	 * @throws Exception eccezione lanciata nel caso in cui la proprieta' non esista
	 */
	public static boolean check(String property) throws Exception {
		if(property.equals("period") || property.equals("item") || property.equals("amount") || property.equals("n_quarters") || property.equals("lei_code") || property.equals("nsa") || property.equals("label")) {
			return true;
		}
		throw new Exception();
	}
	
	/**
	 * Metodo che controlla se l'operatore aritmetico passato come parametro sia uno di quelli supportati
	 * e inoltre controlla se i valori passati rispettino la relazione corrispondente
	 * @param tmp primo valore
	 * @param operator operatore aritmetico
	 * @param value secondo valore 
	 * @return
	 * @throws Exception eccezione lanciata nel caso in cui sia stata chiesto un operatore non supportato
	 */
	public static boolean checkConditional(double tmp, String operator, double value) throws Exception {
		switch(operator) {
		case "<":{
			if(tmp < value) 
				return true;
			} break;
		case "<=":{
			if(tmp <= value) 
				return true;
			} break;
		case "=":{
			if(tmp == value) 
				return true;
			} break;
		case ">=":{
			if(tmp >= value) 
				return true;
			} break;
		case ">":{			
			if(tmp > value) 
				return true;
			} break;
		default: throw new Exception();
		}
		return false;
	}
}
