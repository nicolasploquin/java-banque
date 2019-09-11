package fr.eni.formation.banque;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static fr.eni.formation.banque.Banque.DATE_FORMAT;
import static fr.eni.formation.banque.Banque.log;
import static fr.eni.formation.banque.Operation.Type.CREDIT;
import static fr.eni.formation.banque.Operation.Type.DEBIT;

/**
 * @author NPloquin
 * 
 */
public class Compte implements Serializable, Cloneable, Comparable<Compte> {

	private static final long serialVersionUID = -6768523482580774672L;

	public static final java.util.Comparator<Compte> SOLDE_COMPARATOR = new java.util.Comparator<Compte>() {
		@Override
		public int compare(Compte o1, Compte o2) {
			return Double.compare(o1.getSolde(), o2.getSolde());
		}
	};

	private static long numerotation = 0L;

	private String numero;

	private Banque banque;
	/** Titulaire du compte */
	private Client client;
	// private List<Operation> operations = new ArrayList<Operation>();
	private Set<Operation> operations = new TreeSet<Operation>();

	private transient boolean majSolde = true;
	private transient double solde = 0.0;

	public Compte() {
		super();
		creerNumero();
	}

	public Compte(String numero, Client... client) {
		super();
		setNumero(numero);
		if (client.length > 0)
			setClient(client[0]);
	}

	/**
	 * @param operations
	 *            the operations to set
	 */
	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	/**
	 * @param solde
	 *            the solde to set
	 */
	public void setSolde(double solde) {
		this.solde = solde;
	}

	/**
	 * @return the banque
	 */
	public Banque getBanque() {
		return banque;
	}

	/**
	 * @param banque
	 *            the banque to set
	 */
	public void setBanque(Banque banque) {
		this.banque = banque;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Définit le nouveau titulaire du compte et enregistre le compte dans les
	 * compte de ce nouveau titulaire
	 * 
	 * @param client
	 *            Nouveau titulaire du compte
	 */
	public void setClient(Client client) {
		if (this.client != client) {

			// Suppression du compte des comptes de l'ancien titulaire
			if (this.client != null)
				this.client.getComptes().remove(this);

			// Enregistrement du nouveau titulaire
			this.client = client;

			// Ajout du compte aux comptes du nouveau titulaire
			if (client != null)
				client.getComptes().add(this);
		}
	}

	/**
	 * @return the operations
	 */
	public Set<Operation> getOperations() {
		return operations;
	}

	/**
	 * @return the solde
	 */
	public double getSolde() {
		if(majSolde) majSolde();
		return solde;
	}

	public void addOperation(Operation operation) {
		majSolde = operations.add(operation);
	}

	public Operation addOperation(String date, String libelle, double montant) {
        return addOperation(montant < 0.0 ? DEBIT : CREDIT, date, libelle, Math.abs(montant));
	}

	public Operation addOperation(Operation.Type type, String date, String libelle, double montant) {

		Operation operation = null;
		try {
			Date dateObj = DATE_FORMAT.parse(date);
			operation = addOperation(type, dateObj, libelle, montant);
		} catch (ParseException e) {
			System.err.println("L'opération n'a pas pu être créée - Date Erronée : "
							+ e.getMessage());
		} catch (MontantInvalideException e) {
			System.err.println("L'opération n'a pas pu être créée - Montant invalide : "
					+ e.getMessage());
		}
		return operation;
	}

	public Operation addOperation(Operation.Type type, Date date, String libelle, double montant) throws MontantInvalideException {

		Operation operation = null;
		if(montant < 0){
			throw new MontantInvalideException("Le montant de l'opération ne doit pas être négatif.");
		}
		try {
			switch (type) {
			case CREDIT:
				operation = new Credit(date, libelle, montant);
				break;
			case DEBIT:
				operation = new Debit(date, libelle, montant);
			}

			addOperation(operation);
		} catch (BanqueException e) {
			log.error("L'opération n'a pas pu être créée : " + e.getMessage());
			e.printStackTrace(System.err);
		} finally {
			// System.err.println("clause finally montant invalide");
		}
		return operation;
	}

	public void removeOperation(Operation operation) {
		majSolde = operations.remove(operation);
	}

	protected String creerNumero() {
		setNumero(Long.toString(numerotation++));
		return getNumero();
	}

	// protected String createNumero() {
	// String num;
	// do {
	// num = NUMERO_FORMAT.format(numerotation++);
	// } while (banque.getNumeros().contains(num));
	// numero = num;
	// return num;
	// }

	private void majSolde() {
		double nouveauSolde = operations.stream()
				.mapToDouble(ope -> ope.getMontantRelatif())
				.sum();
		setSolde(nouveauSolde);
	}

	public void virement(Compte destinataire, double montant, String libelle) {
		
		try {
			this.addOperation(Operation.Type.DEBIT, new Date(), libelle, montant);
			destinataire.addOperation(Operation.Type.CREDIT, new Date(), libelle,
					montant);
		} catch (MontantInvalideException e) {
			System.err.println("L'opération n'a pas pu être créée - Montant invalide : "
					+ e.getMessage());
		}
	}

	/**
	 * 
	 * @param destinataire
	 *            Numero du compte de destination du virement
	 * @param montant
	 * @param libelle
	 */
	public void virement(String destinataire, double montant, String libelle) {
		Compte compte = client.getCompte(destinataire);
		if (compte != null)
			virement(compte, montant, libelle);
	}

	@Override
    public String toString() {
        String result = String.format("%8s : %15s - %s\n",
                numero,
                Banque.EURO_FORMAT.format(getSolde()),
                client==null ? "" : client.getNom()
        );

        return result;
    }
    public void afficherDetails() {
        String result = toString();

        // Collections.sort(operations, Operation.COMPARATOR);
        // Collections.sort(operations);
        for (Operation ope : operations) {
            result += ope.toString();
        }

        System.out.println(result);
    }

	/**
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	/**
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compte other = (Compte) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	public double calculerInterets(int annee, boolean crediter) {
		return 0.0;
	}

	public int compareTo(Compte o1) {
		return Double.compare(this.getSolde(), o1.getSolde());
	}

	public static class Comparator implements java.util.Comparator<Compte> {

		@Override
		public int compare(Compte o1, Compte o2) {
			// TODO Auto-generated method stub
			return Double.compare(o1.getSolde(), o2.getSolde());
		}

	}

}
