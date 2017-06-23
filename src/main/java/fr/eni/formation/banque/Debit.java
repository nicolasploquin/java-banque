package fr.eni.formation.banque;

import java.util.Date;

/**
 * @author NPloquin
 *
 */
public class Debit extends Operation {

	private static final long serialVersionUID = -8545744818060505837L;

	public Debit(){
		super();
	}

	public Debit(Date date, String libelle, double montant) throws BanqueException{
		super(TypeOperation.DEBIT, date, libelle, montant);
	}
	
	public Debit(String date, String libelle, double montant) throws BanqueException {
		super(TypeOperation.DEBIT, date, libelle, montant);
	}
	
	/**
	 * @see fr.eni.formation.banque.Operation#getMontantSigne()
	 * @deprecated Use {@link #getMontantRelatif()} instead
	 */
	@Override
	@Deprecated
	public double getMontantSigne() {
		return getMontantRelatif();
	}

	/**
	 * @see fr.eni.formation.banque.Operation#getMontantRelatif()
	 */
	@Override
	public double getMontantRelatif() {
		return -montant;
	}
	@Override
	public String toString() {
		return "[Débit]  " + super.toString();
	}


}
