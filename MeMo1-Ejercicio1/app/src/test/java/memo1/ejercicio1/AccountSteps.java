package memo1.ejercicio1;

import static org.junit.Assert.*;
import io.cucumber.java.en.*;

public class AccountSteps {
    private Account account;
    private boolean operationResult;

    @Given("I create an account with CBU {long}")
    public void createAccountWithDefaultBalance(long cbu) {
        account = new Account();
        account.setCbu(cbu);
    }

    @Given("I create an account with CBU {long} and a balance of {double}")
    public void createAccountWithInitialBalance(long cbu, double balance) {
        account = new Account(cbu, balance);
    }

    @Given("An account with CBU {long} and a balance of {double}")
    public void anAccountWithCBUAndBalance(long cbu, double balance) {
        account = new Account(cbu, balance);
    }

    @When("I deposit {double} into the account")
    public void depositIntoAccount(double amount) {
        operationResult = account.deposit(amount);
    }

    @When("I try to deposit {double} into the account")
    public void tryToDepositIntoAccount(double amount) {
        operationResult = account.deposit(amount);
    }

    @When("I withdraw {double} from the account")
    public void withdrawFromAccount(double amount) {
        operationResult = account.withdraw(amount);
    }

    @When("I try to withdraw {double} from the account")
    public void tryToWithdrawFromAccount(double amount) {
        operationResult = account.withdraw(amount);
    }

    @Then("The account balance should be {double}")
    public void verifyAccountBalance(double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance(), 0.01);
    }

    @Then("The operation should be denied")
    public void verifyOperationDenied() {
        assertFalse(operationResult);
    }

    @Then("The account balance should remain {double}")
    public void verifyBalanceRemains(double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance(), 0.01);
    }

    @Then("The operation should be denied due to insufficient funds")
    public void verifyInsufficientFunds() {
        assertFalse(operationResult);
    }
}
