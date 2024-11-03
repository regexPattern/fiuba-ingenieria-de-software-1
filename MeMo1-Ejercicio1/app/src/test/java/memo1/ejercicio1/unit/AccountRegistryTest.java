package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountRegistryTest {
  @Test
  void anAccountRegistryIsCreatedWithNoAccounts() {
    AccountRegistry accountRegistry = new AccountRegistry();

    assertEquals(accountRegistry.getRegisteredAccounts().size(), 0);
  }

  @Test
  void registryShouldThrowExceptionWhenTransferingToNonExcitingAccount() {
    AccountRegistry accountRegistry = new AccountRegistry();
    Account sender = new Account(123456789L, "sender", 100.0);

    accountRegistry.registerAccount(sender, dummyBranch());

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> accountRegistry.transferFromAccountToAccount(sender.getCbu(), 987654321L, 20.0));

    assertEquals(exception.getMessage(), "Receiver account does not exist.");

    exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                accountRegistry.transferFromAccountToAccount(sender.getAlias(), "receiver", 20.0));

    assertEquals(exception.getMessage(), "Receiver account does not exist.");
  }

  @Test
  void registryShouldThrowExceptionWhenTransferingFromNonExcitingAccount() {
    AccountRegistry accountRegistry = new AccountRegistry();
    Account receiver = new Account(987654321L, "sender", 100.0);

    accountRegistry.registerAccount(receiver, dummyBranch());

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                accountRegistry.transferFromAccountToAccount(123456789L, receiver.getCbu(), 20.0));

    assertEquals(exception.getMessage(), "Sender account does not exist.");

    exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                accountRegistry.transferFromAccountToAccount(
                    "receiver", receiver.getAlias(), 20.0));

    assertEquals(exception.getMessage(), "Sender account does not exist.");
  }

  @Test
  void branchGetsAddedToAccountWhenItIsRegistered() {
    AccountRegistry accountRegistry = new AccountRegistry();
    Account account = new Account(123456789L, "account");
    Branch branch = new Branch(001, "Suc. Belgrano", "Cabildo 9000 CABA");

    accountRegistry.registerAccount(account, branch);

    assertEquals(account.getBranch(), branch);
  }

  @Test
  void settingAnOwnerWhenAnAccountAlreadyHasOneThrowsException() {
    Account account = new Account(123456789L, "account");
    Client client1 = new Client(98765421L, "Carlos", "Castillo");
    Client client2 = new Client(11231232L, "Eduardo", "Pereira");

    account.setOwner(client1);

    Exception exception =
        assertThrows(IllegalStateException.class, () -> account.setOwner(client2));

    assertEquals(exception.getMessage(), "Cannot assign multiple owners.");
  }

  @Test
  void settingOwnerAlsoAsCoOwnerThrowsException() {
    Account account = new Account(123456789L, "account");
    Client owner = new Client(98765421L, "Carlos", "Castillo");

    account.setOwner(owner);

    Exception exception =
        assertThrows(IllegalStateException.class, () -> account.setCoOwner(owner));

    assertEquals(exception.getMessage(), "Account owner cannot be set as co-owner.");
  }

  @Test
  void settingsCoOwnersAddsThemToTheCoOwnersList() {
    Account account = new Account(123456789L, "account");

    Client client1 = new Client(98765421L, "Carlos", "Castillo");
    Client client2 = new Client(11231232L, "Eduardo", "Pereira");
    Client client3 = new Client(67584921L, "Flavio", "Castillo");

    account.setCoOwner(client1);
    account.setCoOwner(client2);
    account.setCoOwner(client3);

    assertEquals(account.getCoOwners().size(), 3);
    assertTrue(account.getCoOwners().contains(client1));
    assertTrue(account.getCoOwners().contains(client2));
    assertTrue(account.getCoOwners().contains(client3));
  }

  private static Branch dummyBranch() {
    return new Branch(001, "Suc. Belgrano", "Cabildo 1000 CABA");
  }
}
