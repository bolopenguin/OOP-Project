package europeBanks.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import europeBanks.spring.model.*;
import europeBanks.spring.service.BudgetService;

import java.util.ArrayList;

import org.json.JSONObject;


@RestController
public class BankController {

	BudgetService service = new BudgetService();
	
	/**
	 * Metodi che restituiscono i Data e i Metadata
	 * @return
	 */
	
	@RequestMapping(value = "/metadata", method = RequestMethod.GET)
	public Object metadata()
	{
		return service.getMetadata();
	}
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public Object data()
	{
		return service.getBudget();
	}
	
	/**
	 * Implementazione delle Funzioni Statistiche
	 */
	/**
	 * questa funzione restutisce tutti i budget in cui la proprietÃ  Ã¨ uguale al valore dato
	 * @param property proprieta' su cui fare la ricerca
	 * @param value valore che deve avere la proprieta'
	 * @return array list dei budget trovati
	 */
	@RequestMapping(value = "/budget/property/{property}/value/{value}", method = RequestMethod.GET)
	public ArrayList<Budget> budgetByProperty(@PathVariable("property") String property, @PathVariable("value") String value )
	{
		ArrayList<Budget> budgetsByProperty = service.getBudgetByProperty(property, value);
		return budgetsByProperty;
	}
	
	@RequestMapping(value = "/budget/average/property/{property}/value/{value}", method = RequestMethod.GET)
	public String averageBudget(@PathVariable("property") String property, @PathVariable("value") String value )
	{
		JSONObject average = new JSONObject();
		double avg = service.avgBudget(property, value);
		average.put("Average", avg);
		return average.toString();
	}

	/**
	 * Restituisce la somma dei budgets delle attivita' con variabile lei_code e  valore di lei_code scelto dall'utente
	 */
	@RequestMapping(value = "/budget/sum/property/{property}/value/{value}", method = RequestMethod.GET)
	public ResponseEntity<?> sumBudget(@PathVariable("property")String property , @PathVariable("value") String value) {
		double response = service.sumBudget(property, value);
		if (response == 0) {
			System.out.println("Error request");
			return new ResponseEntity(new Error("Error request, property : " + property +" with value : "+value+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Double>(response, HttpStatus.OK);

	}
	
	/**
	 * Restituisce il minimo di tutti i budgets con variabile scelta dall'utente
	 

	@RequestMapping(value = "/budget/min/{property}", method = RequestMethod.GET)
	public ResponseEntity<?> minBudget(@PathVariable("property")String property) {
		logger.info("Counting Business with property {} ", property);
		String response = service.minBudget(property);
		if (response.isEmpty()) {
			logger.error("Error request, variable {}  with value {} not found.", property);
			return new ResponseEntity(HttpStatus.NO_CONTENT);		
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}*/
	
}
