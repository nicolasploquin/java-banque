package fr.eni.formation.banque.app;

import fr.eni.formation.banque.*;

import java.io.IOException;

import static fr.eni.formation.banque.TypeOperation.CREDIT;
import static fr.eni.formation.banque.TypeOperation.DEBIT;

/**
 * @author NPloquin
 *
 */
public class Main {
	
	private static Banque banque = new Banque();
	
	static {
		System.setProperty("file.encoding", "UTF-8");
		banque = createBanque();
	}

	/**
	 * @param args Pas de paramètres attendus
	 */
	public static void main(String[] args) throws IOException {
		
		System.out.println(banque);
		
	}
	
	

	private static Banque createBanque() {
//		Banque banque = new Banque();
		
		Client client1 = banque.addClient("Martin", "jeAn-marc");
		Client client2 = banque.addClient("Durand", "soPhie");
		Client client3 = banque.addClient("Leblanc", "Marc");
		
		Compte compte1 = banque.addLivret(client1, 3.0, "00000001");
		banque.addCompte(client2, "00000003");
		banque.addCompte(client2);
		Compte compte4 = banque.addCompte(client2);
		Compte compte5 = banque.addCompte(client3);
		Compte compte6 = banque.addCompte(client3);
		
//		new Client("troadec", "nolwenn", compte4, compte5);	
				
		
//		compte3.setClient(null);

		try {
			Credit credit1 = new Credit("03-05/2011", "Salaire Renault", 1300.0);
			compte1.getOperations().add(credit1);
		} catch (BanqueException e) {
			System.err.println("impossible de créer l'opération...");
		}
		try {
			Credit credit2 = new Credit("03/05/2011", "Salaire Renault", 1300.0);
			compte1.getOperations().add(credit2);
		} catch (BanqueException e) {
			System.err.println("impossible de créer l'opération...");
		}
		
		
		compte1.addOperation(CREDIT, "03/05/2011", "Salaire Renault", 1300.0);
		compte1.addOperation(DEBIT, "01/05/2011", "Loyer", 420.0);
		compte1.addOperation(DEBIT, "12-05/2011", "EDF", 68.0);
		compte1.addOperation(DEBIT, "20/05/2011", "SFR", 58.0);
		compte1.addOperation(DEBIT, "05/05/2011", "TAN Mai", 49.0);
		compte4.addOperation(CREDIT, "01/01/2008", "Salaire", -500.0);
		compte5.addOperation(CREDIT, "01/01/2008", "Salaire", 1500.0);
		compte6.addOperation(CREDIT, "01/01/2008", "Salaire", 50000.0);
		
		try {
			new Credit("12-05/2011", "EDF", 68.0);
		} catch (BanqueException e) {
//			e.printStackTrace();
		}
		
			
		compte4.virement(compte1, 300.0, "Virement");
		
		return banque;
	}


}
