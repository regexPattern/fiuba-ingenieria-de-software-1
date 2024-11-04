package memo1.ejercicio1.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import memo1.ejercicio1.Account;
import memo1.ejercicio1.AccountRegistry;
import memo1.ejercicio1.Branch;
import memo1.ejercicio1.Client;

public class AccountSteps {
  private final BranchCommonSteps branchSteps;
  private final ClientCommonSteps clientSteps;
  private final OperationDeniedCommonSteps operationResultSteps;

  private Account account1;
  private Account account2;
  private AccountRegistry accountRegistry;
  private Branch branch;

  public AccountSteps(
      BranchCommonSteps branchSteps,
      ClientCommonSteps clientSteps,
      OperationDeniedCommonSteps operationResultSteps) {
    this.branchSteps = branchSteps;
    this.clientSteps = clientSteps;
    this.operationResultSteps = operationResultSteps;
  }

  @Before
  public void resetAccountRegistry() {
    accountRegistry = new AccountRegistry();
  }

  @When(
      "I create an account with CBU {long} and alias {string} in the given branch and owned by the given client")
  public void createAccountWithDefaultBalance(long cbu, String alias) {
    account1 = new Account(cbu, alias, branchSteps.getBranch(), clientSteps.getClient());
  }

  @When(
      "I create an account with CBU {long}, alias {string} and balance of {double} in the given branch and owned by the given client")
  public void createAccountWithBalance(long cbu, String alias, Double balance) {
    account1 = new Account(cbu, alias, branchSteps.getBranch(), clientSteps.getClient(), balance);
  }

  @Then("The account CBU should be {long}")
  public void verifyAccountCbu(long cbu) {
    assertEquals(account1.getCbu(), cbu);
  }

  @And("The account alias should be {string}")
  public void verifyAccountAlias(String alias) {
    assertEquals(account1.getAlias(), alias);
  }

  @And("The account balance should be {double}")
  public void verifyAccountBalance(Double balance) {
    assertEquals(account1.getBalance(), balance, 0.01);
  }

  @And(
      "The account branch should be the branch with code {long}, name {string} and address {string}")
  public void verifyAccountBranch(long code, String name, String address) {
    Branch branch = account1.getBranch();

    assertEquals(branch.getCode(), code);
    assertEquals(branch.getName(), name);
    assertEquals(branch.getAddress(), address);
  }

  @And("The account owner should be the client with DNI {long}, name {string} and surname {string}")
  public void verifyAccountOwner(long dni, String name, String surName) {
    Client owner = account1.getOwner();

    assertEquals(owner.getDni(), dni);
    assertEquals(owner.getName(), name);
    assertEquals(owner.getSurName(), surName);
  }

  @Given(
      "An account with CBU {long}, alias {string}, branch with code {long} and owner with DNI {long}")
  @Given(
      "I register an account with CBU {long}, alias {string}, branch with code {long} and owner with DNI {long}")
  public void createFirstAccountWithoutSpecificBranchAndOwner(
      long cbu, String alias, long branchCode, long ownerDni) {
    account1 =
        new Account(
            cbu,
            alias,
            new Branch(branchCode, "Some branch", "Some address"),
            new Client(ownerDni, "Some name", "Some surname"));

    operationResultSteps.execute(() -> accountRegistry.register(account1));
  }

  @When(
      "I try to register an account with CBU {long}, alias {string}, branch with code {long} and owner with DNI {long}")
  @When(
      "I register another account with CBU {long}, alias {string}, branch with code {long} and owner with DNI {long}")
  public void createAndRegisterSecondAccountWithoutSpecificBranchAndOwner(
      long cbu, String alias, long branchCode, long ownerDni) {
    account2 =
        new Account(
            cbu,
            alias,
            new Branch(branchCode, "Some branch", "Some address"),
            new Client(ownerDni, "Some name", "Some surname"));

    operationResultSteps.execute(() -> accountRegistry.register(account2));
  }

  @Then("The first account should be registered")
  public void verifyFirstAccountRegistered() {
    assertTrue(accountRegistry.getAccounts().contains(account1));
  }

  @And("The second account should be registered")
  public void verifySecondAccountRegistered() {
    assertTrue(accountRegistry.getAccounts().contains(account2));
  }

  @And("The second account should not be registered")
  public void verifySecondAccountNotRegistered() {
    ArrayList<Account> registeredAccounts = accountRegistry.getAccounts();

    assertEquals(registeredAccounts.size(), 1);
    assertFalse(registeredAccounts.contains(account2));
  }

  @Given("A branch with code {long}, name {string} and address {string} which is closed")
  public void createClosedBranch(long code, String name, String address) {
    branch = new Branch(code, name, address);
    branch.setOpen(false);
  }

  @When(
      "I try to create an account with CBU {long}, alias {string}, branch with code {long} and owner with DNI {long}")
  public void registerAccountWithClosedBranch(
      long cbu, String alias, Long branchCode, Long ownerDni) {
    operationResultSteps.execute(
        () -> new Account(cbu, alias, branch, new Client(ownerDni, "Some name", "Some surname")));
  }

  @And("The account should not be created")
  public void verifyAccountNotRegistered() {
    assertEquals(accountRegistry.getAccounts().size(), 0);
  }
}
