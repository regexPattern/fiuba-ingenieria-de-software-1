package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AccountTest {
  @Test
  void constructorSetsCbuAliasBranchAndOwnersCorrectly() {
    long cbu = 123456789L;
    String alias = "account";
    Branch branch = new Branch(1L, "Branch 1", "Paseo Colón 950");
    Client owner = new Client(96113425L, "Carlos", "Castillo");

    Account account = new Account(cbu, alias, branch, owner);

    assertEquals(account.getCbu(), cbu);
    assertEquals(account.getAlias(), alias);
    assertEquals(account.getBranch(), branch);
    assertEquals(account.getOwner(), owner);
    assertEquals(account.getCoOwners().size(), 0);
  }

  @Test
  void constructorInitializesBalanceToZero() {
    Account account =
        new Account(
            123456789L,
            "account",
            new Branch(1L, "Branch 1", "Paseo Colón 950"),
            new Client(96113425L, "Carlos", "Castillo"));

    assertEquals(account.getBalance(), 0.0);
  }

  @Test
  void constructorSetsBalanceCorrectly() {
    Double balance = 56471.2;

    Account account =
        new Account(
            123456789L,
            "account",
            new Branch(1L, "Branch 1", "Paseo Colón 950"),
            new Client(96113425L, "Carlos", "Castillo"),
            balance);

    assertEquals(account.getBalance(), balance);
  }

  @Test
  void constructorThrowsExceptionIfAliasIsNull() {
    String alias = null;

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                new Account(
                    123456789L,
                    alias,
                    new Branch(1L, "Branch 1", "Paseo Colón 950"),
                    new Client(96113425L, "Carlos", "Castillo")));

    assertEquals(exception.getMessage(), "Alias cannot be null.");
  }

  @Test
  void constructorThrowsExceptionIfBranchIsNull() {
    Branch branch = null;

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                new Account(
                    123456789L, "account", branch, new Client(96113425L, "Carlos", "Castillo")));

    assertEquals(exception.getMessage(), "Branch cannot be null.");
  }

  @Test
  void constructorThrowsExceptionIfBranchIsClosed() {
    Branch branch = new Branch(1L, "Branch 1", "Paseo Colón 950");

    branch.setOpen(false);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                new Account(
                    123456789L, "account", branch, new Client(96113425L, "Carlos", "Castillo")));

    assertEquals(exception.getMessage(), "Branch cannot be null.");
  }

  @Test
  void constructorThrowsExceptionIfOwnerIsNull() {
    Client owner = null;

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                new Account(
                    123456789L, "account", new Branch(1L, "Branch 1", "Paseo Colón 950"), owner));

    assertEquals(exception.getMessage(), "Owner cannot be null.");
  }

  @Test
  void constructorThrowsExceptionIfBalanceIsNegative() {
    Double balance = -50.0;

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                new Account(
                    123456789L,
                    "account",
                    new Branch(1L, "Branch 1", "Paseo Colón 950"),
                    new Client(96113425L, "Carlos", "Castillo"),
                    balance));

    assertEquals(exception.getMessage(), "Balance cannot be negative.");
  }

  @Test
  void settingNewOwnerChangesTheAccountOwner() {
    Client owner = new Client(98765421L, "Carlos", "Castillo");
    Client newOwner = new Client(11231232L, "Eduardo", "Pereira");

    Account account =
        new Account(123456789L, "account", new Branch(1L, "Branch 1", "Paseo Colón 950"), owner);

    account.setOwner(newOwner);

    assertEquals(account.getOwner(), newOwner);
  }

  @Test
  void settingNewOwnerAddsThePreviousOwnerAsCoOwner() {
    Client owner = new Client(98765421L, "Carlos", "Castillo");
    Client newOwner = new Client(11231232L, "Eduardo", "Pereira");

    Account account =
        new Account(123456789L, "account", new Branch(1L, "Branch 1", "Paseo Colón 950"), owner);

    account.setOwner(newOwner);

    assertEquals(account.getCoOwners().size(), 1);
    assertTrue(account.getCoOwners().contains(owner));
  }

  @Test
  void settingNullOwnerThrowsException() {
    Account account = dummyAccount();

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> account.setOwner(null));

    assertEquals(exception.getMessage(), "New owner cannot be null.");
  }

  @Test
  void settingNullCoOwnerThrowsException() {
    Account account = dummyAccount();

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> account.setCoOwner(null));

    assertEquals(exception.getMessage(), "New co-owner cannot be null.");
  }

  @Test
  void settingNewCoOwnersAddsThemToCoOwnersList() {
    Account account = dummyAccount();

    Client client1 = new Client(98765421L, "Wendollin", "Hernández");
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

  @Test
  void settingAlreadySetCoOwnerLeavesCoOwnersListTheSame() {
    Account account = dummyAccount();

    Client coOwner = new Client(98765421L, "Wendollin", "Hernández");

    account.setCoOwner(coOwner);
    account.setCoOwner(coOwner);

    assertEquals(account.getCoOwners().size(), 1);
  }

  @Test
  void settingOwnerAsCoOwnerThrowsException() {
    Account account = dummyAccount();

    Exception exception =
        assertThrows(IllegalStateException.class, () -> account.setCoOwner(account.getOwner()));

    assertEquals(exception.getMessage(), "Account owner cannot be set as co-owner.");
  }

  @Test
  void comparingTwoAccountsForEquality() {
    long Cbu1 = 123456789L;
    String alias1 = "alias1";
    Branch branch1 = new Branch(1L, "Branch 1", "Paseo Colón 950");
    Client owner1 = new Client(1L, "Carlos", "Castillo");

    long Cbu2 = 987654321L;
    String alias2 = "alias2";
    Branch branch2 = new Branch(2L, "Branch 2", "Las Heras 2214");
    Client owner2 = new Client(2L, "Eduardo", "Pereira");

    assertEquals(
        new Account(Cbu1, alias1, branch1, owner1), new Account(Cbu1, alias1, branch1, owner1));

    assertNotEquals(
        new Account(Cbu1, alias1, branch1, owner1), new Account(Cbu2, alias1, branch1, owner1));
    assertNotEquals(
        new Account(Cbu1, alias1, branch1, owner1), new Account(Cbu1, alias2, branch1, owner1));
    assertNotEquals(
        new Account(Cbu1, alias1, branch1, owner1), new Account(Cbu1, alias1, branch2, owner1));
    assertNotEquals(
        new Account(Cbu1, alias1, branch1, owner1), new Account(Cbu1, alias1, branch1, owner2));
  }

  private Account dummyAccount() {
    return new Account(
        123456789L,
        "account",
        new Branch(1L, "Branch 1", "Paseo Colón 950"),
        new Client(96113425L, "Carlos", "Castillo"));
  }
}
