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
	
	/**
	 * blocco che parte all'avvio, serve per andare a riempire gli array list
	 * cioè fa partire il download e il parsing dei dati
	 */

	static {
		try {
			budgets = fill();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<Budget> fill() throws IOException {
		ArrayList<Budget> BudgetB = new ArrayList<Budget>();
		String nameCSV = "Budget.csv";
		String nameJSON = "JsonFile.json";

		ParserJSON parse = new ParserJSON (nameJSON, nameCSV);
		String url_json = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-wide-transparency-exercise-results-templates-2016";

		parse.downloadJSON(url_json,parse.getNameJSON());
		System.out.println("DONE");
		//il problema è qua, non scarica il csv
		parse.downloadCSV(parse.getURL(nameJSON), parse.getNameCSV());
		List<List<String>> records = ParserCSV.readFile(parse.getNameCSV());


		ParserCSV.parserCSV(records);
		ArrayList<Budget> Budget = ParserCSV.getBudget();

		System.out.println("Rows accepted : " + Budget.size());
		for(int i = 0; i < Budget.size(); i++) {
			BudgetB.add(new Budget(Budget.get(i)));
		} 

		BudgetService.setMetadata(new ArrayList<Metadata>());

		return BudgetB;
	}
	
	private static void setMetadata(ArrayList<Metadata> arrayList) {
		metadata.add(new Metadata("lei_code","Legal Entity Identifier Code","String"));
		metadata.add(new Metadata("nsa","Country","String"));
		metadata.add(new Metadata("period","Periodo di Riferimento","Integer"));
		metadata.add(new Metadata("item","Budget Item Code","Integer"));
		metadata.add(new Metadata("label","Label of the Budget Item","String"));
		metadata.add(new Metadata("amount","Budget Item Amount","Double"));
		metadata.add(new Metadata("n_quarters","Trimestre di Riferimento","Integer"));
		}
	
	//implementazione delle operazioni
	
	


	@Override
	public ArrayList<Budget> getBudget() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Metadata> getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double sumBudget(String property, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double maxBudget(String property, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double minBudget(String property, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double avgBudget(String property, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double devstdBudget(String property, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countBank(String property, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Budget> getBudgetByProperty(String property, String value) {
		// TODO Auto-generated method stub
		return null;
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
