package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

class TransferTest {
  @Test
  void transferShouldThrowExceptionIfReceiverAccountIsNull() {
    Account account = dummySenderAccount(100.0);

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> account.transfer(null, 100.0));

    assertEquals(exception.getMessage(), "Receiver account cannot be null.");
  }

  @Test
  void transferShouldThrowExceptionIfReceiverAccountIsTheSameAsSender() {
    Account sender = dummySenderAccount(100.0);

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> sender.transfer(sender, 100.0));

    assertEquals(exception.getMessage(), "Receiver account cannot be same as sender.");
  }

  @Test
  void transferShouldThrowExceptionIfAmountIsNegative() {
    Account sender = dummySenderAccount(100.0);
    Account receiver = dummyReceiverAccount(0.0);

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> sender.transfer(receiver, -100.0));

    assertEquals(exception.getMessage(), "Amount cannot be negative.");
  }

  @Test
  void transferShouldDecreaseBalanceOfSenderAccount() {
    Account sender = dummySenderAccount(100.0);
    Account receiver = dummyReceiverAccount(0.0);

    sender.transfer(receiver, 30.0);

    assertEquals(sender.getBalance(), 70.0);
  }

  @Test
  void transferShouldIncreaseBalanceOfReceiverAccount() {
    Account sender = dummySenderAccount(100.0);
    Account receiver = dummyReceiverAccount(0.0);

    sender.transfer(receiver, 30.0);
    assertEquals(receiver.getBalance(), 30.0);

    sender.transfer(receiver, 20.0);
    assertEquals(receiver.getBalance(), 50.0);
  }

  @Test
  void transferShouldThrowExceptionWhenAmountExceedsSenderFunds() {
    Account sender = dummySenderAccount(100.0);
    Account receiver = dummyReceiverAccount(0.0);

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> sender.transfer(receiver, 2000.0));

    assertEquals(exception.getMessage(), "Not enough funds.");
  }

  @Test
  void transferShouldAllowExactAmount() {
    Account sender = dummySenderAccount(100.0);
    Account receiver = dummyReceiverAccount(0.0);

    assertDoesNotThrow(() -> sender.transfer(receiver, sender.getBalance()));
    assertEquals(sender.getBalance(), 0.0);
  }

  @Test
  void withdrawingFromAccountLogsWithdrawalTransaction() {
    Account account = dummyAccount(200.0);

    Transaction transaction = account.withdraw(10.0);

    assertEquals(transaction.getType(), "withdrawal");
    assertEquals(transaction.getAmount(), 10.0);

    assertEquals(transaction.getSender(), account);
    assertEquals(transaction.getReceiver(), account);
  }

  @Test
  void depositingIntoAccountLogsDepositTransaction() {
    Account account = dummyAccount(200.0);

    Transaction transaction = account.deposit(10.0);

    assertEquals(transaction.getType(), "deposit");
    assertEquals(transaction.getAmount(), 10.0);

    assertEquals(transaction.getSender(), account);
    assertEquals(transaction.getReceiver(), account);
  }

  @Test
  void transferingBetweenAccountsLogsTransferTransaction() {
    Account sender = dummyAccount(200.0);
    Account receiver = dummyAccount(0.0);

    Transaction transaction = sender.transfer(receiver, 10.0);

    assertEquals(transaction.getType(), "transfer");
    assertEquals(transaction.getAmount(), 10.0);

    assertEquals(transaction.getSender(), sender);
    assertEquals(transaction.getReceiver(), receiver);
  }

  private Account dummyAccount(Double balance) {
    return dummySenderAccount(balance);
  }

  private Branch dummyBranch() {
    return new Branch(1, "Branch 1", "Paseo Colón 950");
  }

  private Client dummyOwner() {
    return new Client(96113425L, "Carlos", "Castillo");
  }

  private Account dummySenderAccount(double balance) {
    return new Account(123456789L, "sender", dummyBranch(), dummyOwner(), balance);
  }

  private Account dummyReceiverAccount(double balance) {
    return new Account(987654321L, "receiver", dummyBranch(), dummyOwner(), balance);
  }
}
