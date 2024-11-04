package memo1.ejercicio1.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import memo1.ejercicio1.Account;
import memo1.ejercicio1.AccountRegistry;
import memo1.ejercicio1.Branch;
import memo1.ejercicio1.Client;
import memo1.ejercicio1.Transaction;

public class AccountSteps {
  private Account account;
  private AccountRegistry accountRegistry;
  private Exception operationResult;
  private Transaction transference;

  @Given("I create an account with CBU {long} and alias {string}")
  public void createAccountWithDefaultBalance(long cbu, String alias) {
    account = new Account(cbu, alias, new Branch(1, "", ""), new Client(1234L, "", "")); // TODO
  }

  @Given("I create an account with CBU {long}, alias {string} and a balance of {double}")
  @Given("An account with CBU {long}, alias {string} and a balance of {double}")
  public void createAccountWithInitialBalance(long cbu, String alias, double balance) {
    account =
        new Account(cbu, alias, new Branch(1, "", ""), new Client(1234L, "", ""), balance); // TODO
  }

  @When("I deposit {double} into the account")
  public void depositIntoAccount(double amount) {
    operationResult = null;
    transference = null;

    try {
      transference = account.deposit(amount);
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @When("I try to deposit {double} into the account")
  public void tryToDepositIntoAccount(double amount) {
    operationResult = null;
    transference = null;

    try {
      transference = account.deposit(amount);
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @When("I withdraw {double} from the account")
  public void withdrawFromAccount(double amount) {
    operationResult = null;
    transference = null;

    try {
      transference = account.withdraw(amount);
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @When("I try to withdraw {double} from the account")
  public void tryToWithdrawFromAccount(double amount) {
    operationResult = null;
    transference = null;

    try {
      transference = account.withdraw(amount);
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @And("The account CBU should be {long}")
  public void verifyAccountCbu(Long cbu) {
    assertEquals(account.getCbu(), cbu);
  }

  @And("The account alias should be {string}")
  public void verifyAccountAlias(String alias) {
    assertEquals(account.getAlias(), alias);
  }

  @And("The account balance should be {double}")
  public void verifyAccountBalance(double balance) {
    assertEquals(balance, account.getBalance(), 0.01);
  }

  @Then("The account balance should remain {double}")
  public void verifyBalanceRemains(double expectedBalance) {
    assertEquals(expectedBalance, account.getBalance(), 0.01);
  }

  @Then("The operation should be denied")
  public void verifyOperationDenied() {
    assertNotNull(operationResult);
  }

  @And("The log should be associated with the given account")
  public void verifyOneAssociatedAccount() {
    Account sender = transference.getSender();
    Account receiver = transference.getReceiver();

    assertEquals(sender, account);
    assertEquals(receiver, account);
  }

  @Given("An account with CBU {long} and alias {string}")
  public void createAccountWithCbuAndAlias(Long cbu, String alias) {
    accountRegistry = new AccountRegistry();
    account = new Account(cbu, alias, new Branch(1, "", ""), new Client(123L, "", "")); // TODO
    accountRegistry.register(account);
  }

  @When("I try to create an account with CBU {long} and alias {string}")
  public void tryToCreateAnotherAccountWithCbuAndAlias(Long cbu, String alias) {
    try {
      accountRegistry.register(
          new Account(cbu, alias, new Branch(1, "", ""), new Client(1L, "", "")));
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @And("The second account should not be registered")
  public void verifyOnlyOneAccountRegistered() {
    ArrayList<Account> registeredAccounts = accountRegistry.getAccounts();

    assertEquals(registeredAccounts.size(), 1);
    assertEquals(registeredAccounts.get(0), account);
  }

  @Then("The deposit should be logged")
  @Then("The withdrawal should be logged")
  public void verifyTransferLogged() {
    assertNotNull(transference);
  }

  @And("The log should have a correlative code")
  public void verifyLogHasCorrelativeCode() {
    assertNotNull(transference.getCode());
  }

  @And("The log should have a date")
  public void verifyLogHasDate() {
    assertNotNull(transference.getDate());
  }

  @And("The log should have a time")
  public void verifyLogHasTime() {
    assertNotNull(transference.getTime());
  }

  @And("The logged type should be {string}")
  public void verifyLogType(String type) {
    assertEquals(transference.getType(), type);
  }

  @And("The logged amount should be {double}")
  public void verifyLoggedAmount(Double amount) {
    assertEquals(transference.getAmount(), amount);
  }
}
