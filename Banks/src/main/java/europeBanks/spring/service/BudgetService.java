package europeBanks.spring.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import europeBanks.spring.model.*;
import europeBanks.spring.parser.*;
import europeBanks.spring.util.FilterTools;



/**
 * Classe in cui vengono implementate le diverse operazioni da compiere sul dataset
 * Inoltre sono contenuti i metodo per andare a settare i data e i metadata
 * cioe' i filtri e le operazioni statistiche
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

@Service("BudgetService")
public class BudgetService implements IBudgetService {

	//Array list in cui sono contenuti i budgets e i metadata
	private static ArrayList<Budget> budgets;
	private static ArrayList<Metadata> metadata;

	/**
	 * Metodo per andare a scaricare e parsare i file JSON e CSV
	 * alla fine si setta la variabilie budgets che conterra' gli oggetti ricavati dal csv
	 * @throws IOException
	 */
	public static void setBudgets() throws IOException {
		// Nomi dei file Csv e Json
		String nameCSV = "Budget.csv";
		String nameJSON = "JsonFile.json";

		// si crea l'oggetto ParserJson
		ParserJSON parse = new ParserJSON (nameJSON, nameCSV);
		// url in cui si trova il file Json da scaricare
		String url_json = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-wide-transparency-exercise-results-templates-2016";

		// si scarica il file Json
		parse.downloadJSON(url_json,parse.getNameJSON());
		System.out.println("Download JSON completato");
		// si scarica il file CSV
		parse.downloadCSV(parse.getURL(nameJSON), parse.getNameCSV());
		System.out.println("Download CSV completato");
		// si fa il parsing 
		List<List<String>> records = ParserCSV.readFile(parse.getNameCSV());
		ParserCSV.parserCSV(records);
		
		// si salva il risultato del parsing nella variabile budgets
		budgets = ParserCSV.getBudgets();
	}
	
	/**
	 * Metodo per settare i metadata dell'applicazione
	 * @param metadata
	 */
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

	/**
	 * Getter dei budget
	 */
	@Override
	public ArrayList<Budget> getBudget() {
		return budgets;
	}
	/**
	 * Getter dei metadata
	 */
	@Override
	public ArrayList<Metadata> getMetadata() {
		return metadata;
	}

	
	//implementazione delle operazioni statistiche
	
	/**
	 * Metodo per calcolare la somma di un campo property
	 */
	@Override
	public double sumBudget(String property) throws Exception {
		double sum = 0;
		// in base al caso si va a prendere un campo diverso su cui fare la somma
		switch(property) {
			case "period":{
				for(int i = 0; i < budgets.size(); i++) {
					if(budgets.get(i).getPeriod() != 0) 
						sum += budgets.get(i).getPeriod();
				}
			} break;
			case "item":{
				for(int i = 0; i < budgets.size(); i++) {
					if(budgets.get(i).getItem() != 0) 
						sum += budgets.get(i).getItem();
				}
			} break;
			case "amount":{
				for(int i = 0; i < budgets.size(); i++) {
					if(!Double.isNaN(budgets.get(i).getAmount())) {
						sum += budgets.get(i).getAmount();
					}
				}
			} break;
			case "n_quarters":{
				for(int i = 0; i < budgets.size(); i++) {
					if(budgets.get(i).getN_quarters() != 0) 
						sum += budgets.get(i).getN_quarters();
				}
			} break;
			default: throw new Exception();
		}
		
		return sum;
	}

	/**
	 * Metodo per trovare il massimo valore di un campo property
	 */
	@Override
	public double maxBudget(String property) throws Exception{
		double max = 0;

		switch(property) {
		case "period":{
			for(int i = 0; i < budgets.size(); i++) {
				// deve essere diverso da zero perche' period non puo' essere uguale a zero
				if(budgets.get(i).getPeriod() > max && budgets.get(i).getPeriod() != 0) 
					max = Double.valueOf(budgets.get(i).getPeriod());
			}
		} break;
		case "item":{
			// deve essere diverso da zero perche' item non puo' essere uguale a zero
			for(int i = 0; i < budgets.size(); i++) {
				if(budgets.get(i).getItem()> max && budgets.get(i).getItem() != 0) 
					max = Double.valueOf(budgets.get(i).getItem());
			}
		} break;
		case "amount":{
			for(int i = 0; i < budgets.size(); i++) {
				// deve essere diverso da zero perche' amount non puo' essere uguale un nan
				if(budgets.get(i).getAmount()> max && !Double.isNaN(budgets.get(i).getAmount())) 
					max = budgets.get(i).getAmount();
			}
		} break;
		case "n_quarters":{
			for(int i = 0; i < budgets.size(); i++) {
				// deve essere diverso da zero perche' n_quarters non puo' essere uguale a zero
				if(budgets.get(i).getN_quarters()> max && budgets.get(i).getN_quarters() != 0) 
					max = Double.valueOf(budgets.get(i).getN_quarters());
			}
		} break;
		
		default: throw new Exception();
		
		}
		
		return max;
	}

	/**
	 * Metodo che conta il numero di occorenze di un campo property
	 */
	@Override
	public int countBudget(String property) throws Exception {
		int counter = 0;

		switch(property) {
		case "period":{
			for(int i = 0; i < budgets.size(); i++) {
				// deve essere diverso da zero perche' period non puo' essere uguale a zero
				if(budgets.get(i).getPeriod() != 0) 
					counter++;
			}
		} break;
		case "item":{
			for(int i = 0; i < budgets.size(); i++) {
				// deve essere diverso da zero perche' item non puo' essere uguale a zero
				if(budgets.get(i).getItem() != 0) 
					counter++;
			}
		} break;
		case "amount":{
			for(int i = 0; i < budgets.size(); i++) {
				// deve essere diverso da zero perche' amount non puo' essere un nan
				if(!Double.isNaN(budgets.get(i).getAmount())) {
					counter++;
				}
			}
		} break;
		case "n_quarters":{
			for(int i = 0; i < budgets.size(); i++) {
				// deve essere diverso da zero perche' n_quarters non puo' essere uguale a zero
				if(budgets.get(i).getN_quarters() != 0) 
					counter++;
			}
		} break;
		default: throw new Exception();
	}	
		return counter;
	}

	/**
	 * Metodo che calcola la media dei valori di un campo property
	 */
	@Override
	public double avgBudget(String property) throws Exception{
		double sum = 0;
		int n = 0;	
		
		switch(property) {
		case "period":{
			for(int i = 0; i < budgets.size(); i++) {
				// deve essere diverso da zero perche' period non puo' essere uguale a zero
				if(budgets.get(i).getPeriod() != 0) {
					sum += budgets.get(i).getPeriod();
					n++;
				}
			}
		} break;
		case "item":{
			for(int i = 0; i < budgets.size(); i++) {
				if(budgets.get(i).getItem() != 0) {
					// deve essere diverso da zero perche' item non puo' essere uguale a zero
					sum += budgets.get(i).getItem();
					n++;
				}
			}
		} break;
		case "amount":{
			for(int i = 0; i < budgets.size(); i++) {
				if(!Double.isNaN(budgets.get(i).getAmount())) {
					// deve essere diverso da zero perche' amount non puo' essere un nan
					sum += budgets.get(i).getAmount();
					n++;
				}
			}
		} break;
		case "n_quarters":{
			for(int i = 0; i < budgets.size(); i++) {
				if(budgets.get(i).getN_quarters() != 0) {
					// deve essere diverso da zero perche' n_quarters non puo' essere uguale a zero
					sum += budgets.get(i).getN_quarters();
					n++;
				}
			}
		} break;
		default: throw new Exception();
	
	}
		double avg = sum/n;
		return avg;

	}
	
	/**
	 * Metodo per trovare il minimo valore di un campo property
	 */
	@Override
	public double minBudget(String property) throws Exception{
		double min = 0;
		
		switch(property) {
			case "period":{
				min = budgets.get(0).getPeriod();
				for(int i = 1; i < budgets.size(); i++) {
					// deve essere diverso da zero perche' period non puo' essere uguale a zero
					if(budgets.get(i).getPeriod() < min && budgets.get(i).getPeriod() != 0 ) 
						min = Double.valueOf(budgets.get(i).getPeriod());
				}
			} break;
			case "item":{
				min = budgets.get(0).getItem();
				for(int i = 1; i < budgets.size(); i++) {
					// deve essere diverso da zero perche' item non puo' essere uguale a zero
					if(budgets.get(i).getItem() < min && budgets.get(i).getItem() != 0 ) 
						min = Double.valueOf(budgets.get(i).getItem());
				}
			} break;
			case "amount":{
				min = budgets.get(0).getAmount();
				for(int i = 1; i < budgets.size(); i++) {
					// deve essere diverso da zero perche' amount non puo' essere un nan
					if(budgets.get(i).getAmount() < min && !Double.isNaN(budgets.get(i).getAmount())) 
						min = budgets.get(i).getAmount();
				}
			} break;
			case "n_quarters":{
				min = budgets.get(0).getN_quarters();
				for(int i = 1; i < budgets.size(); i++) {
					// deve essere diverso da zero perche' n_quarters non puo' essere uguale a zero
					if(budgets.get(i).getN_quarters() < min && budgets.get(i).getN_quarters() != 0 ) 
						min = Double.valueOf(budgets.get(i).getN_quarters());
				}
			} break;
			default:throw new Exception();
		}

		return min;
	}

	/**
	 * Metodo per calcolare la deviazione standart di un campo property
	 */
	@Override
	public double devstdBudget(String property) throws Exception{
		
		double count=0.0;
		double avg = avgBudget(property);
		double num= 0.0;
		
		switch (property) {
		
		case "period":{
			for(Budget b : budgets){
				// deve essere diverso da zero perche' period non puo' essere uguale a zero
				if(b.getPeriod()== 0) {
					continue;
				}else {
					num += Math.pow((b.getPeriod() - avg), 2);
					count++;
				}
			}
		} break;
			
		case "item":{
			for(Budget b : budgets){
				// deve essere diverso da zero perche' item non puo' essere uguale a zero
				if(b.getItem()== 0) {
					continue;
				}else {
					num += Math.pow((b.getItem() - avg), 2);
					count++;
				}
			}
		} break;
			
		case "amount":{
			for(Budget b : budgets){
				// deve essere diverso da zero perche' n_quarters non puo' essere un nan
				if(!Double.isNaN(b.getAmount())) {
					num += Math.pow((b.getAmount() - avg), 2);
					count++;	
					}
				}
		} break;
		
		case "n_quarters":{
			for(Budget b : budgets){
				// deve essere diverso da zero perche' n_quarters non puo' essere uguale a zero
				if(b.getN_quarters()== 0) {
					continue;
				}else {
					num += Math.pow((b.getN_quarters() - avg), 2);
					count++;
				}
			}
		} break;
		default: throw new Exception();
		}
		double devStdB = Math.sqrt( num / count);
		return devStdB; 
	}

	/**
	 * Metodo per calcolare gli elementi unici di un campo property
	 */
	@Override
	public Map<String, Integer> getUniqueString(String property) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int count = 0;
		
		switch(property) {
			case "lei_code":{
				for(int i = 0; i < budgets.size(); i++) {
					map.put(budgets.get(i).getLei_code(), count++);
				}
				
			} break;
			case "nsa":{
				for(int i = 0; i < budgets.size(); i++) {
					map.put(budgets.get(i).getNsa(), count++);
				}
			} break;
			case "label":{
				for(int i = 0; i < budgets.size(); i++) {
					map.put(budgets.get(i).getLabel(), count++);
				}
				
			} break;
			default: throw new Exception();
		}
		return map;
	}

	//implementazione dei filtri
	
	/**
	 * Metodo per effettuare il filtro condizionale
	 */
	@Override
	public ArrayList<Budget> conditionalFilter(String fieldName, String number, String operator) throws Exception {
		ArrayList<Budget> budgetsFiltered = new ArrayList<Budget>();
		switch(operator) {
		case "<":{
			for(Budget b : budgets) {
				// si prende il getter per prendere il valore della proprieta' che ci interessa
				Method m = b.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
				// se viene chiesto un double si deve convertire le stringhe in double
				if(Double.class.isInstance(m.invoke(b))) {
					double tmp = (double) m.invoke(b);
					double value = Double.parseDouble(number);				
					if(tmp < value) 
						budgetsFiltered.add(b);
				// se non viene chiesto un double viene chiesto un int e quindi bisogna convertire le stringhe in int	
				} else {
					int tmp = (int) m.invoke(b);
					int value = Integer.parseInt(number);				
					if(tmp < value) 
						budgetsFiltered.add(b);
				}
			}
			
		} break;
		case "<=":{
			for(Budget b : budgets) {
				Method m = b.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
				if(Double.class.isInstance(m.invoke(b))) {
					double tmp = (double) m.invoke(b);
					double value = Double.parseDouble(number);				
					if(tmp <= value) 
						budgetsFiltered.add(b);
				} else {
					int tmp = (int) m.invoke(b);
					int value = Integer.parseInt(number);				
					if(tmp <= value) 
						budgetsFiltered.add(b);
				}
			}
			
		} break;
		case "=":{
			for(Budget b : budgets) {
				Method m = b.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
				if(Double.class.isInstance(m.invoke(b))) {
					double tmp = (double) m.invoke(b);
					double value = Double.parseDouble(number);				
					if(tmp == value) 
						budgetsFiltered.add(b);
				} else {
					int tmp = (int) m.invoke(b);
					int value = Integer.parseInt(number);				
					if(tmp == value) 
						budgetsFiltered.add(b);
				}
			}
		} break;
		case ">=":{
			for(Budget b : budgets) {
				Method m = b.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
				if(Double.class.isInstance(m.invoke(b))) {
					double tmp = (double) m.invoke(b);
					double value = Double.parseDouble(number);				
					if(tmp >= value) 
						budgetsFiltered.add(b);
				} else {
					int tmp = (int) m.invoke(b);
					int value = Integer.parseInt(number);				
					if(tmp >= value) 
						budgetsFiltered.add(b);
				}
			}
		} break;
		case ">":{
			for(Budget b : budgets) {
				Method m = b.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
				if(Double.class.isInstance(m.invoke(b))) {
					double tmp = (double) m.invoke(b);
					double value = Double.parseDouble(number);				
					if(tmp > value) 
						budgetsFiltered.add(b);
				} else {
					int tmp = (int) m.invoke(b);
					int value = Integer.parseInt(number);				
					if(tmp > value) 
						budgetsFiltered.add(b);
				}
			}
		} break;
		default: throw new Exception();
		}
		
		return budgetsFiltered;
	}

	/**
	 * Metodo per effettuare il filtro logico
	 */
	@Override
	public ArrayList<Budget> logicalFilter(String fieldName1, String value1, String operator, String fieldName2, String value2)throws Exception {
		ArrayList<Budget> budgetsFiltered = new ArrayList<Budget>();
		operator = operator.toLowerCase();
		
		switch(operator) {
		case "and":{
			// se manca il secondo campo viene lanciata un'eccezione
			if(fieldName2.isEmpty() || value2.isEmpty())
				throw new Exception();
			for(Budget b : budgets) {
				// si vanno a prendere i valori su cui bisogna fare il confronto e le si salvano in delle stringhe
				String tmp1 = FilterTools.getFilterString(fieldName1, value1, b);
				String tmp2 = FilterTools.getFilterString(fieldName2, value2, b);
				// si effettua il confronto 
				if(tmp1.toLowerCase().equals(value1.toLowerCase()) && tmp2.toLowerCase().equals(value2.toLowerCase()))
					budgetsFiltered.add(b);
			}
			
		} break;
		case "or":{
			if(fieldName2.isEmpty() || value2.isEmpty())
				throw new Exception();
			for(Budget b : budgets) {
				
				String tmp1 = FilterTools.getFilterString(fieldName1, value1, b);
				String tmp2 = FilterTools.getFilterString(fieldName2, value2, b);
				
				if(tmp1.toLowerCase().equals(value1.toLowerCase()) || tmp2.toLowerCase().equals(value2.toLowerCase()))
					budgetsFiltered.add(b);
			}
		} break;
		case "not":{
			for(Budget b : budgets) {
				
				String tmp1 = FilterTools.getFilterString(fieldName1, value1, b);
				
				if(!tmp1.toLowerCase().equals(value1.toLowerCase()))
					budgetsFiltered.add(b);
			}
		} break;
		case "in":{
			for(Budget b : budgets) {
				
				try (Scanner rowScanner = new Scanner(value1)) {
					rowScanner.useDelimiter(",");
					while(rowScanner.hasNext()) {
						
						String tmp1 = rowScanner.next().toLowerCase();
						String tmp2 = FilterTools.getFilterString(fieldName1, tmp1, b);
						
						if(tmp2.toLowerCase().equals(tmp1))
							budgetsFiltered.add(b);
					}
					rowScanner.close();
				}
			}
		} break;
		case "nin":{
			for(Budget b : budgets) {
				try (Scanner rowScanner = new Scanner(value1)) {
					rowScanner.useDelimiter(",");
					while(rowScanner.hasNext()) {
						
						String tmp1 = rowScanner.next().toLowerCase();
						String tmp2 = FilterTools.getFilterString(fieldName1, tmp1, b);
						
						if(!tmp2.toLowerCase().equals(tmp1))
							budgetsFiltered.add(b);
					}
					rowScanner.close();
				}
			}
		} break;
		default: throw new Exception();
		}
		
		return budgetsFiltered;
	}

	
	
}
