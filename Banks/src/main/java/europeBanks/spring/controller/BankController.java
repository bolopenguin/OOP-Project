package europeBanks.spring.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value = "/budget", method = RequestMethod.GET)
	public ArrayList<Budget> budgetByProperty(@RequestParam("property") String property, @RequestParam("value") String value )
	{
		ArrayList<Budget> budgetsByProperty = service.getBudgetByProperty(property, value);
		return budgetsByProperty;
	}
	
	@RequestMapping(value = "/budget/average", method = RequestMethod.GET)
	public String avgBudget(@RequestParam("property") String property)
	{
		JSONObject average = new JSONObject();
		double avg = service.avgBudget(property);
		average.put("Average", avg);
		return average.toString();
	}

	/**
	 * Restituisce la somma dei budgets delle attivita' con variabile lei_code e  valore di lei_code scelto dall'utente
	 */
	@RequestMapping(value = "/budget/sum", method = RequestMethod.GET)
	public String sumBudget(@RequestParam("property")String property) 
	{
		JSONObject addiction = new JSONObject();
		double sum = service.sumBudget(property);
		addiction.put("Addiction", sum);
		return addiction.toString();

	}
	
	/**
	 * Restituisce il
	 */
	@RequestMapping(value = "/budget/count", method = RequestMethod.GET)
	public String countBudget(@RequestParam("property") String property)
	{
		JSONObject counter = new JSONObject();
		double count = service.countBudget(property);
		counter.put("Count", count);
		return counter.toString();
	}
	
	@RequestMapping(value = "/budget/max", method = RequestMethod.GET)
	public String maxBudget(@RequestParam("property") String property)
	{
		JSONObject max = new JSONObject();
		double maximum = service.maxBudget(property);
		max.put("Max", maximum);
		return max.toString();
	}
	
	@RequestMapping(value = "/budget/min", method = RequestMethod.GET)
	public String minBudget(@RequestParam("property") String property)
	{
		JSONObject min = new JSONObject();
		double minimum = service.minBudget(property);
		min.put("Min", minimum);
		return min.toString();
	}

	
}
