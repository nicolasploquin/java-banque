package fr.eni.formation.banque.junit;

import fr.eni.formation.banque.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ClientTest {
	
	Client client = null;

	@Before
	public void before() throws Exception {
		client = new Client("duPOnt","mArc");
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void setNomUpperCase() {
		assertEquals("DUPONT", client.getNom());
	}

	@Test
	public void setPrenom() {
		assertEquals("Marc", client.getPrenom());
	}

	@Test
	@Ignore
	public void addCompte() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void removeCompte() {
		fail("Not yet implemented");
	}

}
