package fr.eni.formation.banque;

/**
 * @author NPloquin
 *
 */
public class MontantInvalideException extends Exception {
	
	private static final long serialVersionUID = 3237964690532512236L;

	private double montant;

	/**
	 * 
	 */
	public MontantInvalideException() {
		super();
	}

	/**
	 * @param message Montant invalide
	 */
	public MontantInvalideException(String message) {
		super(message);
	}

	public double getMontant() {
		return montant;
	}
	
	public void setMontant(double montant) {
		this.montant = montant;
	}

}
