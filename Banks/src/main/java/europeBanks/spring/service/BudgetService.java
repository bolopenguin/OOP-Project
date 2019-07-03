package europeBanks.spring.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import europeBanks.spring.model.*;
import europeBanks.spring.parser.*;



/**
 * classe in cui vengono implementate le diverse operazioni da compiere sul dataset
 * inoltre viene fatto partire il download del file ed effettuato il parsing all'avvio dell'applicazione
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

@Service("BudgetService")
public class BudgetService implements IBudgetService {

	/**
	 * liste di array in cui andremo a inserire rispettivamente i budgets del dataset e i metadata
	 */
	private static ArrayList<Budget> budgets;
	private static ArrayList<Metadata> metadata;

	
	public static void setBudgets() throws IOException {
		String nameCSV = "Budget.csv";
		String nameJSON = "JsonFile.json";

		ParserJSON parse = new ParserJSON (nameJSON, nameCSV);
		String url_json = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-wide-transparency-exercise-results-templates-2016";

		parse.downloadJSON(url_json,parse.getNameJSON());
		System.out.println("DONE");
		parse.downloadCSV(parse.getURL(nameJSON), parse.getNameCSV());
		List<List<String>> records = ParserCSV.readFile(parse.getNameCSV());


		ParserCSV.parserCSV(records);
		budgets = ParserCSV.getBudgets();
	}
	
	public static void setMetadata(ArrayList<Metadata> metadata) {
		metadata.add(new Metadata("lei_code","Legal Entity Identifier Code","String"));
		metadata.add(new Metadata("nsa","Country","String"));
		metadata.add(new Metadata("period","Periodo di Riferimento","Integer"));
		metadata.add(new Metadata("item","Budget Item Code","Integer"));
		metadata.add(new Metadata("label","Label of the Budget Item","String"));
		metadata.add(new Metadata("amount","Budget Item Amount","Double"));
		metadata.add(new Metadata("n_quarters","Trimestre di Riferimento","Integer"));
		BudgetService.metadata = metadata;
		}

	@Override
	public ArrayList<Budget> getBudget() {
		return budgets;
	}
	
	@Override
	public ArrayList<Metadata> getMetadata() {
		return metadata;
	}

	
	//implementazione delle operazioni
	
	@Override
	/**
	 * restituisce la somma dei budgets con variabile uguale a lei_code e valore scelto dall'utente
	 * @param n, totale degli amount
	 * 
	 */
	public double sumBudget(String property) {
		double sum = 0;
		int n = 0;
		
		for(int i = 0; i < budgets.size(); i++) {
			switch(property) {
			case "period":{
					sum += budgets.get(i).getPeriod();
					n++;
			} break;
			case "item":{
				sum += budgets.get(i).getItem();
				n++;
			} break;
			case "amount":{
				sum += budgets.get(i).getAmount();
				n++;
			} break;
			case "n_quarters":{
				sum += budgets.get(i).getN_quarters();
				n++;
			} break;
			default: break;
			}
		}
		//gestire errore
		return sum;
	}

	@Override
	public double maxBudget(String property) {
		double max = 0;
		
		
		for(int i = 0; i < budgets.size(); i++) {
			switch(property) {
			case "period":{
					if(budgets.get(i).getPeriod()> max) 
						max = Double.valueOf(budgets.get(i).getPeriod());
			} break;
			case "item":{
					if(budgets.get(i).getItem()> max) 
						max = Double.valueOf(budgets.get(i).getItem());
			} break;
			case "amount":{
					if(budgets.get(i).getAmount()> max) 
						max = budgets.get(i).getAmount();
			} break;
			case "n_quarters":{
					if(budgets.get(i).getN_quarters()> max) 
						max = Double.valueOf(budgets.get(i).getN_quarters());
			} break;
			default: break;
			}
		}
		// gestire errore
		return max;
	}
	
	/** @param variable, variabile su cui si effettua il conteggio di attivita'
	 * @value value, valore della variabile che l'utente sceglie per eseguire il conteggio
	 * @return n, conteggio finale
	 */

	@Override
	public int countBudget(String property) {
		int counter = 0;
		
		for(int i = 0; i < budgets.size(); i++) {
			switch(property) {
			case "lei_code":{
				if(budgets.get(i).getLei_code() != null) counter++;
			} break;
			case "nsa":{
				if(budgets.get(i).getNsa() != null) counter++;
			} break;
			case "period":{
				 counter++;
			} break;
			case "item":{
				 counter++;
			} break;
			case "label":{
				if(budgets.get(i).getLabel() != null) counter++;
			} break;
			case "amount":{
				counter++;
			} break;
			case "n_quarters":{
				counter++;
			} break;
			default: break;
			}
		}
		//gestire errore
		return counter;
	}

	@Override
	public double avgBudget(String property) {
		double sum = 0;
		int n = 0;
		
		
		for(int i = 0; i < budgets.size(); i++) {
			switch(property) {
			case "period":{
					sum += budgets.get(i).getPeriod();
					n++;
			} break;
			case "item":{
				sum += budgets.get(i).getItem();
				n++;
			} break;
			case "amount":{
				sum += budgets.get(i).getAmount();
				n++;
			} break;
			case "n_quarters":{
				sum += budgets.get(i).getN_quarters();
				n++;
			} break;
			default: break;
			}
		}
		//gestire errore
		double avg = sum/n;
		return avg;
	}
	
	@Override
	public double minBudget(String property) {
		double min = 0;
		
		switch(property) {
			case "period":{
				min = budgets.get(0).getPeriod();
				for(int i = 1; i < budgets.size(); i++) {
					// -1 vuol dire che il parametro non è presente e quindi non va considerato
					if(budgets.get(i).getPeriod() < min ) 
						min = Double.valueOf(budgets.get(i).getPeriod());
				}
			} break;
			case "item":{
				min = budgets.get(0).getItem();
				for(int i = 1; i < budgets.size(); i++) {
					// -1 vuol dire che il parametro non è presente e quindi non va considerato
					if(budgets.get(i).getItem() < min ) 
						min = Double.valueOf(budgets.get(i).getItem());
				}
			} break;
			case "amount":{
				min = budgets.get(0).getAmount();
				for(int i = 1; i < budgets.size(); i++) {
					// -1 vuol dire che il parametro non è presente e quindi non va considerato
					if(budgets.get(i).getAmount() < min ) 
						min = budgets.get(i).getAmount();
				}
			} break;
			case "n_quarters":{
				min = budgets.get(0).getN_quarters();
				for(int i = 1; i < budgets.size(); i++) {
					// -1 vuol dire che il parametro non è presente e quindi non va considerato
					if(budgets.get(i).getN_quarters() < min ) 
						min = Double.valueOf(budgets.get(i).getN_quarters());
				}
			} break;
			default: break;
		}

		// gestire errore
		return min;
	}

	@Override
	public ArrayList<Budget> getBudgetByProperty(String property, String value) {
		ArrayList<Budget> tmp = new ArrayList<Budget>();
		int tmpInt;
		double tmpDbl;
		
		for(int i = 0; i < budgets.size(); i++) {
			switch(property) {
			case "lei_code":{
				if(budgets.get(i).getLei_code().equals(value))
					tmp.add(budgets.get(i));
			} break;
			case "nsa":{
				if(budgets.get(i).getNsa().equals(value))
					tmp.add(budgets.get(i));
			}break;
			case "period":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getPeriod() == tmpInt)
					tmp.add(budgets.get(i));
			} break;
			case "item":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getItem() == tmpInt)
					tmp.add(budgets.get(i));
			} break;
			case "label":{
				if(budgets.get(i).getLabel().equals(value))
					tmp.add(budgets.get(i));
			}break;
			case "amount":{
				tmpDbl = Double.parseDouble(value);
				if(budgets.get(i).getAmount() == tmpDbl)
					tmp.add(budgets.get(i));
			} break;
			case "n_quarters":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getN_quarters() == tmpInt)
					tmp.add(budgets.get(i));
			} break;
			default: break;
			}
		}
		
		return tmp;
	}

	//implementazione dei filtri
	

	@Override
	public double devstdBudget(String property) {
		// TODO Auto-generated method stub
		return 0;
	}


	//implementazione dei filtri
	
	@Override
	public ArrayList<Budget> amountFilter(String operator, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Budget> propertyFilter(String property, String values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Budget> ninFilter(String operator, String property, String values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Budget> notFilter(String property, String values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Budget> orFilter(String property, String values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Budget> andFilter(String property, String values) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
}
