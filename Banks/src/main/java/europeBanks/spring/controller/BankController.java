package europeBanks.spring.controller;

import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import europeBanks.spring.model.*;
import europeBanks.spring.util.*;
import europeBanks.spring.util.Error;
import europeBanks.spring.service.BudgetService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.jboss.logging.Param;
import org.json.JSONObject;

/**
 * Classe per gestire le richieste fatte all'applicazione alla porta 8080.
 * Vengono mappate le varie richieste e restituiti all'utente i dati richiesti.
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 *
 */
@RestController
public class BankController {

	// istanza della classe service, serve per chiamare le diverse funzioni statistiche
	BudgetService service = new BudgetService();
	
	/**
	 * Metodo che restituiscono i Metadata
	 * @return
	 */
	@RequestMapping(value = "/metadata", method = RequestMethod.GET)
	public ArrayList<Metadata> metadata(){
		return service.getMetadata();
	}
	
	/**
	 * Metodo che restituiscono i Data
	 * @return
	 */
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ArrayList<Budget> data(){	
		return service.getBudget();
	}
	
	// Implementazione delle Funzioni Statistiche
	
	
	/**
	 * Metodo che prende in ingresso una proprieta' e restituisce tutte le statistiche associate.
	 * @param property Nome della proprieta' su cui calcolare le statistiche
	 * @return 
	 * @throws Exception Eccezione che viene lanciata nel caso in cui la proprietà richiesta sia errata/ non esiste
	 */
	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public String statsBudget(@RequestParam("property") String property) 
			throws Exception
	{
		// oggetto che sarà restituito all'utente
		JSONObject stats = new JSONObject();
		try {
			// if per distinguere i casi in cui viene chiesta una statisticha su un campo numerico o di tipo Stringa
			if(!(property.toLowerCase().equals("lei_code") || property.toLowerCase().equals("nsa") || property.toLowerCase().equals("label") )) {
				// si va a prendere tutte le statistiche numeriche 
				// e le si inseriscono nell'oggetto JSON
				stats.put("average", service.avgBudget(property.toLowerCase()));
				stats.put("sum", service.sumBudget(property.toLowerCase()));
				stats.put("count", service.countBudget(property.toLowerCase()));
				stats.put("max",service.maxBudget(property.toLowerCase()));
				stats.put("min", service.minBudget(property.toLowerCase()));
				stats.put("devstd", service.devstdBudget(property.toLowerCase()));			
			} else {
				// si va a prendere la statistica relativa alle Stringhe
				
				// HashMap che contiene gli elementi unici
				Map<String, Integer> map = new HashMap<String, Integer>();
				map = service.getUniqueString(property.toLowerCase());
				// si aggiungono i campi della map nell'oggetto JSON
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
				    Map.Entry pairs = (Map.Entry)it.next();
				    stats.put(pairs.getKey().toString(), pairs.getValue() );
				}
			}
	
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		// ritorna il JSON all'utente
		return stats.toString();
	}
	
	
	/**
	 * Funzione che calcola la media dei valori della proprietà scelta
	 * @param property Proprieta' su cui calcolare i valori
	 * @return 
	 * @throws Exception Eccezione che viene lanciata nel caso in cui la proprietà richiesta sia errata/ non esiste
	 */
	@RequestMapping(value = "/stats/average", method = RequestMethod.GET)
	public String avgBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject average = new JSONObject();
		try {
			// si calcola la media e la si inserisce nel JSON
			average.put("average", service.avgBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		// ritorna il JSON all'utente
		return average.toString();
	}

	/**
	 * Funzione che calcola la somma dei valori della proprieta' scelta
	 * @param property Proprieta' su cui calcolare i valori
	 * @return somma
	 * @throws Exception Eccezione che viene lanciata nel caso in cui la proprietà richiesta sia errata/ non esiste
	 */
	
	@RequestMapping(value = "/stats/sum", method = RequestMethod.GET)
	public String sumBudget(@RequestParam("property")String property) throws Exception 
	{
		JSONObject addiction = new JSONObject();
		try {
			// si calcola la somma e la si inserisce nel JSON
			addiction.put("sum", service.sumBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		// ritorna il JSON all'utente
		return addiction.toString();

	}
	
	/**
	 * Funzione che calcola il numero di occorrenze della proprieta' scelta
	 * @param property Proprieta' di cui calcolare le occorrenze
	 * @return 
	 * @throws Exception Eccezione che viene lanciata nel caso in cui la proprietà richiesta sia errata/ non esiste
	 */
	
	@RequestMapping(value = "/stats/count", method = RequestMethod.GET)
	public String countBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject counter = new JSONObject();
		try {
			// si calcola il conteggio e lo si inserisce nel JSON
			counter.put("count", service.countBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		// ritorna il JSON all'utente
		return counter.toString();
	}
	
	/**
	 * Funzione che calcola il massimo dei valori della proprieta' scelta
	 * @param property Proprieta' in cui cercare il massimo
	 * @return 
	 * @throws Exception Eccezione che viene lanciata nel caso in cui la proprietà richiesta sia errata/ non esiste
	 */
	
	@RequestMapping(value = "/stats/max", method = RequestMethod.GET)
	public String maxBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject max = new JSONObject();
		try {
			// si cerca il massimo e lo si inserisce nel JSON
			max.put("max",service.maxBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		// ritorna il JSON all'utente
		return max.toString();
	}
	
	/**
	 * Funzione che calcola il minimo dei valori della proprieta' scelta
	 * @param property Proprieta' in cui cercare il minimo
	 * @return 
	 * @throws Exception Eccezione che viene lanciata nel caso in cui la proprietà richiesta sia errata/ non esiste
	 */
	
	@RequestMapping(value = "/stats/min", method = RequestMethod.GET)
	public String minBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject min = new JSONObject();
		try {
			// si cerca il minimo e lo si inserisce nel JSON
			min.put("min", service.minBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		// ritorna il JSON all'utente
		return min.toString();
	}
	
	
	/**
	 * Funzione che calcola la deviazione standard dei valori della proprieta' scelta
	 * @param property Proprieta' su cui calcolare la deviazione standard
	 * @return 
	 * @throws Exception Eccezione che viene lanciata nel caso in cui la proprietà richiesta sia errata/ non esiste
	 */
	
	@RequestMapping(value = "/stats/devstd", method = RequestMethod.GET)
	public String devstdBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject devstd = new JSONObject();
		try {
			// si calcola la dev std e la si inserisce nel JSON
			devstd.put("devstd", service.devstdBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		// ritorna il JSON all'utente
		return devstd.toString();
	}
	
	
	/**
	 * Funzione che calcola il numero di occorrenze degli elementi unici su una proprieta'
	 * Viene applicato solo alle Stringhe
	 * @param property Proprieta' su individuare gli elementi unici
	 * @return 
	 * @throws Exception Eccezione che viene lanciata nel caso in cui la proprietà richiesta sia errata/ non esiste
	 */
	@RequestMapping(value = "/stats/unique", method = RequestMethod.GET)
	public Map<String, Integer> getUniqueString(@RequestParam("property") String property) throws Exception
	{
		try {
			// mappa che conterrà come chiave il nome del valore e come valore il numero di occorrenze presenti
			return service.getUniqueString(property.toLowerCase());
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		return null;
	}

	// implementazione dei filtri
	
	/**
	 * Filtro Logico, si puo' applicare a tutte le proprieta'
	 * Ritorna un Array List contente solo gli oggetti che sono stati filtrati
	 * @param operator Operatore sui cui fare il filtro (AND, OR, NOT, IN, NIN)
	 * @param field1 Proprieta' uno
	 * @param value1 Valore della proprieta' 1
	 * @param field2 Proprieta' due
	 * @param value2 Valore della proprieta' 2
	 * @return
	 * @throws Exception eccezione nel caso in cui si sia chiesta una proprietà sbagliata / non esistente oppure nel caso in cui i valori di input siano errati
	 */
	@RequestMapping(value = "/logicalFilter", method = RequestMethod.GET)
	public ArrayList<Budget> logicalFilter(
			@RequestParam("operator") String operator, 
			@RequestParam("field1") String field1, 
			@RequestParam("value1") String value1, 
			@RequestParam(name = "field2", defaultValue = "") String field2, 
			@RequestParam(name = "value2", defaultValue = "") String value2
			) throws Exception
	{
		try {
			return service.logicalFilter(field1.toLowerCase(), value1, operator.toLowerCase(), field2.toLowerCase(), value2);
		} catch (Exception e) {
			Error.noParamsMsg();
		}
		
		return service.getBudget();
	}
	
	
	/**
	 * Filtro Condizionale, si applica solo alle proprieta' di tipo numerico
	 * Ritorna un Array List contente solo gli oggetti che sono stati filtrati
	 * @param operator Operatore su cui fare il Filtro ( <, <=, =, >, >=) 
	 * @param field Proprieta'
	 * @param value Valore
	 * @return
	 * @throws Exception eccezione nel caso in cui si sia chiesta una proprietà sbagliata / non esistente oppure nel caso in cui i valori di input siano errati
	 */
	@RequestMapping(value = "/conditionalFilter", method = RequestMethod.GET)
	public ArrayList<Budget> conditionalFilter(
			@RequestParam("operator") String operator, 
			@RequestParam("field") String field, 
			@RequestParam("value") String value
			) throws Exception
	{
		try {
			return service.conditionalFilter(field.toLowerCase(),value, operator);
		} catch (Exception e) {
			Error.noParamsMsg();
		}
		
		return service.getBudget();
	}
}
