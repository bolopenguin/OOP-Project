package europeBanks.spring.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import europeBanks.spring.model.*;
import europeBanks.spring.parser.*;
import javaExamProject.spring.model.Business;


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
	public double sumBudget(String property, String value) {
		double n = 0;
		switch (property) {
			case "lei_code":

				for(Budget b : budgets)

				if(b.getLei_code().equals(value))
					n += b.getAmount();
				break;
			
			case "nsa":
				for(Budget b : budgets)

					if(b.getNsa().equals(value))
						n += b.getAmount();
					break;
					
			case "period":
				for(Budget b : budgets)

					if(Integer.toString(b.getPeriod()).equals(value))
						n += b.getAmount();
					break;
					
			case "item":
				for(Budget b : budgets)

					if(Double.toString(b.getItem()).equals(value))
						n += b.getAmount();
					break;
					
			case "label":
				for(Budget b : budgets)

					if(b.getLabel().equals(value))
						n += b.getAmount();
					break;
				
			case "n_quarters":
				for(Budget b : budgets)

					if(Integer.toString(b.getN_quarters()).equals(value))
						n += b.getAmount();
					break;
		}	
		return n;
	}

	@Override
	public double maxBudget(String property, String value) {
		double max = 0;
		
		int tmpInt = 0;
		double tmpDbl = 0;
		
		for(int i = 0; i < budgets.size(); i++) {
			switch(property) {
			case "lei_code":{
				if(budgets.get(i).getLei_code().equals(value))
					if(budgets.get(i).getAmount()> max) 
						max = budgets.get(i).getAmount();
					
			} break;
			case "nsa":{
				if(budgets.get(i).getNsa().equals(value))
					if(budgets.get(i).getAmount()> max) 
						max = budgets.get(i).getAmount();
			}break;
			case "period":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getPeriod() == tmpInt)
					if(budgets.get(i).getAmount()> max) 
						max = budgets.get(i).getAmount();
			} break;
			case "item":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getItem() == tmpInt)
					if(budgets.get(i).getAmount()> max) 
						max = budgets.get(i).getAmount();
			} break;
			case "label":{
				if(budgets.get(i).getLabel().equals(value))
					if(budgets.get(i).getAmount()> max) 
						max = budgets.get(i).getAmount();
			}break;
			case "amount":{
				tmpDbl = Double.parseDouble(value);
				if(budgets.get(i).getAmount() == tmpDbl)
					if(budgets.get(i).getAmount()> max) 
						max = budgets.get(i).getAmount();
			} break;
			case "n_quarters":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getN_quarters() == tmpInt)
					if(budgets.get(i).getAmount()> max) 
						max = budgets.get(i).getAmount();
			} break;
			default: break;
			}
		}
		return max;
	}
	
	/** @param variable, variabile su cui si effettua il conteggio di attivita'
	 * @value value, valore della variabile che l'utente sceglie per eseguire il conteggio
	 * @return n, conteggio finale
	 */

	public int countBudget(String property, String value) {
		int n= 0;
		switch(property) {
		case "lei_code":
			n = 0; 
			for(Budget b : budgets) {
				if (b.getLei_code().equals(value)) {
					n++;	
				}
			}
			break;

		case "nsa":
			n = 0;
			for(Budget b : budgets) {
				if (b.getNsa().equals(value)) {
					n++;
				}
			}
			break;

		case "period":
			n = 0;
			for(Budget b : budgets) {
				if (b.getPeriod() == Integer.parseInt(value)) {
					n++;
				}
			}
			break;

		case "item":
			n = 0;
			for(Budget b : budgets) {
				if(b.getItem() == Integer.parseInt(value))
					n++;
			}
			break;
			
		case "label":
			n = 0;
			for(Budget b : budgets) {
				if (b.getLabel().equals(value)) {
					n++;
				}
			}
			break;
			
		case "amount":
			n = 0;
			for(Budget b : budgets) {
				if (b.getAmount() == Double.parseDouble(value)) {
					n++;
				}
			}
			break;
			
		case "n_quarters":
			n = 0;
			for(Budget b : budgets) {
				if(b.getN_quarters() == Integer.parseInt(value))
					n++;
			}
			break;
			
		}
		return n;
	}

	/**
	 *restituisce l'oggetto messaggio valorizzato da una stringa che specifica  la variabile, scelta dall'utente, con meno business (min)
	 * @return message,  risposta all'utente
	 */
	public double minBudget(String property, String value) {
		double min = 0;
		
		int tmpInt = 0;
		double tmpDbl = 0;
		
		for(int i = 0; i < budgets.size(); i++) {
			switch(property) {
			case "lei_code":{
				if(budgets.get(i).getLei_code().equals(value))
					if
			} break;
			case "nsa":{
				if(budgets.get(i).getNsa().equals(value))
					sum += budgets.get(i).getAmount();
					n++;
			}break;
			case "period":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getPeriod() == tmpInt)
					sum += budgets.get(i).getAmount();
					n++;
			} break;
			case "item":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getItem() == tmpInt)
					sum += budgets.get(i).getAmount();
					n++;
			} break;
			case "label":{
				if(budgets.get(i).getLabel().equals(value))
					sum += budgets.get(i).getAmount();
					n++;
			}break;
			case "amount":{
				tmpDbl = Double.parseDouble(value);
				if(budgets.get(i).getAmount() == tmpDbl)
					sum += budgets.get(i).getAmount();
					n++;
			} break;
			case "n_quarters":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getN_quarters() == tmpInt)
					sum += budgets.get(i).getAmount();
					n++;
			} break;
			default: break;
			}
		}
		double avg = sum/n;
		return avg;
	}

	@Override
	public double avgBudget(String property, String value) {
		double sum = 0;
		int n = 0;
		
		int tmpInt = 0;
		double tmpDbl = 0;
		
		for(int i = 0; i < budgets.size(); i++) {
			switch(property) {
			case "lei_code":{
				if(budgets.get(i).getLei_code().equals(value))
					sum += budgets.get(i).getAmount();
					n++;
			} break;
			case "nsa":{
				if(budgets.get(i).getNsa().equals(value))
					sum += budgets.get(i).getAmount();
					n++;
			}break;
			case "period":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getPeriod() == tmpInt)
					sum += budgets.get(i).getAmount();
					n++;
			} break;
			case "item":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getItem() == tmpInt)
					sum += budgets.get(i).getAmount();
					n++;
			} break;
			case "label":{
				if(budgets.get(i).getLabel().equals(value))
					sum += budgets.get(i).getAmount();
					n++;
			}break;
			case "amount":{
				tmpDbl = Double.parseDouble(value);
				if(budgets.get(i).getAmount() == tmpDbl)
					sum += budgets.get(i).getAmount();
					n++;
			} break;
			case "n_quarters":{
				tmpInt = Integer.parseInt(value);
				if(budgets.get(i).getN_quarters() == tmpInt)
					sum += budgets.get(i).getAmount();
					n++;
			} break;
			default: break;
			}
		}
		double avg = sum/n;
		return avg;
	}

	@Override
	public double devstdBudget(String property, String value) {
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
