package europeBanks.spring.model;

/**
 *  Con questa classe rappresentiamo i diversi Budget Items (voci di bilancio) delle diverse banche, cioe' il modello dei dati
 *  
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */
	
public class Budget {
	
	private String lei_code;
	private String nsa;
	private int period;
	private int item;
	private String label;
	private double amount;
	private int n_quarters;
	
	/**
	 * Primo Costruttore
	 * 
	 * @param lei_code codice univoco per indentificare l'attivita' finanziaria (la banca)
	 * @param nsa codice che indica la nazione in cui ha sede la banca
	 * @param period periodo a cui e' stato effettuato il calcolo delle voci
	 * @param item codice per indentificare la voce di bilancio
	 * @param label etichetta associata alla voce di bilancio
	 * @param amount ammontare associato alla voce di bilancio
	 * @param n_quarters trimestre a cui ci si sta riferendo
	 */
	
	public Budget(String lei_code, String nsa, int period, int item, String label, double amount, int n_quarters) {
		super();
		this.lei_code = lei_code;
		this.nsa = nsa;
		this.period = period;
		this.item = item;
		this.label = label;
		this.amount = amount;
		this.n_quarters = n_quarters;
	}
	
	/**	 
	 * Secondo Costruttore 
	 * crea un oggetto copia di quello che gli viene passato come argomento
	 * 
	 * @param i
	 */

	
	public Budget(Budget i) {
		super();
		this.lei_code = i.lei_code;
		this.nsa = i.nsa;
		this.period = i.period;
		this.item = i.item;
		this.label = i.label;
		this.amount = i.amount;
		this.n_quarters = i.n_quarters;
	}

	/**
	 * getter del Lei code
	 * @return
	 */
	public String getLei_code() {
		return lei_code;
	}

	/**
	 * getter dell'Nsa
	 * @return
	 */
	public String getNsa() {
		return nsa;
	}

	/**
	 * getter del Period
	 * @return
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * getter dell'item
	 * @return
	 */
	public double getItem() {
		return item;
	}

	/**
	 * getter del Label
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * getter dell'Amount
	 * @return
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * getter del N quarters
	 * @return
	 */
	public int getN_quarters() {
		return n_quarters;
	}

	/**
	 * setter del Lei Code
	 * @param lei_code
	 */
	public void setLei_code(String lei_code) {
		this.lei_code = lei_code;
	}

	/**
	 * setter dell'Nsa
	 * @param nsa
	 */
	public void setNsa(String nsa) {
		this.nsa = nsa;
	}

	/**
	 * setter del Period
	 * @param period
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * setter dell'item
	 * @param item
	 */
	public void setItem(int item) {
		this.item = item;
	}

	/**
	 * setter del Label
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * setter dell'Amount
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * setter dell'N quarters
	 * @param n_quarters
	 */
	public void setN_quarters(int n_quarters) {
		this.n_quarters = n_quarters;
	}

	/**
	 * metodo che rappresenta sotto forma di stringa l'oggetto con tutti i suoi attributi
	 */
	@Override
	public String toString() {
		return "Budget [lei_code=" + lei_code + ", nsa=" + nsa + ", period=" + period + ", item=" + item
				+ ", label=" + label + ", amount=" + amount + ", n_quarters=" + n_quarters + "]";
	}
	
	
}
