package memo1.ejercicio1;

import static org.junit.Assert.*;

import io.cucumber.java.en.*;

public class OwnershipSteps {
	private Account account;
	private Client owner;
	private Client coOwner;

	@Given("A registered account with CBU {long} and alias {string}")
	public void createAccount(Long cbu, String alias) {
		account = new Account(cbu, alias);
	}

	@And("A registered client with DNI {long}, name {string} and surname {string}")
	public void createClient(Long dni, String name, String surName) {
		owner = new Client(dni, name, surName);
		coOwner = owner;
	}

	@When("I assign the client as the account owner")
	public void setClientAsOwner() {
		account.setOwner(owner);
	}

    @Then("The account owner should be the client")
	public void verifyAccountOwner() {
		assertEquals(account.getOwner(), owner);
	}

    @When("I assign the client as one of the account co-owner")
	public void setClientAsCoOwner() {
		account.setCoOwner(coOwner);
	}

    @Then("One of the account co-owners should be the client")
	public void verifyAccountCoOwners() {
		assertTrue(account.getCoOwners().contains(coOwner));
	}

    @And("A registered client with DNI {long}, name {string} and surname {string} who is the owner of the account")
	public void createClientAndSetAsOwner(Long dni, String name, String surName) {
		Client client = new Client(dni, name, surName);
		account.setOwner(client);
		coOwner = client;
	}
}
