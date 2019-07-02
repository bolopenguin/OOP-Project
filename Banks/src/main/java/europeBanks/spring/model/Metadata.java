package europeBanks.spring.model;

/**
 * classe per descrivere l'insieme dei dati
 * 
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

public class Metadata {
	
	protected String alias;
	protected String realName;
	protected String type;
	
	/**
	 * Costruttore
	 * 
	 * @param alias nome di fantasia per rappresentare un attributo del dataset
	 * @param realName nome reale dell'attributo del dataset
	 * @param type tipo dell'attributo del dataset
	 */
	
	public Metadata(String alias, String realName, String type) {
		super();
		this.alias = alias;
		this.realName = realName;
		this.type = type;
	}

	/**
	 * getter dell'Alias
	 * @return
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * getter del real Name
	 * @return
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * getter del type
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * setter dell'alias
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * setter del real Name
	 * @param realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * setter del type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
