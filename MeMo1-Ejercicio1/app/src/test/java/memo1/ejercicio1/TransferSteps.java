package memo1.ejercicio1;

import static org.junit.Assert.*;
import io.cucumber.java.en.*;

public class TransferSteps {
	private Account sender;
	private Account receiver;
	private Exception operationResult;

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

		try {
			sender.transfer(receiver, amount);
		} catch (Exception exception) {
			operationResult = exception;
		}
	}

	@Then("The sender account balance should be {double}")
	@Then("The sender account balance should remain {double}")
	public void checkSenderAccountBalance(double balance) {
		assertEquals(sender.getBalance(), balance, 0.01);
	}

	@And("The receiver account balance should be {double}")
	@And("The receiver account balance should remain {double}")
	public void checkReceiverAccountBalance(double balance) {
		assertEquals(receiver.getBalance(), balance, 0.01);
	}

	@Then("The transfer should be denied")
	public void checkTransferDenied() {
		assertNotNull(operationResult);
	}

	@Given("The same account with CBU {long} as the receiver")
	public void setReceiverAccountToSameAsSender(double cbu) {
		receiver = sender;
	}
}
