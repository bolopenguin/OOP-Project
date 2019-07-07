package europeBanks.spring.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * La classe che contiene i metodi che permettono di fare il download del file voluto e salvarlo sul computer.
 * Inoltre sono presenti i metodi per effettuare il parsing del file scaricato in modo da poter individuare il file csv.
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

public class ParserJSON {
	// nome con cui viene salvato il file CSV
	private String nameCSV; 
	// nome con cui viene salvato il file JSON
	private String nameJSON;
	
	/**
	 * Metodo che setta i nomi dei file CSV JSON
	 * @param nameJSON
	 * @param nameCSV
	 */
	public ParserJSON(String nameJSON, String nameCSV){
		this.nameCSV = nameCSV; 
		this.nameJSON = nameJSON ; 
	}
	
	/** 
	 * Getter del nome del Csv
	 * @return
	 */
	public String getNameCSV() {
		return nameCSV;
	}

	/**
	 * Getter del nome del JSON
	 * @return
	 */
	public String getNameJSON() {
		return nameJSON;
	}
	
	/**
	 * Setter del nome del CSV
	 * @param nameCSV
	 */
	public void setNameCSV(String nameCSV) {
		this.nameCSV = nameCSV;
	}

	/**
	 * Setter del nome del JSON
	 * @param nameJSON
	 */
	public void setNameJSON(String nameJSON) {
		this.nameJSON = nameJSON;
	}
	
	/**
	 * Metodo che effettua il download del file JSON
	 * @param urlStr e' l'url del file json.
	 * @param fileName e' il nome con il quale verra' salvato il file scaricato.
	 */
	public void downloadJSON(String urlStr, String fileName) {
		try {
			FileWriter fW =new FileWriter(fileName);
			BufferedWriter bW =new BufferedWriter (fW);
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

			BufferedReader read = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			FileOutputStream fis = new FileOutputStream(fileName);
			String line = read.readLine();
			while(line!=null) {
				bW.write(line);
				line = read.readLine();
				bW.flush();
				bW.close();
			}
			fis.close();
			read.close();
			
		} catch(MalformedURLException ex) {
			ex.printStackTrace();
		} catch(IOException ioex) {
			ioex.printStackTrace();
		}

	}
	
	/**
	 * Metodo che effettua il download del file CSV
	 * @param urlStr url del file CSV 
	 * @param fileName nome del file CSV
	 * @throws IOException
	 * @throws FileSystemException
	 */
	public void downloadCSV(String urlStr, String fileName) throws IOException, FileSystemException{
		File file = new File(fileName);
		//se un file con quel nome già esiste non fa niente
		if (!file.exists())
		{
			//altrimenti copiamo l'inputstream ottenuto dall'url nel percorso specificato da filename
			try (InputStream in = URI.create(urlStr).toURL().openStream()) 
			{
				Files.copy(in, Paths.get(fileName));
			}
		}
	}

	/**
	 * Metodo che effettua il parsing del Json andando a individuare il csv. 
	 * @param nomeJSON  Nome che identifica il file json.
	 * @return urlAddress  Url che identifica dove si trova il csv.
	 * @throws FileNotFoundException Eccezione nel caso in cui non riesca a trovare il file json.
	 */
	public String getURL (String nomeJSON)throws FileNotFoundException {
			BufferedReader reader = new BufferedReader(new FileReader(nomeJSON));
			String json = "";
			try {
				String line = reader.readLine();
				while(line!=null) {
					json += line;
					line = reader.readLine();
					reader.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String urlAddress = "";
			JSONObject obj = new JSONObject(json);


		JSONObject objI = (JSONObject) (obj.get("result"));
		JSONArray objA = (JSONArray) (objI.get("resources"));

		for (Object o: objA) {

			if ( o instanceof JSONObject ) {
				JSONObject o1 = (JSONObject)o; 
				String format = (String)o1.get("format");
				String urlD = (String)o1.get("url");
				if(format.equals("http://publications.europa.eu/resource/authority/file-type/CSV")) {
					urlAddress = urlD; 
					return urlAddress;
				}

			}

		}return urlAddress;
	}





	

}
