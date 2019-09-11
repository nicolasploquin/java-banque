package fr.eni.formation.banque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.*;
import java.beans.XMLEncoder;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author NPloquin
 *
 */
@SuppressWarnings("unused")
public class Banque implements Serializable {

	private static final long serialVersionUID = -2863688442586520603L;

	public static final NumberFormat EURO_FORMAT = new DecimalFormat("#,##0.00 ¤");
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	public static final NumberFormat NUMERO_FORMAT = new DecimalFormat("00000000");

	public static final Properties BANQUE_SQL = new Properties();
	
	static final Logger log = LogManager.getLogger(Banque.class);
	
	private List<Client> clients = new LinkedList<>();
	private Set<Compte> comptes = new HashSet<Compte>();

	static {	
		
		log.info("Paramétrage : Locale France");
		Locale.setDefault(Locale.FRANCE);
		
		EURO_FORMAT.setGroupingUsed(true);

		try
		{
			BANQUE_SQL.load(new FileReader("resources/sql.properties"));
		}
		catch(Exception e){}
		
	}
	
	
	public Banque(){
		super();
	}

	/**
	 * @return the clients
	 */
	public List<Client> getClients() {
		return clients;
	}

	/**
	 * @param clients the clients to set
	 */
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	/**
	 * @return the comptes
	 */
	public Set<Compte> getComptes() {
		return comptes;
	}

	/**
	 * @param comptes the comptes to set
	 */
	public void setComptes(Set<Compte> comptes) {
		this.comptes = comptes;
	}
	
	public Client addClient(String nom, String prenom) {
		Client client = new Client(nom, prenom);
		clients.add(client);
		return client;
	}
	
	public Client addClient(Client client){
		clients.add(client);
		return client;
	}
	
	public Compte addCompte(Client client, String... numero){
		Compte compte = new Compte();
		compte.setBanque(this);
		if(numero.length > 0){
			compte.setNumero(numero[0]);
		}else{
			compte.creerNumero();			
		}	
		client.addCompte(compte);
		comptes.add(compte);
		return compte;
	}
	public Compte addLivret(Client client, double taux, String... numero){
		Livret compte = new Livret();
		compte.setBanque(this);
		compte.setTaux(taux);
		if(numero.length > 0){
			compte.setNumero(numero[0]);
		}else{
			compte.creerNumero();			
		}	
		client.addCompte(compte);
		comptes.add(compte);
		return compte;
	}
	
	public Set<String> getNumeros(){
		Set<String> numeros = new HashSet<String>();
		for(Compte compte : comptes){
			numeros.add(compte.getNumero());
		}
		return numeros;
	}

	@Override
	public String toString() {

        clients.sort((c1, c2) -> c1.getPrenom().compareTo(c2.getPrenom()));

		
//		Comparator clientComparateur = new ClientComparator();
//		Collections.sort(clients, clientComparateur);
		
		
		String result = "";
		for(Client client : clients){
			result += client + "\n";
		}
		return result;
	}

	public void loadXML(String filename){
		XMLStreamReader reader = null;
		Client client = null;
		Compte compte = null;
		Operation operation = null;
		

		try{
			XMLInputFactory factory = XMLInputFactory.newInstance();

			reader = factory.createXMLStreamReader(new FileReader(filename));
			
			while(reader.hasNext()){
//				System.out.println(reader.getEventType() + " - " + reader.getLocalName());

				if(reader.getEventType() == XMLStreamConstants.START_DOCUMENT){
					
				}else if(reader.getEventType() == XMLStreamConstants.START_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("client")){
					client = new Client();
				}
				else if(reader.getEventType() == XMLStreamConstants.END_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("client")){
					this.addClient(client);
				}
				else if(reader.getEventType() == XMLStreamConstants.START_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("nom")){
					client.setNom(reader.getElementText());
				}
				else if(reader.getEventType() == XMLStreamConstants.START_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("prenom")){
					client.setPrenom(reader.getElementText());
				}
				else if(reader.getEventType() == XMLStreamConstants.START_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("compte")){
					String numero = reader.getAttributeValue("", "numero");
					compte = this.addCompte(client, numero);
				}
				else if(reader.getEventType() == XMLStreamConstants.START_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("operation")){
					if(reader.getAttributeValue(null, "type").equalsIgnoreCase("debit")){
						operation = new Debit();
					}else{
						operation = new Credit();
					}
				}
				else if(reader.getEventType() == XMLStreamConstants.END_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("operation")){
					compte.addOperation(operation);
				}
				else if(reader.getEventType() == XMLStreamConstants.START_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("date")){
					operation.setDate(DATE_FORMAT.parse(reader.getElementText()));
				}
				else if(reader.getEventType() == XMLStreamConstants.START_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("libelle")){
					operation.setLibelle(reader.getElementText());
				}
				else if(reader.getEventType() == XMLStreamConstants.START_ELEMENT
						&& reader.getLocalName().equalsIgnoreCase("montant")){
					operation.setMontant(EURO_FORMAT.parse(reader.getElementText()).doubleValue());
				}
				
				reader.next();	
			}


			
			
		}
		catch(IOException e){
			
		}
		catch(XMLStreamException e){
			
		}
		catch(ParseException e){
			
		}
		finally{
			try{
				if(reader!=null) reader.close();
			}catch(Exception e){}
		}
	}
	
	public void saveXML(String filename){
		XMLStreamWriter writer = null;
		try{
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			writer = factory.createXMLStreamWriter(new FileWriter(filename));

			writer.writeStartDocument();
			writer.writeCharacters("\n");
			writer.writeStartElement("banque");
			for(Client client : this.clients){
				writer.writeCharacters("\n\t");
				writer.writeStartElement("client");
				writer.writeCharacters("\n\t\t");
				writer.writeStartElement("nom");
				writer.writeCharacters(client.getNom());
				writer.writeEndElement();
				writer.writeCharacters("\n\t\t");
				writer.writeStartElement("prenom");
				writer.writeCharacters(client.getPrenom());
				writer.writeEndElement();
				for(Compte compte : client.getComptes()){
					writer.writeCharacters("\n\t\t");
					writer.writeStartElement("compte");
					writer.writeAttribute("numero", compte.getNumero());
					for(Operation operation : compte.getOperations()){
						writer.writeCharacters("\n\t\t\t");
						writer.writeStartElement("operation");
						writer.writeAttribute("type", operation.getClass().getSimpleName());
						writer.writeCharacters("\n\t\t\t\t");
						writer.writeStartElement("date");
						writer.writeCharacters(DATE_FORMAT.format(operation.getDate()));
						writer.writeEndElement();
						writer.writeCharacters("\n\t\t\t\t");
						writer.writeStartElement("libelle");
						writer.writeCharacters(operation.getLibelle());
						writer.writeEndElement();
						writer.writeCharacters("\n\t\t\t\t");
						writer.writeStartElement("montant");
						writer.writeCharacters(EURO_FORMAT.format(operation.getMontant()));
						writer.writeEndElement();
						writer.writeCharacters("\n\t\t\t");
						writer.writeEndElement();		
					}
					writer.writeCharacters("\n\t\t");
					writer.writeEndElement();		
				}
				writer.writeCharacters("\n\t");
				writer.writeEndElement();
			}
			writer.writeCharacters("\n");
			writer.writeEndElement();
			writer.writeEndDocument();
			
			
			
		}
		catch(IOException e){
			
		}
		catch(XMLStreamException e){
			
		}
		finally{
			try{
				if(writer!=null) writer.close();
			}catch(Exception e){}
		}
		
	}

	public void loadText(String filename){
		BufferedReader reader = null;
		Client client = null;
		
		try{
			reader = new BufferedReader(new FileReader(filename));
			
			while(reader.ready()){
				String ligne = reader.readLine();

				if(ligne.equals("")){
					// ligne vide
				}
				else if(ligne.indexOf(" : ") == 8){
					// ligne de compte
					String numero = ligne.substring(0, 8);
					this.addCompte(client, numero);
				}
				else if(ligne.indexOf(" : ") == 10){
					// ligne d'operation
					
				}
				else {
					// ligne de client
					String nom = ligne.substring(0, ligne.indexOf(" "));
					String prenom = ligne.substring(ligne.indexOf(" ") + 1, ligne.indexOf(" :"));
					client = this.addClient(nom, prenom);
				}
			}
		}
		catch(IOException e){
		}
		finally{
			try{
				if(reader!=null) reader.close();
			}catch(Exception e){}
		}
	}
	
	public void saveText(String filename){
		FileWriter writer = null;
		try{
			writer = new FileWriter(filename);
			writer.write("j'écris dans mon fichier banque.txt\n");
			writer.write(this.toString());
		}
		catch(IOException e){}
		finally{
			try{
				if(writer!=null) writer.close();
			}catch(Exception e){}
		}
	}
	
	public void loadJDBC (){
		
		Connection connexion = null;

		try{
			// Enregistrement du Driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/banque", "root", "root");

			Statement stmt = connexion.createStatement();

			ResultSet selectClients = stmt.executeQuery("SELECT id,nom,prenom FROM client");
			while(selectClients.next()){
				
				Client client = new Client(selectClients.getString("nom"),selectClients.getString("prenom"));
				this.addClient(client);
				
				System.out.println(selectClients.getString("id") + " " + selectClients.getString("nom") + " " + selectClients.getString("prenom"));

			}
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(connexion != null) connexion.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

	public void saveJDBC (){
		Connection connexion = null;
		try{
			// Enregistrement du Driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
//			com.mysql.jdbc.Driver.class.newInstance();
			
			// Ouverture de la connexion
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/banque", "root", "root");

			
			PreparedStatement stmt = connexion.prepareStatement("INSERT INTO client (nom,prenom) VALUES (?,?)");
//			PreparedStatement stmt = connexion.prepareStatement(BANQUE_SQL.getProperty("insert.client"));

			for(Client client : this.clients){
				stmt.setString(1, client.getNom());
				stmt.setString(2, client.getPrenom());
				stmt.executeUpdate();
////				ResultSet clesClient = stmt.getGeneratedKeys();
////				while(clesClient.next()){
////					System.out.println(clesClient.getLong(1));
////				}				
//				PreparedStatement stmtCompte = connexion.prepareStatement(BANQUE_SQL.getProperty("insert.compte"));
//				for(Compte compte : client.getComptes()){
//					stmtCompte.setString(1, compte.getNumero());
//					stmtCompte.executeUpdate();
//				}
//			

							}

			// Cr�ation de la requete simple
//			Statement stmt = connexion.createStatement();
			
			// Execution de la requete SQL
			ResultSet selectClients = stmt.executeQuery("SELECT id,nom,prenom FROM client");

			while(selectClients.next()){
				System.out.println(selectClients.getString("id") + " " + selectClients.getString("nom") + " " + selectClients.getString("prenom"));
			}
			
//			CallableStatement stmtSelect = connexion.prepareCall("{call client_name_s(?)}");
//			stmtSelect.setString(1, "Durand");
//			ResultSet rs = stmtSelect.executeQuery();
//			while(rs.next()){
//				System.out.println(rs.getString("nom") + " - " + rs.getString("prenom"));
//			}
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(connexion != null) connexion.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void serializeXML(String filename) {

		XMLEncoder output = null;
		try {
			output = new XMLEncoder(new BufferedOutputStream(	new FileOutputStream(filename)));
			output.writeObject(this);
		} catch (IOException e) {
		} finally {
			try {
				if (output != null)
					output.close();
			} catch (Exception e) {
			}
		}
	}

	public void serialize(String filename) {
		ObjectOutputStream output = null;
		try {
			output = new ObjectOutputStream(new FileOutputStream(filename));
			output.writeObject(this);
		} catch (IOException e) {

		} finally {
			try {
				if (output != null)
					output.close();
			} catch (Exception e) {
			}
		}
	}

	public static Banque unserialize(String filename){
		Banque banque = null;
		ObjectInputStream input = null;
		try{
			input = new ObjectInputStream(new FileInputStream(filename));
			banque = (Banque)input.readObject();
		}
		catch(IOException e){
			
		}
		catch(ClassNotFoundException e){
			
		}
		finally{
			try {
				if(input != null) input.close();
			}catch(Exception e){}
		}
		return banque;
	}

	public List<Client> rechercherClient(String recherche) {
		
		return this.clients.stream()
				.filter(
					client -> (client.getPrenom()+client.getNom())
								.toLowerCase()
								.contains(recherche)
				)
				.sorted( (cli1,cli2) -> cli1.getNom().compareToIgnoreCase(cli2.getNom()) )
				.collect(Collectors.toList());
		
//		recherche = recherche.toLowerCase();
//		List<Client> clients = new LinkedList<Client>();
//		for (Client client : this.clients) {
//			if ( client.getNom().toLowerCase().contains(recherche) 
//			  || client.getPrenom().toLowerCase().contains(recherche) ) {
//				
//				clients.add(client);
//				
//			}
//		}
//		return clients;
	}
	
}
