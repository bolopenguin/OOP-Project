package europeBanks.spring.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import europeBanks.spring.model.*;

/**
 * Classe che contiene i metodi per effettuare il parsing del CSV
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

public class ParserCSV {
	// Delimitatore su cui fare il parsing
	final static String COMMA_DELIMITER = ",";
	// Array List che contiene gli oggetti del nostro caso di caso
	private static ArrayList<Budget> budgets;

	/**
	 * Getter del bugets
	 * @return
	 */
	public static ArrayList<Budget> getBudgets() {
		return budgets;
	}

	/**
	 * setter del budgets
	 * @param budgets
	 */
	public static void setBudgets(ArrayList<Budget> budgets) {
		ParserCSV.budgets = budgets;
	}
	
	/**
	 * Dopo aver preso in ingresso il nome del file csv, salva riga per riga il contenuto in una array list di righe
	 * @param file Nome del file CSV
	 * @return records Array list contenente tutte le righe
	 * @throws FileNotFoundException Eccezione nel caso di non esistenza del CSV
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
		System.out.println("Numero di righe: "+i);
		return records;
	}
	
	/**
	 * Metodo che scandisce le righe e separa le diverse parti di ogni riga (secondo il COMMA_DELIMITER) salvandole in un array list di stringhe
	 * @param line Singola riga del file CSV
	 * @return values Valori  di tipo ArrayList che verranno poi aggiunti nel record @see readFile
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
	 * Metodo che prende in ingresso l'array list che contiene array i cui elementi sono stringhe che identificano le diverse proprieta' della sigola riga.
	 * Il metodo restituisce un array list, non piu' di array di stringhe, ma un array list di oggetti Budget.
	 * Infine qui vengono fatti i controlli per verificare che la riga sia consistente prima di salvarla come un oggetto Budget.
	 * @param records
	 */
	public static void parserCSV( List<List<String>> records) {
		List<String> line;
		String tmp; 
		
		for(int i = 1; i< records.size() ; i++) {			
			line = records.get(i);
			/* 
			 * Questo controllo serve perché il campo Label potrebbe contenere delle virgole, e quindi 
			 * quando si fa la separazione dei campi si potrebbero avere più di 7 elementi
			 * quindi in questo caso si prendono gli elementi che sono stati separati nel parsing e rimessi insieme
			 * per poter poi creare l'oggetto budget in maniera corretta.
			 * Gli altri campi invece non contengono virgole quindi questo processo si riferisce solo al campo Label
			 */
			if(line.size() > 7) { 
				tmp = line.get(4);
				for(int j = 5 ; j < line.size() - 2; j++) {
					tmp = tmp + "," + line.get(j);
				}
				Budget b = new Budget(
						line.get(0).isEmpty() ? null : line.get(0),
						line.get(1).isEmpty() ? null : line.get(1),
						line.get(2).isEmpty() ? 0 : Integer.parseInt(line.get(2)),
						line.get(3).isEmpty() ? 0 : Integer.parseInt(line.get(3)),
						line.get(4).isEmpty() ? null : tmp,
						line.get(5).isEmpty() ? Double.NaN : Double.parseDouble(line.get(line.size()-2)),
						line.get(6).isEmpty() ? 0 : Integer.parseInt(line.get(line.size()-1))
						);
				budgets.add(b);
				
			}else {
				Budget b = new Budget(
						line.get(0).isEmpty() ? "LeiCodeNotFound" : line.get(0),
						line.get(1).isEmpty() ? "NsaCodeNotFound" : line.get(1),
						line.get(2).isEmpty() ? 0 : Integer.parseInt(line.get(2)),
						line.get(3).isEmpty() ? 0 : Integer.parseInt(line.get(3)),
						line.get(4).isEmpty() ? "LabelNotFound" : line.get(4),
						line.get(5).isEmpty() ? Double.NaN : Double.parseDouble(line.get(5)),
						line.get(6).isEmpty() ? 0 : Integer.parseInt(line.get(6))
						);
				budgets.add(b);
			}
		}
	}	
}


