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
 * Questa classe contiene i metodi per fare il download del file voluto dal web e salvarlo sul computer
 * Inoltre sono presenti i metodi per effettuare il parsing del file scaricato per poter individuare il file csv
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

public class ParserJSON {
	/**
	 * variabili che indicano i nomi in cui andremo a salvare i file csv e json
	 */
	private String nameCSV; 
	private String nameJSON;
	
	
	public ParserJSON(String nameJSON, String nameCSV){
		this.nameCSV = nameCSV; 
		this.nameJSON = nameJSON ; 
	}
	
	public String getNameCSV() {
		return nameCSV;
	}

	public String getNameJSON() {
		return nameJSON;
	}
	
	public void setNameCSV(String nameCSV) {
		this.nameCSV = nameCSV;
	}

	public void setNameJSON(String nameJSON) {
		this.nameJSON = nameJSON;
	}
	
	/**
	 * metodo che fa il download del json
	 * @param urlStr è l'url di dove si trova il file json
	 * @param file è il nome con cui dobbiamo salvare il file scaricato
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
	
	//cambiare questa 
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
	 * metodo che fa il parsing del Json andando a individuare il csv 
	 * @param nomeJSON nome dove si trova il file json
	 * @return urlAddress  url dove si trova il csv
	 * @throws FileNotFoundException eccezione nel caso in cui non riesca a trovare il file json
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
				System.out.println(format + " | " + urlD);
				if(format.equals("http://publications.europa.eu/resource/authority/file-type/CSV")) {
					System.out.println("There is a CSV");
					urlAddress = urlD; 
					return urlAddress;

				}
				System.out.println(urlD);
			}

		}return urlAddress;
	}





	

}
