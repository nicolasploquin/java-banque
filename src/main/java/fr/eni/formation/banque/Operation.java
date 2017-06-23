	/**
 * 
 */
package fr.eni.formation.banque;

    import java.io.Serializable;
    import java.text.ParseException;
    import java.util.Comparator;
    import java.util.Date;
    import java.util.GregorianCalendar;

    import static fr.eni.formation.banque.Banque.DATE_FORMAT;
    import static fr.eni.formation.banque.Banque.EURO_FORMAT;

/**
 * @author NPloquin
 *
 */
public abstract class Operation implements Serializable, Comparable<Operation> {
	private static final long serialVersionUID = -3090580097072024643L;

	/** Type Crédit */ 
	public static final int CREDIT = 1;
	public static final int DEBIT = 2;
		
	public static final Comparator<Operation> COMPARATOR = new Comparator<Operation>(){
		@Override
		public int compare(Operation o1, Operation o2) {
			return - o1.getDate().compareTo(o2.getDate());
		}
	};

	@Override
	public int compareTo(Operation operation) {
		return -((Double)this.getMontantRelatif()).compareTo(operation.getMontantRelatif());
	}
	
//	@Override
//	public int compareTo(Operation operation) {
//		return -this.getDate().compareTo(operation.getDate());
//	}
//	
	
	protected Date date = null;
	protected String libelle = "";
	protected double montant = 0.0;
	
	protected TypeOperation type;
	
	public Operation(){
		super();
	}

	public Operation(TypeOperation type, Date date, String libelle, double montant) throws BanqueException{
		super();
		setDate(date);
		setLibelle(libelle);
		if(montant >= 0.0){
			setMontant(montant);
		}else{
//			MontantInvalideException e = new MontantInvalideException();
//			e.setMontant(montant);
			throw new BanqueException("Le montant de l'opération doit être supérieur "
					+ "ou égal à 0. Montant indiqué : " + montant);
		}
	}

	public Operation(TypeOperation type, String date, String libelle, double montant) throws BanqueException{
		this(type, (Date)null, libelle, montant);		
		try {
			this.date = DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			throw new BanqueException("La date ne respecte pas le format attendu (" + date + ").", e);
		}
	}

	
	/**
	 * Montant de l'opération, négatif si type Débit, positif si type Crédit.
	 * @deprecated Use {@link #getMontantRelatif()} instead
	 */
	@Deprecated
	public abstract double getMontantSigne();

	/**
	 * Montant de l'opération, négatif si type Débit, positif si type Crédit.
	 */
	public abstract double getMontantRelatif();

	/**
	 * @return the date
	 */
	public Date getDate() {
		
		new GregorianCalendar(2015,8,15).getTime();
		
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the montant
	 */
	public double getMontant() {
		return montant;
	}

	/**
	 * @param montant the montant to set
	 */
	public void setMontant(double montant) {
		this.montant = montant;
	}
	
	/**
	 * @return the type
	 */
	public TypeOperation getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeOperation type) {
		this.type = type;
	}

	@Override
	public String toString() {
//		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
//		return df.format(date) + " : " + libelle + "\t" + EURO_FORMAT.format(getMontantRelatif()) + "\n";
//		return Banque.DATE_FORMAT.format(date) + " : " + libelle + "\t" + EURO_FORMAT.format(getMontantRelatif()) + "\n";
		return String.format("%s : %-20s %12s\n", Banque.DATE_FORMAT.format(date), libelle, EURO_FORMAT.format(getMontantRelatif()));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if((this instanceof Credit && obj instanceof Credit)
		||(this instanceof Debit && obj instanceof Debit)){
			Operation ope = (Operation)obj;
			return 
					ope.date.equals(date) &&
					ope.libelle.equals(libelle) &&
					ope.montant == montant;				
			
		}
		return false;
	}

}
