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
	private static ArrayList <Budget> budgets;

	public static ArrayList<Budget> getBudgets() {
		return budgets;
	}

	public static void setBudgets(ArrayList<Budget> budgets) {
		ParserCSV.budgets = budgets;
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
	 * il metodo restituisce un array list non piÃ¹ di array di stringhe ma un array list di oggetti Budget
	 * inoltre qui vengono fatti i controlli per verificare che la riga sia consistente prima di salvarla come un oggetto Budget
	 * @param records
	 */
	public static void parserCSV( List<List<String>> records) {
		ArrayList <Budget> template = new ArrayList <Budget>();
		List<String> line;
		String tmp; 
		
		for(int i = 1; i< records.size() ; i++) {			
			line = records.get(i);
			if(line.size() > 7) { 
				tmp = line.get(4);
				for(int j = 5 ; j < line.size() - 2; j++) {
					tmp = tmp + "," + line.get(j);
				}
				Budget b = new Budget(
						line.get(0).isEmpty() ? "LeiCodeNotFound" : line.get(0),
						line.get(1).isEmpty() ? "NsaCodeNotFound" : line.get(1),
						line.get(2).isEmpty() ? 0 : Integer.parseInt(line.get(2)),
						line.get(3).isEmpty() ? 0 : Integer.parseInt(line.get(3)),
						line.get(4).isEmpty() ? "LabelNotFound" : tmp,
						line.get(5).isEmpty() ? 0 : Double.parseDouble(line.get(line.size()-2)),
						line.get(6).isEmpty() ? 0 : Integer.parseInt(line.get(line.size()-1))
						);
				template.add(b);
				
			}else {
				Budget b = new Budget(
						line.get(0).isEmpty() ? "LeiCodeNotFound" : line.get(0),
						line.get(1).isEmpty() ? "NsaCodeNotFound" : line.get(1),
						line.get(2).isEmpty() ? 0 : Integer.parseInt(line.get(2)),
						line.get(3).isEmpty() ? 0 : Integer.parseInt(line.get(3)),
						line.get(4).isEmpty() ? "LabelNotFound" : line.get(4),
						line.get(5).isEmpty() ? 0 : Double.parseDouble(line.get(5)),
						line.get(6).isEmpty() ? 0 : Integer.parseInt(line.get(6))
						);
				template.add(b);
			}
				
		}
		budgets = template;
	}	
}


