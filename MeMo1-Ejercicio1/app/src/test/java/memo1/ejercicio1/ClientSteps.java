package memo1.ejercicio1;

import static org.junit.Assert.*;
import io.cucumber.java.en.*;

import java.time.LocalDate;

public class ClientSteps {
	private Long personDni;
	private String personName;
	private String personSurName;
	private String personBirthDateString;
	private String personAddress;

	private Client client;

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
		try {
			client = new Client(personDni, personName, personSurName, personBirthDateString, personAddress);
		} catch (Exception exception) {
		}
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
	void verifyAddress(String address) {
		assertEquals(client.getAddress(), address);
	}
}
