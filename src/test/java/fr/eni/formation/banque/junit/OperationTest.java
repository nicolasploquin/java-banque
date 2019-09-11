package fr.eni.formation.banque.junit;

import fr.eni.formation.banque.BanqueException;
import fr.eni.formation.banque.Client;
import fr.eni.formation.banque.Credit;
import fr.eni.formation.banque.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OperationTest {
	

	@Test( expected = BanqueException.class )
	public void newCredit() throws Exception {
		Operation ope = new Credit("unedate","un libellé",1234.0);
	}



}
