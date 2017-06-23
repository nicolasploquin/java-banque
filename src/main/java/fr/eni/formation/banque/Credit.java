package fr.eni.formation.banque;

import java.util.Date;

/**
 * @author NPloquin
 *
 */
public class Credit extends Operation {
	
	private static final long serialVersionUID = 6183974520202681128L;

	public Credit(){
		super();
	}
	
	public Credit(Date date, String libelle, double montant) throws BanqueException{
		super(TypeOperation.CREDIT, date, libelle, montant);
	}

	
	
	
	
	
	public Credit(String date, String libelle, double montant) throws BanqueException{
		super(TypeOperation.CREDIT, date, libelle, montant);
	}


	/**
	 * @see fr.eni.formation.banque.Operation#getMontantSigne()
	 * @deprecated Use {@link #getMontantRelatif()} instead
	 */
	@Override
	public double getMontantSigne() {
		return getMontantRelatif();
	}

	/**
	 * @see fr.eni.formation.banque.Operation#getMontantRelatif()
	 */
	@Override
	public double getMontantRelatif() {
		return montant;
	}
	@Override
	public String toString() {
		return "[Crédit] " + super.toString();
	}

}
