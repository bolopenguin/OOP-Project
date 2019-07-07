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
 * Classe per gestire le richieste fatte all'applicazione attraverso la porta 8080
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 *
 */
@RestController
public class BankController {

	BudgetService service = new BudgetService();
	
	/**
	 * Metodo che restituiscono i Metadata
	 * @return
	 */
	
	@RequestMapping(value = "/metadata", method = RequestMethod.GET)
	public ArrayList<Metadata> metadata()
	{
		return service.getMetadata();
	}
	
	/**
	 * Metodo che restituiscono i Data
	 * @return
	 */
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ArrayList<Budget> data(
			@RequestParam(name = "logicalOperator", defaultValue = "") String logicalOperator, 
			@RequestParam(name = "conditionalOperator", defaultValue = "") String conditionalOperator, 
			@RequestParam(name = "field", defaultValue = "") String field, 
			@RequestParam(name = "value", defaultValue = "") String value, 
			@RequestParam(name = "field1", defaultValue = "") String field1, 
			@RequestParam(name = "value1", defaultValue = "") String value1
			) throws Exception
	{	
		return service.getBudget();
	}
	
	/**
	 * Implementazione delle Funzioni Statistiche
	 */
	
	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public String statsBudget(@RequestParam("property") String property) 
			throws Exception
	{
		JSONObject stats = new JSONObject();
		
		try {
			
			if(!(property.toLowerCase().equals("lei_code") || property.toLowerCase().equals("nsa") || property.toLowerCase().equals("label") )) {
				stats.put("average", service.avgBudget(property.toLowerCase()));
				stats.put("sum", service.sumBudget(property.toLowerCase()));
				stats.put("count", service.countBudget(property.toLowerCase()));
				stats.put("max",service.maxBudget(property.toLowerCase()));
				stats.put("min", service.minBudget(property.toLowerCase()));
				stats.put("devstd", service.devstdBudget(property.toLowerCase()));			
			} else {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map = service.getUniqueString(property.toLowerCase());
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
				    Map.Entry pairs = (Map.Entry)it.next();
				    stats.put(pairs.getKey().toString(), pairs.getValue() );
				}
			}
	
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		return stats.toString();
	}
	
	
	/**
	 * Funzione che calcola la media dei valori della proprietà scelta
	 * @param property proprietà su cui calcolare i valori
	 * @return media 
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/stats/average", method = RequestMethod.GET)
	public String avgBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject average = new JSONObject();
		try {
			average.put("average", service.avgBudget(property.toLowerCase()));
		} catch (Exception e) {
			
			Error.noPropertyMsg(property);
		}
		
		return average.toString();
	}

	/**
	 * Funzione che calcola la somma dei valori della proprieta' scelta
	 * @param property proprietà su cui calcolare i valori
	 * @return somma
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/stats/sum", method = RequestMethod.GET)
	public String sumBudget(@RequestParam("property")String property) throws Exception 
	{
		JSONObject addiction = new JSONObject();
		try {
			addiction.put("sum", service.sumBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		
		return addiction.toString();

	}
	
	/**
	 * Funzione che calcola il numero di occorrenze della proprieta' scelta
	 * @param property proprietà di cui calcolare le occorrenze
	 * @return conteggio
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/stats/count", method = RequestMethod.GET)
	public String countBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject counter = new JSONObject();
		try {
			counter.put("count", service.countBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		
		return counter.toString();
	}
	
	/**
	 * Funzione che calcola il massimo dei valori della proprieta' scelta
	 * @param property proprietà in cui cercare il massimo
	 * @return massimo
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/stats/max", method = RequestMethod.GET)
	public String maxBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject max = new JSONObject();
		try {
			max.put("max",service.maxBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		
		return max.toString();
	}
	
	/**
	 * Funzione che calcola il minimo dei valori della proprieta' scelta
	 * @param property proprietà in cui cercare il minimo
	 * @return minimo
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/stats/min", method = RequestMethod.GET)
	public String minBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject min = new JSONObject();
		try {
			min.put("min", service.minBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		
		return min.toString();
	}
	
	
	/**
	 * Funzione che calcola la deviazione standard dei valori della proprieta' scelta
	 * @param property proprietà su cui calcolare la deviazione standard
	 * @return deviazione standard
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/stats/devstd", method = RequestMethod.GET)
	public String devstdBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject devstd = new JSONObject();
		try {
			devstd.put("devstd", service.devstdBudget(property.toLowerCase()));
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		
		return devstd.toString();
	}
	
	/**
	 * Funzione che calcola il numero di occorrenze degli elementi unici su una proprieta'
	 * @param property proprietà su individuare gli elementi unici
	 * @return mappa che contiene elementi del tipo <valore , numero di occorrenze>
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/stats/unique", method = RequestMethod.GET)
	public Map<String, Integer> getUniqueString(@RequestParam("property") String property) throws Exception
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			map = service.getUniqueString(property.toLowerCase());
		} catch (Exception e) {
			Error.noPropertyMsg(property);
		}
		
		return map;
	}

	/**
	 * Implementazione dei Filtri
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
