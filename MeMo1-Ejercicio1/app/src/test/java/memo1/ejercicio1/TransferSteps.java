package memo1.ejercicio1;

import static org.junit.Assert.*;

import java.util.HashSet;

import io.cucumber.java.en.*;

public class TransferSteps {
	private Account sender;
	private Account receiver;
	private Exception operationResult;
	private TransferLog transferLog;

	@Given("A sender account with CBU {long}, alias {string} and a balance of {double}")
	public void createSenderAccount(Long cbu, String alias, double balance) {
		sender = new Account(cbu, alias, balance);
	}

	@And("A receiver account with CBU {long}, alias {string} and a balance of {double}")
	public void createReceiverAccount(Long cbu, String alias, double balance) {
		receiver = new Account(cbu, alias, balance);
	}

	@When("I transfer {double} from the sender account into the receiver account")
	public void transferFromSenderToReceiver(double amount) {
		operationResult = null;
		transferLog = null;

		try {
			transferLog = sender.transfer(receiver, amount);
		} catch (Exception exception) {
			operationResult = exception;
		}
	}

	@Then("The transfer should be logged")
	public void verifyTransferLogged() {
		assertNotNull(transferLog);
	}

	@Then("The transfer should not be logged")
	public void verifyTransferNotLogged() {
		assertNull(transferLog);
	}

	@Then("The sender account balance should be {double}")
	@Then("The sender account balance should remain {double}")
	public void verifySenderAccountBalance(double balance) {
		assertEquals(sender.getBalance(), balance, 0.01);
	}

	@And("The receiver account balance should be {double}")
	@And("The receiver account balance should remain {double}")
	public void verifyReceiverAccountBalance(double balance) {
		assertEquals(receiver.getBalance(), balance, 0.01);
	}

	@Then("The transfer should be denied")
	public void verifyTransferDenied() {
		assertNotNull(operationResult);
	}

	@And("No receiver account")
	public void setReceiverAsNonExistingAccount() {
		receiver = null;
	}

	@Given("The same account with CBU {long} as the receiver")
	public void setReceiverAccountToSameAsSender(double cbu) {
		receiver = sender;
	}

	@And("The transfer log should have a correlative code")
	public void verifyLogHasCorrelativeCode() {
		assertNotNull(transferLog.getCorrelativeCode());
	}

	@And("The transfer log should have a date")
	public void verifyLogHasDate() {
		assertNotNull(transferLog.getDate());
	}

	@And("The transfer log should have a time")
	public void verifyLogHasTime() {
		assertNotNull(transferLog.getTime());
	}

    @And("The logged transfer type should be {string}")
	public void verifyLogType(String type) {
		assertEquals(transferLog.getType(), type);
	}

	@And("The logged transfered amount should be {double}")
	public void verifyLoggedAmount(Double amount) {
		assertEquals(transferLog.getAmount(), amount);
	}

    @And("The transfer log should be associated with the given accounts")
	public void verifyTwoAssociatedAccounts() {
		HashSet<Long> associatedAccountsCbus = transferLog.getAssociatedAccountsCbus();

		assertEquals(associatedAccountsCbus.size(), 2);
		assertTrue(associatedAccountsCbus.contains(sender.getCbu()));
		assertTrue(associatedAccountsCbus.contains(receiver.getCbu()));
	}
}
