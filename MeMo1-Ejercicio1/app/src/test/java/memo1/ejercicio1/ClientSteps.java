package memo1.ejercicio1;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;

import io.cucumber.java.en.*;

public class ClientSteps {
	private Client client;
	private ClientRegistry clientRegistry;
	private Exception operationResult;

	@Given("I create a client with DNI {long}, name {string}, surname {string}, birth date {string} and address {string}")
	public void createPerson(Long dni, String name, String surName, String birthDateString, String address) {
		client = new Client(dni, name, surName, birthDateString, address);
	}

	@Then("The client DNI should be {long}")
	public void verifyDni(Long dni) {
		assertEquals(client.getDni(), dni);
	}

	@And("The client name should be {string}")
	public void verifyName(String name) {
		assertEquals(client.getName(), name);
	}

	@And("The client surname should be {string}")
	public void verifySurName(String surName) {
		assertEquals(client.getSurName(), surName);
	}

	@And("The client birth date should be {string}")
	public void verifyBirthDate(String birthDateString) {
		LocalDate birthDate = LocalDate.parse(birthDateString, Client.dateFormatter);
		assertEquals(client.getBirthDate(), birthDate);
	}

	@And("The client address should be {string}")
	public void verifyAddress(String address) {
		assertEquals(client.getAddress(), address);
	}

	@Given("A client with DNI {long}, name {string} and surname {string}")
	public void createClientWithDniOnly(Long dni, String name, String surName) {
		clientRegistry = new ClientRegistry();
		client = new Client(dni, name, surName);
		clientRegistry.registerClient(client);
	}

	@When("I try to create another client with DNI {long}, name {string} and surname {string}")
	public void createPersonWithAlreadyTakenDni(Long dni, String name, String surName) {
		Client newClient = new Client(dni, name, surName);
		try {
			clientRegistry.registerClient(newClient);
		} catch (Exception exception) {
			operationResult = exception;
		}
	}

	@Then("The client register operation should be denied")
	public void verifyOperation() {
		assertNotNull(operationResult);
	}
}
