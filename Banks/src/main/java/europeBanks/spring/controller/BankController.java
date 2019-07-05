package europeBanks.spring.controller;

import org.springframework.http.HttpStatus;
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
import java.util.Map;

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
	public Object metadata()
	{
		return service.getMetadata();
	}
	
	/**
	 * Metodo che restituiscono i Data
	 * @return
	 */
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public Object data()
	{
		return service.getBudget();
	}
	
	/**
	 * Implementazione delle Funzioni Statistiche
	 */
	
	
	/**
	 * Funzione che calcola la media dei valori della proprietà scelta
	 * @param property proprietà su cui calcolare i valori
	 * @return media 
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/budget/average", method = RequestMethod.GET)
	public String avgBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject average = new JSONObject();
		double avg;
		try {
			avg = service.avgBudget(property.toLowerCase());
			average.put("average", avg);
		} catch (Exception e) {
			
			Error.errorMsg(property);
		}
		
		return average.toString();
	}

	/**
	 * Funzione che calcola la somma dei valori della proprieta' scelta
	 * @param property proprietà su cui calcolare i valori
	 * @return somma
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/budget/sum", method = RequestMethod.GET)
	public String sumBudget(@RequestParam("property")String property) throws Exception 
	{
		JSONObject addiction = new JSONObject();
		double sum;
		try {
			sum = service.sumBudget(property.toLowerCase());
			addiction.put("sum", sum);
		} catch (Exception e) {
			Error.errorMsg(property);
		}
		
		return addiction.toString();

	}
	
	/**
	 * Funzione che calcola il numero di occorrenze della proprieta' scelta
	 * @param property proprietà di cui calcolare le occorrenze
	 * @return conteggio
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/budget/count", method = RequestMethod.GET)
	public String countBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject counter = new JSONObject();
		double count;
		try {
			count = service.countBudget(property.toLowerCase());
			counter.put("count", count);
		} catch (Exception e) {
			Error.errorMsg(property);
		}
		
		return counter.toString();
	}
	
	/**
	 * Funzione che calcola il massimo dei valori della proprieta' scelta
	 * @param property proprietà in cui cercare il massimo
	 * @return massimo
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/budget/max", method = RequestMethod.GET)
	public String maxBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject max = new JSONObject();
		double maximum;
		try {
			maximum = service.maxBudget(property.toLowerCase());
			max.put("max", maximum);
		} catch (Exception e) {
			Error.errorMsg(property);
		}
		
		return max.toString();
	}
	
	/**
	 * Funzione che calcola il minimo dei valori della proprieta' scelta
	 * @param property proprietà in cui cercare il minimo
	 * @return minimo
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/budget/min", method = RequestMethod.GET)
	public String minBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject min = new JSONObject();
		double minimum;
		try {
			minimum = service.minBudget(property.toLowerCase());
			min.put("min", minimum);
		} catch (Exception e) {
			Error.errorMsg(property);
		}
		
		return min.toString();
	}
	
	
	/**
	 * Funzione che calcola la deviazione standard dei valori della proprieta' scelta
	 * @param property proprietà su cui calcolare la deviazione standard
	 * @return deviazione standard
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/budget/devstd", method = RequestMethod.GET)
	public String devstdBudget(@RequestParam("property") String property) throws Exception
	{
		JSONObject devstd = new JSONObject();
		double deviation;
		try {
			deviation = service.devstdBudget(property.toLowerCase());
			devstd.put("devstd", deviation);
		} catch (Exception e) {
			Error.errorMsg(property);
		}
		
		return devstd.toString();
	}
	
	/**
	 * Funzione che calcola il numero di occorrenze degli elementi unici su una proprieta'
	 * @param property proprietà su individuare gli elementi unici
	 * @return mappa che contiene elementi del tipo <valore , numero di occorrenze>
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/budget/unique", method = RequestMethod.GET)
	public Map<String, Integer> getUniqueString(@RequestParam("property") String property) throws Exception
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			map = service.getUniqueString(property.toLowerCase());
		} catch (Exception e) {
			Error.errorMsg(property);
		}
		
		return map;
	}

	
}
