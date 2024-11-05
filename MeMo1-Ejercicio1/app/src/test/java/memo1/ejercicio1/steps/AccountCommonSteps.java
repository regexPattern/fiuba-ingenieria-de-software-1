package memo1.ejercicio1.steps;

import java.util.ArrayList;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import memo1.ejercicio1.Account;
import memo1.ejercicio1.AccountRegistry;
import memo1.ejercicio1.Branch;
import memo1.ejercicio1.Client;

public class AccountCommonSteps {
  private AccountRegistry accountRegistry;

  public ArrayList<Account> getAccounts() {
    return accountRegistry.getAccounts();
  }

  @Before
  public void reset() {
    accountRegistry = new AccountRegistry();
  }

  //@Given("An account with CBU {long}, alias {string}, branch with code {long} and owner with DNI {long}")
  //public void createBranch(long cbu, String alias, Long branchCode, Long ownerDni) {
  //  accountRegistry.register(new Account(cbu, alias, new Branch(branchCode, "", ""), new Client(ownerDni, "", "")));
  //}
}
