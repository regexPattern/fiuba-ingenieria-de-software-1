package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class AccountRegistryTest {
  @Test
  void defaultConstructorInitializesAccountRegistryWithNoAccounts() {
    AccountRegistry accountRegistry = new AccountRegistry();

    assertEquals(accountRegistry.getAccounts().size(), 0);
  }

  @Test
  void registeringAccountsToAnAccountRegistry() {
    AccountRegistry accountRegistry = new AccountRegistry();

    Account account1 = new Account(123456789L, "account1", dummyBranch(), dummyOwner());
    Account account2 = new Account(711312321L, "account2", dummyBranch(), dummyOwner());

    accountRegistry.register(account1);
    accountRegistry.register(account2);

    ArrayList<Account> registeredAccounts = accountRegistry.getAccounts();

    assertEquals(registeredAccounts.size(), 2);
    assertTrue(registeredAccounts.contains(account1));
    assertTrue(registeredAccounts.contains(account2));
  }

  @Test
  void registeringAccountWithRepeatedCbuThrowsException() {
    AccountRegistry accountRegistry = new AccountRegistry();

    Account account = new Account(123456789L, "account1", dummyBranch(), dummyOwner());

    accountRegistry.register(account);

    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () ->
                accountRegistry.register(
                    new Account(account.getCbu(), "account2", dummyBranch(), dummyOwner())));

    assertEquals(exception.getMessage(), "CBU already in use by another account.");
  }

  @Test
  void registeringAccountWithRepeatedAliasThrowsException() {
    AccountRegistry accountRegistry = new AccountRegistry();

    Account account = new Account(123456789L, "account1", dummyBranch(), dummyOwner());

    accountRegistry.register(account);

    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () ->
                accountRegistry.register(
                    new Account(85712312L, account.getAlias(), dummyBranch(), dummyOwner())));

    assertEquals(exception.getMessage(), "Alias already in use by another account.");
  }

  @Test
  void transferingToNonRegisteredAccountThrowsException() {
    AccountRegistry accountRegistry = new AccountRegistry();

    Account sender = dummySenderAccount();
    Account receiver = dummyReceiverAccount();

    accountRegistry.register(sender);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountRegistry.transfer(sender.getCbu(), receiver.getCbu(), 20.0));

    String expectedMsg = "Receiver account has not been registered yet.";

    assertEquals(exception.getMessage(), expectedMsg);

    exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountRegistry.transfer(sender.getAlias(), receiver.getAlias(), 20.0));

    assertEquals(exception.getMessage(), expectedMsg);

    exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountRegistry.transfer(sender.getAlias(), null, 20.0));

    assertEquals(exception.getMessage(), expectedMsg);
  }

  @Test
  void transferingFromNonRegisteredAccountThrowsException() {
    AccountRegistry accountRegistry = new AccountRegistry();

    Account sender = dummySenderAccount();
    Account receiver = dummyReceiverAccount();

    accountRegistry.register(receiver);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountRegistry.transfer(sender.getCbu(), receiver.getCbu(), 20.0));

    String expectedMsg = "Sender account has not been registered yet.";

    assertEquals(exception.getMessage(), expectedMsg);

    exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountRegistry.transfer(sender.getAlias(), receiver.getAlias(), 20.0));

    assertEquals(exception.getMessage(), expectedMsg);

    exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountRegistry.transfer(null, receiver.getAlias(), 20.0));

    assertEquals(exception.getMessage(), expectedMsg);
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
