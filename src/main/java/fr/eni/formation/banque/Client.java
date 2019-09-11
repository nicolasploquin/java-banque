/**
 * 
 */
package fr.eni.formation.banque;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author NPloquin
 *
 */
public class Client implements Serializable {
	
	private static final long serialVersionUID = 5226166059366060409L;
	
	public static final Comparator<Client> PRENOM_COMPARATOR = new Comparator<Client>() {
		@Override
		public int compare(Client o1, Client o2) {
			return o1.getPrenom().compareTo(o2.getPrenom());
		}
	};
	
	public static final Comparator<Client> NOM_COMPARATOR = new Comparator<Client>() {
		@Override
		public int compare(Client o1, Client o2) {
			return o1.getNom().compareTo(o2.getNom());
		}
	};

	private String nom = "";
	private String prenom = "";
	
	private List<Compte> comptes = new LinkedList<Compte>();
	
	public Client(){
		super();
	}
	
	/**
	 * @param nom
	 * @param prenom
	 */
	public Client(String nom, String prenom, Compte... comptes) {
		super();
		setNom(nom);
		setPrenom(prenom);
		
		for(Compte compte : comptes){
			addCompte(compte);
		}
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom.toUpperCase();
	}

	/**
	 * @return the comptes
	 */
	public List<Compte> getComptes() {
		return comptes;
	}
	public List<Livret> getLivrets() {
		List<Livret> livrets = new LinkedList<Livret>();
		for (Compte compte : comptes) {
			if(compte instanceof Livret) livrets.add((Livret)compte);
		}
		return livrets;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		String[] prenoms = prenom.split("-");
		for (int i = 0; i < prenoms.length; i++) {
			prenoms[i] = prenoms[i].substring(0,1).toUpperCase() 
					+ prenoms[i].substring(1).toLowerCase();
		}
		this.prenom = String.join("-", prenoms);
	}

	/**
	 * @param comptes the comptes to set
	 */
	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
	}

	/**
	 * 
	 * @param compte
	 */
	public void addCompte(Compte compte){
		compte.setClient(this);
	}

	public void removeCompte(Compte compte){
		compte.setClient(null);
	}

	public Compte getCompte(String numero){		
		for(Compte compte : comptes){
			if(compte.getNumero().equals(numero)) return compte;
		}
		return null;
	}

	public String toString(){
		return String.format("%s %s (%d comptes)\n",
				getPrenom(), getNom(), getComptes().size());

	}

	public void afficherDetails(){
		String result = toString();

//		Comparator<Compte> comp = new Comparator<Compte>() {
//			@Override
//			public int compare(Compte o1, Compte o2) {
//				return Double.compare(o1.getSolde(), o2.getSolde());
//			}
//		};

//		Collections.sort(comptes, new Comparator<Compte>() {
//			@Override
//			public int compare(Compte o1, Compte o2) {
//				return Double.compare(o1.getSolde(), o2.getSolde());
//			}
//		});
		Collections.sort(comptes);
		for(Compte compte : comptes){
			result += compte;
		}

		System.out.println(result);
	}

}
