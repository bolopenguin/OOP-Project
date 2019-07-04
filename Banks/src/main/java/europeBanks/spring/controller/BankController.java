package europeBanks.spring.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import europeBanks.spring.model.*;
import europeBanks.spring.service.BudgetService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		double avg;
		try {
			avg = service.avgBudget(property);
			average.put("average", avg);
		} catch (Exception e) {
			average.put("error", "Could not find the input Property");
		}
		
		return average.toString();
	}

	/**
	 * Restituisce la somma dei budgets delle attivita' con variabile lei_code e  valore di lei_code scelto dall'utente
	 */
	@RequestMapping(value = "/budget/sum", method = RequestMethod.GET)
	public String sumBudget(@RequestParam("property")String property) 
	{
		JSONObject addiction = new JSONObject();
		double sum;
		try {
			sum = service.sumBudget(property);
			addiction.put("sum", sum);
		} catch (Exception e) {
			addiction.put("error", "Could not find the input Property");
		}
		
		return addiction.toString();

	}
	
	/**
	 * Restituisce il
	 */
	@RequestMapping(value = "/budget/count", method = RequestMethod.GET)
	public String countBudget(@RequestParam("property") String property)
	{
		JSONObject counter = new JSONObject();
		double count;
		try {
			count = service.countBudget(property);
			counter.put("count", count);
		} catch (Exception e) {
			counter.put("count", "Could not find the input Property or is empty");
		}
		
		return counter.toString();
	}
	
	@RequestMapping(value = "/budget/max", method = RequestMethod.GET)
	public String maxBudget(@RequestParam("property") String property)
	{
		JSONObject max = new JSONObject();
		double maximum;
		try {
			maximum = service.maxBudget(property);
			max.put("max", maximum);
		} catch (Exception e) {
			max.put("error", "Could not find the input Property");
		}
		
		return max.toString();
	}
	
	@RequestMapping(value = "/budget/min", method = RequestMethod.GET)
	public String minBudget(@RequestParam("property") String property)
	{
		JSONObject min = new JSONObject();
		double minimum;
		try {
			minimum = service.minBudget(property);
			min.put("min", minimum);
		} catch (Exception e) {
			min.put("error", "Could not find the input Property");
		}
		
		return min.toString();
	}
	
	@RequestMapping(value = "/budget/devstd", method = RequestMethod.GET)
	public String devstdBudget(@RequestParam("property") String property)
	{
		JSONObject devstd = new JSONObject();
		double deviation;
		try {
			deviation = service.devstdBudget(property);
			devstd.put("devstd", deviation);
		} catch (Exception e) {
			devstd.put("error", "Could not find the input Property");
		}
		
		return devstd.toString();
	}
	
	@RequestMapping(value = "/budget/unique", method = RequestMethod.GET)
	public Map<String, Integer> getUniqueString(@RequestParam("property") String property)
	{
		Map<String, Integer> map = service.getUniqueString(property);
		
		return map;
	}

	
}
