package memo1.ejercicio1.steps;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import java.util.ArrayList;
import memo1.ejercicio1.Account;
import memo1.ejercicio1.AccountRegistry;
import memo1.ejercicio1.Branch;
import memo1.ejercicio1.Client;

public class AccountCommonSteps {
  private Account account;
  private AccountRegistry accountRegistry;

  @Before
  public void reset() {
    account = null;
    accountRegistry = new AccountRegistry();
  }

  public Account getAccount() {
    return account;
  }

  public AccountRegistry getAccountRegistry() {
    return accountRegistry;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public ArrayList<Account> getAccounts() {
    return accountRegistry.getAccounts();
  }

  @Given("An account with CBU {long}, alias {string} and a balance of {double}")
  public void createAccountWithCbuAliasAndBalance(long cbu, String alias, double balance) {
    account =
        new Account(
            cbu,
            alias,
            new Branch(1L, "someName", "someAddress"),
            new Client(123456789L, "someName", "someSurName"),
            balance);

    accountRegistry.register(account);
  }

  @And("The account balance should be {double}")
  public void verifyAccountBalance(Double balance) {
    assertEquals(account.getBalance(), balance, 0.01);
  }
}
