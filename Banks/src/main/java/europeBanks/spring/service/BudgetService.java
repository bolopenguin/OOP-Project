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
import europeBanks.spring.util.Tools;



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

	// metodi necessari per settare i data e i metadata
	
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
		System.out.println("Caricamento completato");
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
		
		for(Budget b: budgets) {
			Method m = b.getClass().getMethod("get"+property.substring(0, 1).toUpperCase()+property.substring(1),null);
			if(Tools.check(property)) {
				if(Double.class.isInstance(m.invoke(b)) && !Double.isNaN((double) m.invoke(b)) ) {
					sum += (double) m.invoke(b);
				} else if((int) m.invoke(b) != 0){
					sum += (int) m.invoke(b);
				}

			}
		}
		
		return sum;
	}

	/**
	 * Metodo per trovare il massimo valore di un campo property
	 */
	@Override
	public double maxBudget(String property) throws Exception{
		double max = 0;
		
		for(Budget b: budgets) {
			Method m = b.getClass().getMethod("get"+property.substring(0, 1).toUpperCase()+property.substring(1),null);
			if(Tools.check(property)) {
				if(Double.class.isInstance(m.invoke(b))) {
					if((double) m.invoke(b) > max &&  !Double.isNaN((double) m.invoke(b))) 
						max = (double) m.invoke(b);
				} else {
					if((int) m.invoke(b) > max && (int) m.invoke(b) != 0) 
						max = (int) m.invoke(b);
				}
			}
		}
		
		return max;
	}
	
	/**
	 * Metodo per trovare il minimo valore di un campo property
	 */
	@Override
	public double minBudget(String property) throws Exception{
		double min = 0;
		
		for(Budget b: budgets) {
			Method m = b.getClass().getMethod("get"+property.substring(0, 1).toUpperCase()+property.substring(1),null);
			if(Tools.check(property)) {
				if(Double.class.isInstance(m.invoke(b))) {
					if((double) m.invoke(b) < min &&  !Double.isNaN((double) m.invoke(b))) 
						min = (double) m.invoke(b);
				} else {
					if((int) m.invoke(b) < min && (int) m.invoke(b) != 0) 
						min = (int) m.invoke(b);
				}
			}
		}

		return min;
	}

	/**
	 * Metodo che conta il numero di occorenze di un campo property
	 */
	@Override
	public int countBudget(String property) throws Exception {
		int counter = 0;
		
		for(Budget b: budgets) {
			Method m = b.getClass().getMethod("get"+property.substring(0, 1).toUpperCase()+property.substring(1),null);
			if(Tools.check(property)) {
				if(Double.class.isInstance(m.invoke(b)) && !Double.isNaN((double) m.invoke(b)) ) {
					counter++;
				} else if((int) m.invoke(b) != 0){
					counter++;
				}

			}
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
		
		for(Budget b: budgets) {
			Method m = b.getClass().getMethod("get"+property.substring(0, 1).toUpperCase()+property.substring(1),null);
			if(Tools.check(property)) {
				if(Double.class.isInstance(m.invoke(b)) && !Double.isNaN((double) m.invoke(b)) ) {
					sum += (double) m.invoke(b);
					n++;
				} else if((int) m.invoke(b) != 0){
					sum += (int) m.invoke(b);
					n++;
				}

			}
		}
		double avg = sum/n;
		return avg;

	}
	

	/**
	 * Metodo per calcolare la deviazione standart di un campo property
	 */
	@Override
	public double devstdBudget(String property) throws Exception{
		
		double count=0.0;
		double avg = avgBudget(property);
		int num = 0;
		
		for(Budget b: budgets) {
			Method m = b.getClass().getMethod("get"+property.substring(0, 1).toUpperCase()+property.substring(1),null);
			if(Tools.check(property)) {
				if(Double.class.isInstance(m.invoke(b)) && !Double.isNaN((double) m.invoke(b)) ) {
					num += Math.pow(((double) m.invoke(b) - avg), 2);
					num++;
				} else if((int) m.invoke(b) != 0){
					num += Math.pow(((int) m.invoke(b) - avg), 2);
					num++;
				}

			}
		}
		
		return Math.sqrt( num / count);
	}

	/**
	 * Metodo per calcolare gli elementi unici di un campo property
	 */
	@Override
	public Map<String, Integer> getUniqueString(String property) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int count = 0;
		
		for(Budget b: budgets) {
			Method m = b.getClass().getMethod("get"+property.substring(0, 1).toUpperCase()+property.substring(1),null);
			if(Tools.check(property)) {
				map.put((String) m.invoke(b), count++);
			}
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
		//check(operator, tmp, value)
		for(Budget b : budgets) {
			// si prende il getter per prendere il valore della proprieta' che ci interessa
			Method m = b.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
			// se viene chiesto un double si deve convertire le stringhe in double
			if(Double.class.isInstance(m.invoke(b))) {
				double tmp = (double) m.invoke(b);
				double value = Double.parseDouble(number);				
				if(Tools.checkConditional(tmp, operator, value)) 
					budgetsFiltered.add(b);
			// se non viene chiesto un double viene chiesto un int e quindi bisogna convertire le stringhe in int	
			} else {
				int tmp = (int) m.invoke(b);
				int value = Integer.parseInt(number);				
				if(Tools.checkConditional(tmp, operator, value)) 
					budgetsFiltered.add(b);
			}
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
				String tmp1 = Tools.getFilterString(fieldName1, b);
				value1 = Tools.setValueString(fieldName1, value1, b);
				String tmp2 = Tools.getFilterString(fieldName2, b);
				value2 = Tools.setValueString(fieldName2, value2, b);
				
				// si effettua il confronto 
				if(tmp1.toLowerCase().equals(value1.toLowerCase()) && tmp2.toLowerCase().equals(value2.toLowerCase()))
					budgetsFiltered.add(b);
			}
			
		} break;
		case "or":{
			if(fieldName2.isEmpty() || value2.isEmpty())
				throw new Exception();
			for(Budget b : budgets) {
				
				String tmp1 = Tools.getFilterString(fieldName1, b);
				value1 = Tools.setValueString(fieldName1, value1, b);
				String tmp2 = Tools.getFilterString(fieldName2, b);
				value2 = Tools.setValueString(fieldName2, value2, b);
				
				if(tmp1.toLowerCase().equals(value1.toLowerCase()) | tmp2.toLowerCase().equals(value2.toLowerCase()))
					budgetsFiltered.add(b);
			}
		} break;
		case "not":{
			for(Budget b : budgets) {
				
				String tmp1 = Tools.getFilterString(fieldName1, b);
				value1 = Tools.setValueString(fieldName1, value1, b);
				
				if(!tmp1.toLowerCase().equals(value1.toLowerCase()))
					budgetsFiltered.add(b);
			}
		} break;
		case "in":{
			ArrayList<String> values = new ArrayList<String>();
			boolean add;
			
			// si vanno a prendere i valori passati come input e li si salvano in un arrayList di stringhe
			try (Scanner rowScanner = new Scanner(value1)) {
				rowScanner.useDelimiter(",");
				while(rowScanner.hasNext()) {
					values.add(rowScanner.next().toLowerCase());
				}
				rowScanner.close();
			}
			
			// si controlla elemento per elemento se il campo da confrontare sia uguale a uno dei valori passati come input
			for(Budget b : budgets) {
				add = false;
				
				for(String tmp1 : values) {
					tmp1 = tmp1.toLowerCase();
					String tmp2 = Tools.getFilterString(fieldName1, b);
					value1 = Tools.setValueString(fieldName1, value1, b);
					
					if(tmp2.toLowerCase().equals(tmp1))
						add = true;
				}
				if(add) {
					budgetsFiltered.add(b);
				}
			}
		} break;
		case "nin":{
			ArrayList<String> values = new ArrayList<String>();
			boolean add;
			
			try (Scanner rowScanner = new Scanner(value1)) {
				rowScanner.useDelimiter(",");
				while(rowScanner.hasNext()) {
					values.add(rowScanner.next().toLowerCase());
				}
				rowScanner.close();
			}
			
			for(Budget b : budgets) {
				add = true;
				
				for(String tmp1 : values) {
					tmp1 = tmp1.toLowerCase();
					String tmp2 = Tools.getFilterString(fieldName1, b);
					value1 = Tools.setValueString(fieldName1, value1, b);
					if(tmp2.toLowerCase().equals(tmp1))
						add = false;
				}
				if(add) {
					budgetsFiltered.add(b);
				}
			}
		} break;
		default: throw new Exception();
		}
		
		return budgetsFiltered;
	}

	
	
}
