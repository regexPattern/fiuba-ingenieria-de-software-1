package memo1.ejercicio1;

import static org.junit.Assert.*;
import io.cucumber.java.en.*;

public class AccountSteps {
    private Account account;
    private Exception operationResult;

    @Given("I create an account with CBU {long} and alias {string}")
    public void createAccountWithDefaultBalance(long cbu, String alias) {
        account = new Account(cbu, alias);
    }

    @Given("I create an account with CBU {long}, alias {string} and a balance of {double}")
    @Given("An account with CBU {long}, alias {string} and a balance of {double}")
    public void createAccountWithInitialBalance(long cbu, String alias, double balance) {
        account = new Account(cbu, alias, balance);
    }

    @When("I deposit {double} into the account")
    public void depositIntoAccount(double amount) {
        try {
            account.deposit(amount);
        } catch (Exception exception) {
            operationResult = exception;
        }
    }

    @When("I try to deposit {double} into the account")
    public void tryToDepositIntoAccount(double amount) {
        operationResult = null;

        try {
            account.deposit(amount);
        } catch (Exception exception) {
            operationResult = exception;
        }
    }

    @When("I withdraw {double} from the account")
    public void withdrawFromAccount(double amount) {
        operationResult = null;

        try {
            account.withdraw(amount);
        } catch (Exception exception) {
            operationResult = exception;
        }
    }

    @When("I try to withdraw {double} from the account")
    public void tryToWithdrawFromAccount(double amount) {
        operationResult = null;

        try {
            account.withdraw(amount);
        } catch (Exception exception) {
            operationResult = exception;
        }
    }

    @And("The account alias should be {string}")
    public void verifyAccountAlias(String alias) {
        assertEquals(account.getAlias(), alias);
    }

    @And("The account balance should be {double}")
    public void verifyAccountBalance(double balance) {
        assertEquals(balance, account.getBalance(), 0.01);
    }

    @Then("The operation should be denied")
    public void verifyOperationDenied() {
        assertNotNull(operationResult);
    }

    @Then("The account balance should remain {double}")
    public void verifyBalanceRemains(double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance(), 0.01);
    }

    @Then("The operation should be denied due to insufficient funds")
    public void verifyInsufficientFunds() {
        assertNotNull(operationResult);
    }
}
