/**
 * package test de banque Console
 */
package fr.eni.formation.banque.app;

import fr.eni.formation.banque.Banque;
import fr.eni.formation.banque.Client;
import fr.eni.formation.banque.Compte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static fr.eni.formation.banque.Operation.Type.CREDIT;
import static fr.eni.formation.banque.Operation.Type.DEBIT;

/**
 * @author NPloquin
 *
 */
public class Console {
	
	private static BufferedReader in;
	private static Banque banque;
	
	static {
		System.setProperty("file.encoding", "UTF-8");
		in = new BufferedReader(new InputStreamReader(System.in));
		banque = new Banque();
	}

	/**
	 * @param args rien
	 */
	public static void main(String[] args) throws IOException {
		
		menu();
		
	}
	
	
	private static void menu() throws IOException {
		/*
		 * 1 - Initialiser la banque
		 * 2 - Afficher les clients
		 * 2 - Ajouter un client
		 * 3 - Ajouter un compte
		 * 4 - Ajouter une opération
		 * 5 - Effectuer un virement
		 * 0 - Quitter
		 * 
		 */
		System.out.println("---------- MENU -----------");
		System.out.println("1 - Initialiser la banque");
		System.out.println("2 - Afficher la banque");
		System.out.println("3 - Créer nouveau client");
		System.out.println("4 - Afficher client");
		System.out.println("5 - Créer nouveau compte");
		System.out.println("6 - Calculer les intérêts");
		System.out.println("0 - Quitter");
		
		String action = in.readLine();
		switch(action){
		case "0" :
			System.out.println("Fin");
			return;
		case "1" :
			banque = Console.createBanque();
			menu();
			break;
		case "2" :
			System.out.println(banque);
			menu();
		case "3" :
			creerClient();
			menu();
		case "4" :
			Client client = selectionnerClient();
			if(client != null)
				System.out.println(client);
			else
				System.out.println("Client non trouvé.");
			menu();
		case "5" :
			client = selectionnerClient();
			creerCompte(client);
			menu();
		case "6" :
			for (Compte compte : banque.getComptes()) {
				compte.calculerInterets(2014, true);
			}
			menu();
		}
	}


	private static Compte creerCompte(Client client) throws IOException {
		System.out.print("Numéro du compte : ");
		String numero = in.readLine();
		
		return banque.addCompte(client, numero);
	}


	private static Client selectionnerClient() throws IOException {
		System.out.print("Rechercher : ");
		String recherche = in.readLine();
		List<Client> clients = banque.rechercherClient(recherche);
		if(clients.size() == 1){
			return clients.get(0);
		} else if(clients.size() > 0){
			for (int i = 0; i < clients.size(); i++) {
				System.out.println(i + " - " + clients.get(i).getPrenom() + " " + clients.get(i).getNom());
			}
			System.out.print("Client : ");
			int selection = Integer.parseInt(in.readLine());
			return clients.get(selection);
		}
		return null;
	}


	private static Client creerClient() throws IOException {
		System.out.print("Nom du client : ");
		String nom = in.readLine();
		System.out.print("Prénom du client : ");
		String prenom = in.readLine();
		
		return banque.addClient(nom, prenom);
	}

	public static Banque createBanque() {
//		Banque banque = new Banque();
		
		Client client1 = banque.addClient("Martin", "Jean");
		Client client2 = banque.addClient("Durand", "Sophie");
		Client client3 = banque.addClient("Leblanc", "Marc");
		
		Compte compte1 = banque.addLivret(client1, 3.0, "00000001");
		banque.addCompte(client2, "00000003");
		banque.addCompte(client2);
		Compte compte4 = banque.addCompte(client2);
		Compte compte5 = banque.addCompte(client3);
		Compte compte6 = banque.addCompte(client3);
		
//		compte3.setClient(null);

		compte1.addOperation(CREDIT, "03/05/2011", "Salaire Renault", 1300.0);
		compte1.addOperation(DEBIT, "01/05/2011", "Loyer", 420.0);
		compte1.addOperation(DEBIT, "12-05/2011", "EDF", 68.0);
		compte1.addOperation(DEBIT, "20/05/2011", "SFR", 58.0);
		compte1.addOperation(DEBIT, "05/05/2011", "TAN Mai", 49.0);
		compte4.addOperation(CREDIT, "01/01/2008", "Salaire", -500.0);
		compte5.addOperation(CREDIT, "01/01/2008", "Salaire", 1500.0);
		compte6.addOperation(CREDIT, "01/01/2008", "Salaire", 50000.0);
			
		compte4.virement(compte1, 300.0, "Virement");
		
		return banque;
	}


}
