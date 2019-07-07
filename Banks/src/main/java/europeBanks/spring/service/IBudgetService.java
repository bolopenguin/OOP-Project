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
	 * Metodo che ritorna i budget
	 * @return
	 */
	ArrayList <Budget> getBudget();
	/**
	 * Metodo che ritorna i metadata
	 * @return
	 */
	ArrayList<Metadata> getMetadata();
	
	
	// Operazioni Statistiche
	/**
	 * Metodo che calcola la somma degli elementi di una certa proprieta' che viene passata come input
	 * @param property
	 * @return
	 * @throws Exception viene lanciata nel caso in cui venga chiesta una proprieta' su cui non si puo' calcolare la somma
	 */
	double sumBudget(String property) throws Exception;
	/**
	 * Metodo che calcola il valore massimo degli elementi di una certa proprieta' che viene passata come input
	 * @param property
	 * @return
	 * @throws Exception viene lanciata nel caso in cui venga chiesta una proprieta' su cui non si puo' calcolare la somma
	 */
	double maxBudget(String property) throws Exception;
	/**
	 * Metodo che calcola il valore minimo degli elementi di una certa proprieta' che viene passata come input
	 * @param property
	 * @return
	 * @throws Exception viene lanciata nel caso in cui venga chiesta una proprieta' su cui non si puo' calcolare la somma
	 */
	double minBudget(String property) throws Exception;
	/**
	 * Metodo che calcola la media degli elementi di una certa proprieta' che viene passata come input
	 * @param property
	 * @return
	 * @throws Exception viene lanciata nel caso in cui venga chiesta una proprieta' su cui non si puo' calcolare la somma
	 */
	double avgBudget(String property) throws Exception;
	/**
	 * Metodo che calcola la deviazione standart degli elementi di una certa proprieta' che viene passata come input
	 * @param property
	 * @return
	 * @throws Exception viene lanciata nel caso in cui venga chiesta una proprieta' su cui non si puo' calcolare la somma
	 */
	double devstdBudget(String property) throws Exception;
	/**
	 * Metodo che calcola il numero degli elementi di una certa proprieta' che viene passata come input
	 * @param property
	 * @return
	 * @throws Exception viene lanciata nel caso in cui venga chiesta una proprieta' su cui non si puo' calcolare la somma
	 */
	int countBudget(String property) throws Exception;
	
	/**
	 * Metodo che calcola gli elementi unici di una certa proprieta' che viene passata come input
	 * @param property
	 * @return
	 * @throws Exception
	 */
	Map<String, Integer> getUniqueString(String property) throws Exception;
	

	// filtri

	/**
	 * Metodo che filtra i budgets secondo un filtro di tipo condizionale
	 * @param fieldName nome del campo su cui fare il filtro
	 * @param number valore di riferimento per fare il filtraggio
	 * @param operator filtro condizionale
	 * @return
	 * @throws Exception
	 */
	ArrayList<Budget> conditionalFilter(String fieldName, String number, String operator) throws Exception;
	/**
	 * Metodo che filtra i budgets secondo un filtro di tipo logico
	 * @param fieldName1 nome del primo campo su cui fare il filtro
	 * @param value1 valore di riferimento per il primo campo per fare il filtraggio
	 * @param operator filtro logico
	 * @param fieldName2 nome del secondo campo su cui fare il filtro
	 * @param value2 valore di riferimento per il secondo campo per fare il filtraggio
	 * @return
	 * @throws Exception
	 */
	ArrayList<Budget> logicalFilter(String fieldName1, String value1, String operator, String fieldName2, String value2) throws Exception;

}
