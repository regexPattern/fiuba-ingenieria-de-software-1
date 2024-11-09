package memo1.ejercicio1.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDate;
import memo1.ejercicio1.Account;
import memo1.ejercicio1.Branch;
import memo1.ejercicio1.Client;
import memo1.ejercicio1.ClientRegistry;

public class ClientSteps {

    private final AccountCommonSteps accountSteps;
    private final ClientCommonSteps clientSteps;
    private final OperationResultCommonSteps operationResultSteps;

    private Client client;
    private ClientRegistry clientRegistry;

    public ClientSteps(
        AccountCommonSteps accountSteps,
        ClientCommonSteps clientSteps,
        OperationResultCommonSteps operationResultSteps
    ) {
        this.accountSteps = accountSteps;
        this.clientSteps = clientSteps;
        this.operationResultSteps = operationResultSteps;
    }

    @Before
    public void reset() {
        this.clientRegistry = new ClientRegistry();
    }

    @Given(
        "I create a client with DNI {long}, name {string}, surname {string}, birth date {string} and address {string}"
    )
    public void createPerson(
        long dni,
        String name,
        String surName,
        String birthDateString,
        String address
    ) {
        client = new Client(dni, name, surName, birthDateString, address);
    }

    @Given(
        "A client with DNI {long}, name {string}, surname {string} that has no accounts"
    )
    public void createClientWithNoAccounts(
        long dni,
        String name,
        String surname
    ) {
        client = new Client(dni, name, surname);
        clientRegistry.register(client);
    }

    @Given(
        "A client with DNI {long}, name {string}, surname {string}, birth date {string} and address {string}"
    )
    public void createClientWithBirthDate(
        long dni,
        String name,
        String surName,
        String birthDateString,
        String address
    ) {
        client = new Client(dni, name, surName, birthDateString, address);
        clientRegistry.register(client);
    }

    @When(
        "I update the client information with name {string}, surname {string}, birth date {string} and address {string}"
    )
    public void updateClientInformation(
        String name,
        String surName,
        String birthDateString,
        String address
    ) {
        client.update(name, surName, birthDateString, address);
    }

    @When("I try to update the client birth date to {string}")
    public void tryToUpdateClientBirthDate(String birthDateString) {
        operationResultSteps.execute(() ->
            client.update(null, null, birthDateString, null)
        );
    }

    @And("The client birth date should remain {string}")
    public void verifyBirthDateRemains(String birthDateString) {
        LocalDate expectedBirthDate = LocalDate.parse(
            birthDateString,
            Client.dateFormatter
        );
        assertEquals(client.getBirthDate(), expectedBirthDate);
    }

    @Given("A client with DNI {long}, name {string}, surname {string}")
    public void createClient(long dni, String name, String surname) {
        client = new Client(dni, name, surname);
        clientRegistry.register(client);
    }

    @When("I delete the client")
    public void deleteClient() {
        operationResultSteps.execute(() ->
            clientRegistry.unregister(
                client.getDni(),
                accountSteps.getAccountRegistry()
            )
        );
    }

    @When("I try to delete the client")
    public void tryToDeleteClient() {
        operationResultSteps.execute(() ->
            clientRegistry.unregister(
                client.getDni(),
                accountSteps.getAccountRegistry()
            )
        );
    }

    @When(
        "I try to register another client with DNI {long}, name {string} and surname {string}"
    )
    public void createPersonWithAlreadyTakenDni(
        long dni,
        String name,
        String surName
    ) {
        operationResultSteps.execute(() ->
            clientSteps
                .getClientRegistry()
                .register(new Client(dni, name, surName))
        );
    }

    @Given(
        "I try to delete a client with DNI {long} that has not been registered"
    )
    public void tryToDeleteUnregisteredClient(long dni) {
        operationResultSteps.execute(() ->
            clientRegistry.unregister(dni, accountSteps.getAccountRegistry())
        );
    }

    @And(
        "An account with CBU {long}, alias {string} where the client is the owner"
    )
    public void createAccountWithOwner(long cbu, String alias) {
        Account account = new Account(
            cbu,
            alias,
            new Branch(1L, "someName", "someAddress"),
            client,
            0.0
        );
        accountSteps.getAccountRegistry().register(account);
    }

    @And(
        "An account with CBU {long}, alias {string} where the client is the co-owner"
    )
    public void createAccountWithCoOwner(long cbu, String alias) {
        Client owner = new Client(999999999L, "Main", "Owner");
        clientRegistry.register(owner);
        Account account = new Account(
            cbu,
            alias,
            new Branch(1L, "someName", "someAddress"),
            owner,
            0.0
        );
        account.setCoOwner(client);
        accountSteps.getAccountRegistry().register(account);
    }

    @Then("The client DNI should be {long}")
    public void verifyDni(long dni) {
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
        LocalDate birthDate = LocalDate.parse(
            birthDateString,
            Client.dateFormatter
        );
        assertEquals(client.getBirthDate(), birthDate);
    }

    @And("The client address should be {string}")
    public void verifyAddress(String address) {
        assertEquals(client.getAddress(), address);
    }

    @Then("The client should be deleted")
    public void verifyClientDeletion() {
        assertFalse(clientRegistry.getClients().contains(client));
    }

    @And("The client should remain registered")
    public void verifyClientStillRegistered() {
        assertTrue(clientRegistry.getClients().contains(client));
    }
}
