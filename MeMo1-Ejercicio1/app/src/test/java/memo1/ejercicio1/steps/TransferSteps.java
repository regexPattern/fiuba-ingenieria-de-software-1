package memo1.ejercicio1.steps;

import static org.junit.Assert.*;

import org.junit.Before;

import io.cucumber.java.en.*;
import memo1.ejercicio1.*;

public class TransferSteps {
  private final OperationDeniedCommonSteps operationResultSteps;

  private Account sender;
  private Account receiver;
  private Transaction transaction;

  public TransferSteps(OperationDeniedCommonSteps operationResultSteps) {
    this.operationResultSteps = operationResultSteps;
  }

  @Before
  public void reset() {
    transaction = null;
  }

  @Given("A sender account with CBU {long}, alias {string} and a balance of {double}")
  public void createAccount(long cbu, String alias, double balance) {
    sender = new Account(cbu, alias, dummyBranch(), dummyClient(), balance);
  }

  @And("A receiver account with CBU {long}, alias {string} and a balance of {double}")
  public void createReceiverAccount(long cbu, String alias, double balance) {
    receiver = new Account(cbu, alias, dummyBranch(), dummyClient(), balance);
  }

  @When("I transfer {double} from the sender account into the receiver account")
  public void transferFromSenderToReceiver(double amount) {
    transaction = operationResultSteps.execute(() -> sender.transfer(receiver, amount));
  }

  @Then("The sender account balance should be {double}")
  public void verifySenderAccountBalance(double balance) {
    assertEquals(sender.getBalance(), balance, 0.01);
  }

  @And("The receiver account balance should be {double}")
  public void verifyReceiverAccountBalance(double balance) {
    assertEquals(receiver.getBalance(), balance, 0.01);
  }

  @Then("The transfer should generate a transaction")
  public void verifyTransactionGenerated() {
    assertNotNull(transaction);
  }

  @Then("The transfer should not generate a transaction")
  @And("The operation should not generate a transaction")
  public void verifyTransactionNotGenerated() {
    assertNull(transaction);
  }

  @And("No receiver account")
  public void setReceiverAsNonExistingAccount() {
    receiver = null;
  }

  @Given("The same account with CBU {long} as the receiver")
  public void setReceiverAccountToSameAsSender(double cbu) {
    receiver = sender;
  }

  @And("The transaction should have a correlative code")
  public void verifyTransactionHasCorrelativeCode() {
    assertNotNull(transaction.getCode());
  }

  @And("The transaction should have a date")
  public void verifyTransactionHasDate() {
    assertNotNull(transaction.getDate());
  }

  @And("The transaction should have a time")
  public void verifyTransactionHasTime() {
    assertNotNull(transaction.getTime());
  }

  @And("The transaction type should be {string}")
  public void verifyTransactionType(String type) {
    assertEquals(transaction.getType(), type);
  }

  @And("The transaction amount should be {double}")
  public void verifyTransactionAmount(Double amount) {
    assertEquals(transaction.getAmount(), amount);
  }

  @And("The transaction sender should be account with CBU {long}")
  public void verifyTransactionSender(long cbu) {
    assertEquals(transaction.getSender().getCbu(), cbu);
  }

  @And("The transaction receiver should be account with CBU {long}")
  public void verifyTransactionReceiver(long cbu) {
    assertEquals(transaction.getReceiver().getCbu(), cbu);
  }

  private Branch dummyBranch() {
    return new Branch(1L, "someName", "someAddress");
  }

  private Client dummyClient() {
    return new Client(123456789L, "someName", "someSurName");
  }
}
