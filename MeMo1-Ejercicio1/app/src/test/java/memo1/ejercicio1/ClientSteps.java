package memo1.ejercicio1;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;

import io.cucumber.java.en.*;

public class ClientSteps {
	private Long personDni;
	private String personName;
	private String personSurName;
	private String personBirthDateString;
	private String personAddress;

	private Client client;
	private ClientRegistry clientRegistry;
	private Exception operationResult;

	@Given("A person with DNI {long}, name {string}, surname {string}, birthdate {string} and address {string}")
	public void createPerson(Long dni, String name, String surName, String birthDateString, String address) {
		personDni = dni;
		personName = name;
		personSurName = surName;
		personBirthDateString = birthDateString;
		personAddress = address;
	}

	@When("I sign-up the person as a client")
	public void signUpPersonAsClient() {
		client = new Client(personDni, personName, personSurName, personBirthDateString, personAddress);
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

	@And("The client birthdate should be {string}")
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
		clientRegistry.signUp(client);
	}

	@And("A person who wants to become a client and has DNI {long}, name {string} and surname {string}")
	public void createPersonWithAlreadyTakenDni(Long dni, String name, String surName) {
		personDni = dni;
		personName = name;
		personSurName = surName;
	}

	@When("I try to sign-up the second person as a client")
	public void tryToSignUpPersonWithAlreadyTakenDni() {
		Client newClient = new Client(personDni, personName, personSurName);
		try {
			clientRegistry.signUp(newClient);
		} catch (Exception exception) {
			operationResult = exception;
		}
	}

	@Then("The client sign-up operation should be denied")
	public void verifyOperation() {
		assertNotNull(operationResult);
	}
}
