/**
 * 
 */
package fr.eni.formation.banque;

/**
 * @author NPloquin
 *
 */
public class DecouvertException extends Exception {
	private static final long serialVersionUID = 1L;
	public double solde;
	
	public DecouvertException(String message, double solde){
		super(message);
		this.solde = solde;
	}

}
