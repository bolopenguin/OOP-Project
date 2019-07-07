package europeBanks.spring.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import europeBanks.spring.model.*;

/** 
 * Interfaccia contenente i metodi che verranno implementati in BudjetService
 * 
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

public interface IBudgetService {
	/**
	 * Dati e Metadati
	 */
	
	ArrayList <Budget> getBudget();

	ArrayList<Metadata> getMetadata();
	
	/**
	 * statistiche
	 * @throws Exception 
	 */
	
	double sumBudget(String property) throws Exception;
	double maxBudget(String property) throws Exception;
	double minBudget(String property) throws Exception;
	double avgBudget(String property) throws Exception;
	double devstdBudget(String property) throws Exception;
	
	int countBudget(String property) throws Exception;
	
	Map<String, Integer> getUniqueString(String property) throws Exception;
	
	/**
	 * filtri
	 * @throws Exception 
	 */

	ArrayList<Budget> conditionalFilter(String fieldName, String number, String operator) throws Exception;
	
	ArrayList<Budget> logicalFilter(String fieldName1, String value1, String operator, String fieldName2, String value2) throws Exception;

}
