package europeBanks.spring.service;

import java.util.ArrayList;

import europeBanks.spring.model.*;

/** 
 * Interfaccia contente metodi che verranno implementati in BudjetItemServiceImplements
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
	double maxBudget(String property);
	double minBudget(String property);
	double avgBudget(String property);
	double devstdBudget(String property);
	
	int countBudget(String property);
	
	ArrayList <Budget> getBudgetByProperty(String property, String value);
	
	/**
	 * filtri
	 */
	//filtro sulla proprieta' amount (es. prende i budget con amount >= 100)
	ArrayList<Budget> amountFilter(String operator, String value);
	//filtro su una proprieta' generica (es. prende i budget con nsa = IT)
	ArrayList<Budget> propertyFilter(String property, String values);
	//filtro in o nin (es. prende i budget con nsa = IT oppure non prende i budget con nsa = IT)
	ArrayList<Budget> ninFilter(String operator, String property, String values);
	//filtro not (es. non prende i budget con nsa = FR) 
	ArrayList<Budget> notFilter(String property, String values);	
	//filtro or (es. prende i budget con nsa = FR o nsa = IT)
	ArrayList<Budget> orFilter(String property, String values);
	//filtro and (es. prende i budget con nsa = FR e n_quarters = 4)
	ArrayList<Budget> andFilter(String property, String values);

}
