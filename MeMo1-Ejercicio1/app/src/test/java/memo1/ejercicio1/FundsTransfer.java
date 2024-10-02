package memo1.ejercicio1;

import static org.junit.Assert.*;
import io.cucumber.java.en.*;

public class FundsTransfer {
	private Account sender;
	private Account receiver;
    private boolean operationResult;

	@Given("A sender account with CBU {long} and a balance of {double}")
	public void createSenderAccount(Long cbu, double balance) {
		sender = new Account(cbu, balance);
	}

	@And("A receiver account with CBU {long} and a balance of {double}")
	public void createReceiverAccount(Long cbu, double balance) {
		receiver = new Account(cbu, balance);
	}

	@When("I transfer {double} from the sender account into the receiver account")
	public void transferFromSenderToReceiver(double amount) {
		operationResult = sender.transfer(receiver, amount);
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
		assertFalse(operationResult);
	}

	@Given("The same account with CBU {long} as the receiver")
	public void setReceiverAccountToSameAsSender(double cbu) {
		receiver = sender;
	}
}
