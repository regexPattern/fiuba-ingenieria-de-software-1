package memo1.ejercicio1;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import io.cucumber.java.en.*;

public class OwnershipSteps {
	private Account account;
	private Client owner;
	private Client coOwner;
	private Exception operationResult;
	private ArrayList<Client> prevCoOwners = new ArrayList<>();
	private LocalDate marriageDate;

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
		try {
			account.setOwner(owner);
		} catch (Exception exception) {
			operationResult = exception;
		}
	}

	@Then("The account owner should be the client")
	public void verifyAccountOwner() {
		assertEquals(account.getOwner(), owner);
	}

	@When("I assign the client as one of the account co-owner")
	public void setClientAsCoOwner() {
		try {
			prevCoOwners = account.getCoOwners();
			account.setCoOwner(coOwner);
		} catch (Exception exception) {
			operationResult = exception;
		}
	}

	@Then("One of the account co-owners should be the client")
	public void verifyAccountCoOwners() {
		assertTrue(account.getCoOwners().contains(coOwner));
	}

	@And("A registered client with DNI {long}, name {string} and surname {string} who is the owner of the account")
	public void createClientAndSetAsOwner(Long dni, String name, String surName) {
		Client accountOwner = new Client(dni, name, surName);
		account.setOwner(accountOwner);
		owner = accountOwner;
		coOwner = owner;
	}

	@Then("The ownership assignment operation should be denied")
	public void verifyOperationResult() {
		assertNotNull(operationResult);
	}

	@And("The client with DNI {long} should remain as the account owner")
	public void verifyOwnerRemains(Long dni) {
		assertEquals(account.getOwner().getDni(), dni);
	}

	@And("The account co-owners should remain the same")
	public void verifyCoOwnersRemain() {
		assertEquals(account.getCoOwners(), prevCoOwners);
	}

	@And("A registered client with DNI {long}, name {string} and surname {string} who married the account owner on {string}")
	public void createClientWhoIsPartnerOfOwner(Long dni, String name, String surName, String marriageDateString) {
		Client ownerPartner = new Client(dni, name, surName);
		marriageDate = owner.setPartner(ownerPartner, marriageDateString);
	}

	@Then("The marriage date should be {string}")
	public void verifyMarriageDate(String marriageDateString) {
		assertEquals(marriageDate, LocalDate.parse(marriageDateString, Client.dateFormatter));
	}
}
