package europeBanks.spring.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import europeBanks.spring.model.*;

/**
 * Classe che contiene i metodi per effettuare il parsing del csv
 * @author Utente
 *
 */

public class ParserCSV {
	final static String COMMA_DELIMITER = ",";
	private static ArrayList <Budget> Budget;

	public static ArrayList<Budget> getBudget() {
		return Budget;
	}

	public static void setBudget(ArrayList<Budget> Budget) {
		ParserCSV.Budget = Budget;
	}
	
	/**
	 * prende in ingresso il file csv e salva riga per riga il contenuto in una array list di righe
	 * @param file nome del file csv
	 * @return records array list che contiene tutte le righe
	 * @throws FileNotFoundException eccezione nel caso non esista il csv
	 */

	
	public static List<List<String>> readFile(String file) throws FileNotFoundException {
		List<List<String>> records = new ArrayList<>();
		int i = 0;
		try (Scanner scanner = new Scanner(new File(file))) {
			while (scanner.hasNextLine()) {
				records.add(getRecordFromLine(scanner.nextLine()));
				i++;
			}
		}
		System.out.println("number of rows : "+i);
		return records;
	}
	/**
	 * metodo per scandire le righe e separare le diverse parti di ogni riga salvandole in un array list di stringhe
	 * @param line, singola riga del file csv
	 * @return values, valori  di tipo ArrayList che verranno poi aggiunti nel record @see readFile
	 */
	
	private static List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(COMMA_DELIMITER);
			while(rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
			rowScanner.close();
		}
		return values;
	}
	/**
	 * metodo che prende in ingresso l'array list che contiene array i cui elementi sono stringhe che identificano
	 * le diverse componenti della sigola riga.
	 * il metodo restituisce un array list non più di array di stringhe ma un array list di oggetti Budget
	 * inoltre qui vengono fatti i controlli per verificare che la riga sia consistente prima di salvarla come un oggetto Budget
	 * @param records
	 */
	public static void parserCSV( List<List<String>> records) {
		ArrayList <Budget> Budget = new ArrayList <Budget>();
		List<String> line;
		
		int ri = 0; 
		int rs = 0;	
		
        String lei_code;
		String nsa;
		int period;
		int item;
		String label;
		double amount;
		int n_quarters;
		
		for(int i = 1; i< records.size() ; i++) {
			line = records.get(i);
			if(line.size() < 7) {
				ri++;
			}else {
				
				if(line.get(0).length() != 20) {
					rs++;
					continue;
				}
				lei_code = line.get(0);
				
				if(line.get(1).length() != 2) {
					rs++;
					continue;
				}
				nsa = line.get(1);
				
				if(line.get(2).length() != 6) {
					rs++;
					continue;
				}
				period = Integer.parseInt(line.get(2));
				
				if(line.get(3).length() != 7) {
					rs++;
					continue;
				}
				item = Integer.parseInt(line.get(3));
				
				label = line.get(4);
				
				amount = Double.valueOf(line.get(5));
				
				if(line.get(6).length() != 1) {
					rs++;
					continue;
				}
				n_quarters = Integer.parseInt(line.get(5));
				
				
				Budget b = new Budget(lei_code, nsa, period, item, label, amount, n_quarters);
				Budget.add(b);
				}
			}
		setBudget(Budget);
		System.out.println("Riga insufficiente : "+ri);
		System.out.println("Riga errata : "+rs);
	}	
}


