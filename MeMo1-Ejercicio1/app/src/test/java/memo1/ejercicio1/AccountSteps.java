package memo1.ejercicio1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import io.cucumber.java.en.*;

public class AccountSteps {
    private Account account;
    private AccountRegistry accountRegistry;
    private Exception operationResult;
	private TransferLog transferLog;

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
        operationResult = null;
        transferLog = null;

        try {
            transferLog = account.deposit(amount);
        } catch (Exception exception) {
            operationResult = exception;
        }
    }

    @When("I try to deposit {double} into the account")
    public void tryToDepositIntoAccount(double amount) {
        operationResult = null;
        transferLog = null;

        try {
            transferLog = account.deposit(amount);
        } catch (Exception exception) {
            operationResult = exception;
        }
    }

    @When("I withdraw {double} from the account")
    public void withdrawFromAccount(double amount) {
        operationResult = null;
        transferLog = null;

        try {
            transferLog = account.withdraw(amount);
        } catch (Exception exception) {
            operationResult = exception;
        }
    }

    @When("I try to withdraw {double} from the account")
    public void tryToWithdrawFromAccount(double amount) {
        operationResult = null;
        transferLog = null;

        try {
            transferLog = account.withdraw(amount);
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
		HashSet<Long> associatedAccountsCbus = transferLog.getAssociatedAccountsCbus();

		assertEquals(associatedAccountsCbus.size(), 1);
		assertTrue(associatedAccountsCbus.contains(account.getCbu()));
	}

    @Given("An account with CBU {long} and alias {string}")
    public void createAccountWithCbuAndAlias(Long cbu, String alias) {
        accountRegistry = new AccountRegistry();
        account = new Account(cbu, alias);
        accountRegistry.registerAccount(account, dummyBranch());
    }

    @When("I try to create an account with CBU {long} and alias {string}")
    public void tryToCreateAnotherAccountWithCbuAndAlias(Long cbu, String alias) {
        try {
            accountRegistry.registerAccount(new Account(cbu, alias), dummyBranch());
        } catch (Exception exception) {
            operationResult = exception;
        }
    }

    @And("The second account should not be registered")
    public void verifyOnlyOneAccountRegistered() {
        ArrayList<Account> registeredAccounts = accountRegistry.getRegisteredAccounts();

        assertEquals(registeredAccounts.size(), 1);
        assertEquals(registeredAccounts.get(0), account);
    }

	@Then("The deposit should be logged")
	@Then("The withdrawal should be logged")
	public void verifyTransferLogged() {
		assertNotNull(transferLog);
	}

	@And("The log should have a correlative code")
	public void verifyLogHasCorrelativeCode() {
		assertNotNull(transferLog.getCorrelativeCode());
	}

	@And("The log should have a date")
	public void verifyLogHasDate() {
		assertNotNull(transferLog.getDate());
	}

	@And("The log should have a time")
	public void verifyLogHasTime() {
		assertNotNull(transferLog.getTime());
	}

    @And("The logged type should be {string}")
	public void verifyLogType(String type) {
		assertEquals(transferLog.getType(), type);
	}

	@And("The logged amount should be {double}")
	public void verifyLoggedAmount(Double amount) {
		assertEquals(transferLog.getAmount(), amount);
	}

	static private Branch dummyBranch() {
		return new Branch(001, "Suc. Belgrano", "Cabildo 1000 CABA");
	}
}
