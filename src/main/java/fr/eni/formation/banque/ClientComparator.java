package fr.eni.formation.banque;

import java.lang.reflect.Field;
import java.util.Comparator;

public class ClientComparator implements Comparator<Client> {
	private Field critere;

	public ClientComparator() {
		this("nom");
	}
	
	public ClientComparator(String attribut) {
		super();
		try{
			critere = Client.class.getField(attribut);
		}catch(NoSuchFieldException e){
			System.err.println(e.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int compare(Client c1, Client c2){
		try{
			if(critere != null)
				return ((Comparable)critere.get(c1)).compareTo(critere.get(c2));
			else
				return 0;
		}catch(IllegalAccessException e){
			System.err.println(e.getMessage());
			return 0;
		}
	}

}
