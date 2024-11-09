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
  private final AccountCommonSteps accountSteps;
  private final BranchCommonSteps branchSteps;
  private final ClientCommonSteps clientSteps;
  private final OperationResultCommonSteps operationResultSteps;

  private Account account1;
  private Account account2;
  private AccountRegistry accountRegistry;
  private Branch branch;
  private ArrayList<Account> prevAccounts;
  private Client coOwner;
  private ArrayList<Client> prevCoOwners;

  public AccountSteps(
      AccountCommonSteps accountSteps,
      BranchCommonSteps branchSteps,
      ClientCommonSteps clientSteps,
      OperationResultCommonSteps operationResultSteps) {
    this.accountSteps = accountSteps;
    this.branchSteps = branchSteps;
    this.clientSteps = clientSteps;
    this.operationResultSteps = operationResultSteps;
  }

  @Before
  public void reset() {
    accountRegistry = new AccountRegistry();
  }

  @When(
      "I create an account with CBU {long} and alias {string} in the given branch and owned by the given client")
  public void createAccountWithDefaultBalance(long cbu, String alias) {
    account1 = new Account(cbu, alias, branchSteps.getBranch(), clientSteps.getClient());
    accountSteps.setAccount(account1);
  }

  @When(
      "I create an account with CBU {long}, alias {string} and balance of {double} in the given branch and owned by the given client")
  public void createAccountWithBalance(long cbu, String alias, Double balance) {
    account1 = new Account(cbu, alias, branchSteps.getBranch(), clientSteps.getClient(), balance);
    accountSteps.setAccount(account1);
  }

  @Then("The account CBU should be {long}")
  public void verifyAccountCbu(long cbu) {
    assertEquals(account1.getCbu(), cbu);
  }

  @And("The account alias should be {string}")
  @And("The first account alias should be {string}")
  public void verifyAccount1Alias(String alias) {
    assertEquals(account1.getAlias(), alias);
  }

  @And("The second account alias should be {string}")
  public void verifyAccount2Alias(String alias) {
    assertEquals(account2.getAlias(), alias);
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
  @And(
      "Another account with CBU {long}, alias {string}, branch with code {long} and owner with DNI {long}")
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

  @When("I update the account alias to be {string}")
  public void updateAccountAlias(String alias) {
    account1.setAlias(alias, new ArrayList<String>());
  }

  @When("I update the account branch to be branch with code {long}")
  public void updateAccountBranch(Long code) {
    account1.setBranch(branchSteps.getBranch());
  }

  @When("I try to update the first account alias to be {string}")
  public void updateAccountAliasToAlreadyTakenOne(String alias) {
    operationResultSteps.execute(() -> account1.setAlias(alias, accountRegistry.getAliases()));
  }

  @When("I try to update the account branch to branch with code {long}")
  public void updateAccountBranchToClosedBranch(long code) {
    operationResultSteps.execute(() -> account1.setBranch(branch));
  }

  @And("The account branch should remain the branch with code {long}")
  public void verifyAccountBranchCode(long code) {
    assertEquals(account1.getBranch().getCode(), code);
  }

  @Given(
      "An account with CBU {long}, alias {string}, branch with code {long}, owner with DNI {long} and a balance of {double}")
  public void createAccountWithBalanceWithoutSpecificBranchAndOwner(
      long cbu, String alias, long branchCode, long ownerDni, double balance) {
    account1 =
        new Account(
            cbu,
            alias,
            new Branch(branchCode, "someName", "someAddress"),
            new Client(ownerDni, "someName", "someSurName"),
            balance);

    accountRegistry.register(account1);
  }

  @When("I unregister the account with CBU {long}")
  @When("I try to unregister the account with CBU {long}")
  public void unRegisterAccount(long cbu) {
    prevAccounts = accountRegistry.getAccounts();
    operationResultSteps.execute(() -> accountRegistry.unRegister(cbu));
  }

  @And("The account with CBU {long} should be unregistered")
  public void verifyAccountUnRegistered(long cbu) {
    assertFalse(accountRegistry.getCbus().contains(cbu));
  }

  @And("The registered accounts should remain the same")
  public void verifyRegisteredAccountsRemainTheSame() {
    assertEquals(prevAccounts, accountRegistry.getAccounts());
  }

  @When("I try to unregister an account with CBU {long} that has not been registered")
  public void unRegisterNotRegisteredAccount(long cbu) {
    prevAccounts = accountRegistry.getAccounts();
    operationResultSteps.execute(() -> accountRegistry.unRegister(cbu));
  }

  @When(
      "I add client with DNI {long}, name {string} and surname {string} as one of the account co-owners")
  public void createClientAsAccountCoOwner(long dni, String name, String surName) {
    coOwner = new Client(dni, name, surName);
    account1.setCoOwner(coOwner);
  }

  @Then("The client with DNI {long} should be one of the account co-owners")
  public void verifyAccountCoOwner(long dni) {
    assertTrue(account1.getCoOwners().contains(coOwner));
  }

  @And("The client with DNI {long} should still be the account owner")
  public void verifyAccountOwner(long dni) {
    assertEquals(account1.getOwner().getDni(), dni);
  }

  @When("I try to set the account owner as one of the account co-owners")
  public void setOwnerAsCoOwner() {
    prevCoOwners = account1.getCoOwners();
    operationResultSteps.execute(() -> account1.setCoOwner(account1.getOwner()));
  }

  @And("The account co-owners should remain the same")
  public void verifyAccountCoOwnersRemainTheSame() {
    assertEquals(account1.getCoOwners(), prevCoOwners);
  }

  @And(
      "A client with DNI {long}, name {string} and surname {string} who is one of the account co-owners")
  public void createClientAsCoOwner(long dni, String name, String surName) {
    coOwner = new Client(dni, name, surName);
    account1.setCoOwner(coOwner);
  }

  @When("I try to set the client with DNI {long} as one of the account co-owners")
  public void setClientAsCoOwner(long dni) {
    prevCoOwners = account1.getCoOwners();
    operationResultSteps.execute(() -> account1.setCoOwner(coOwner));
  }
}
