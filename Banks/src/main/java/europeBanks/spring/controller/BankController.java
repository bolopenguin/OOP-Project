package europeBanks.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import europeBanks.spring.model.*;
import europeBanks.spring.service.BudgetService;

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
	
	@RequestMapping(value = "/budget/property/{value}", method = RequestMethod.GET)
	public ResponseEntity<?> budgetByProperty(@PathVariable("property") String value)
	{
		return service.getBudget();
	}
	
	
}
