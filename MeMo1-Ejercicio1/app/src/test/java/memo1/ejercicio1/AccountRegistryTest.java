package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountRegistryTest {
  @Test
  void accountRegistryIsCreatedWithNoAccounts() {
    AccountRegistry accountRegistry = new AccountRegistry();

    assertEquals(accountRegistry.getAccounts().size(), 0);
  }

  @Test
  void registryShouldThrowExceptionWhenTransferingToNonRegisteredAccount() {
    AccountRegistry accountRegistry = new AccountRegistry();

    Account sender = dummySenderAccount();
    Account receiver = dummyReceiverAccount();

    accountRegistry.register(sender);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountRegistry.transfer(sender.getCbu(), receiver.getCbu(), 20.0));

    assertEquals(exception.getMessage(), "Receiver account has not been registered yet.");

    exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                accountRegistry.transferFromAccountToAccount(sender.getAlias(), "receiver", 20.0));

    assertEquals(exception.getMessage(), "Receiver account does not exist.");
  }

  @Test
  void registryShouldThrowExceptionWhenTransferingFromNonRegisteredAccount() {
    AccountRegistry accountRegistry = new AccountRegistry();

    Account sender = dummySenderAccount();
    Account receiver = dummyReceiverAccount();

    accountRegistry.register(receiver);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountRegistry.transfer(sender.getCbu(), receiver.getCbu(), 20.0));

    assertEquals(exception.getMessage(), "Sender account has not been registered yet.");

    exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                accountRegistry.transferFromAccountToAccount(
                    "receiver", receiver.getAlias(), 20.0));

    assertEquals(exception.getMessage(), "Sender account has not been registered yet.");
  }

  private Branch dummyBranch() {
    return new Branch(1L, "Branch 1", "Paseo Colón 950");
  }

  private Client dummyOwner() {
    return new Client(96113425L, "Carlos", "Castillo");
  }

  private Account dummySenderAccount() {
    return new Account(123456789L, "sender", dummyBranch(), dummyOwner(), 10000.0);
  }

  private Account dummyReceiverAccount() {
    return new Account(987654321L, "receiver", dummyBranch(), dummyOwner(), 10000.0);
  }
}
