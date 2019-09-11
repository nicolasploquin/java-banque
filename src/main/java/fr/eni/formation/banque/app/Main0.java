package fr.eni.formation.banque.app;

import fr.eni.formation.banque.*;

import java.io.IOException;

import static fr.eni.formation.banque.TypeOperation.CREDIT;
import static fr.eni.formation.banque.TypeOperation.DEBIT;

/**
 * @author NPloquin
 *
 */
public class Main0 {
	
	/**
	 * @param args Pas de paramètres attendus
	 */
	public static void main(String[] args) throws Exception {

		Client client1 = new Client("Martin", "jeAn-marc");
		Client client2 = new Client("Durand", "soPhie");
		Client client3 = new Client("Leblanc", "Marc");
		
		Compte compte1 = new Livret("00000001", 3.0, client1);
		Compte compte2 = new Compte("00000003");
        client1.addCompte(compte1);
        client2.addCompte(compte2);

		Credit credit1 = new Credit("2018-02-03", "Salaire Renault", 1300.0);
		compte1.getOperations().add(credit1);

		Credit credit2 = new Credit("03/05/2011", "Salaire Renault", 1300.0);
		compte1.getOperations().add(credit2);

		compte1.addOperation("01/05/2011", "E.Leclerc", -35.0);
		compte1.addOperation(new Debit("01/05/2011", "Loyer", 420.0));
		compte1.addOperation(new Debit("12/05/2011", "EDF", 68.0));
		compte1.addOperation(new Debit("20/05/2011", "SFR", 58.0));
		compte1.addOperation(new Debit("05/05/2011", "TAN Mai", 49.0));
        compte2.addOperation(new Credit("01/01/2008", "Salaire", 500.0));

        compte2.virement(compte1, 300.0, "Virement");
		
		client1.afficherDetails();
		client2.afficherDetails();
		client3.afficherDetails();

		compte1.afficherDetails();
		compte2.afficherDetails();
	}

}
