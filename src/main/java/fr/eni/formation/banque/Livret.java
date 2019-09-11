package fr.eni.formation.banque;

import java.util.Date;


public class Livret extends Compte {
	private static final long serialVersionUID = 3011594500244906308L;
	
	double taux = 0.0;

	public Livret() {
	}

	public Livret(String numero,double taux, Client... client) {
		super(numero, client);
	}

	/**
	 * @return the taux
	 */
	public double getTaux() {
		return taux;
	}

	/**
	 * @param taux the taux to set
	 */
	public void setTaux(double taux) {
		this.taux = taux;
	}
	
	@Override
	public double calculerInterets(int annee, boolean crediter) {
		double interets = getSolde() * (taux/100.0);
		try {
			if(crediter && interets > 0.0) addOperation(Operation.Type.CREDIT, new Date(), "Intérêt " + annee, interets);
		} catch (MontantInvalideException e) {} // Le montant ne sera jamais invalide
		return interets;
	}


}
