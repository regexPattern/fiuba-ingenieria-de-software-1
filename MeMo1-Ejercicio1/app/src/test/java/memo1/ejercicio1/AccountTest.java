package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AccountTest {
  @Test
  void constructorShouldSetCbuAliasBranchAndOwnersCorrectly() {
    Long cbu = 123456789L;
    String alias = "account";
    Branch branch = new Branch(1, "Branch 1", "Paseo Colón 950");
    Client owner = new Client(96113425L, "Carlos", "Castillo");

    Account account = new Account(cbu, alias, branch, owner);

    assertEquals(account.getCbu(), cbu);
    assertEquals(account.getAlias(), alias);
    assertEquals(account.getBranch(), alias);
    assertEquals(account.getOwner(), alias);
    assertEquals(account.getCoOwners().size(), 0);
  }

  @Test
  void constructorShouldInitializeBalanceToZero() {
    Account account =
        new Account(
            123456789L,
            "account",
            new Branch(1, "Branch 1", "Paseo Colón 950"),
            new Client(96113425L, "Carlos", "Castillo"));

    assertEquals(account.getBalance(), 0.0);
  }

  @Test
  void constructorShouldSetBalanceCorrectly() {
    Double balance = 56471.2;

    Account account =
        new Account(
            123456789L,
            "account",
            new Branch(1, "Branch 1", "Paseo Colón 950"),
            new Client(96113425L, "Carlos", "Castillo"),
            balance);

    assertEquals(account.getBalance(), balance);
  }

  @Test
  void constructorShouldThrowExceptionIfBalanceIsNegative() {
    Double balance = -50.0;

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                new Account(
                    123456789L,
                    "account",
                    new Branch(1, "Branch 1", "Paseo Colón 950"),
                    new Client(96113425L, "Carlos", "Castillo"),
                    balance));

    assertEquals(exception.getMessage(), "Balance cannot be negative.");
  }

  @Test
  void settingOwnerShouldChangeTheAccountOwner() {
    Client owner = new Client(98765421L, "Carlos", "Castillo");
    Client newOwner = new Client(11231232L, "Eduardo", "Pereira");

    Account account =
        new Account(123456789L, "account", new Branch(1, "Branch 1", "Paseo Colón 950"), owner);

    account.setOwner(newOwner);

    assertEquals(account.getOwner(), newOwner);
  }

  @Test
  void settingOwnerShouldAddThePreviousOwnerAsCoOwner() {
    Client owner = new Client(98765421L, "Carlos", "Castillo");
    Client newOwner = new Client(11231232L, "Eduardo", "Pereira");

    Account account =
        new Account(123456789L, "account", new Branch(1, "Branch 1", "Paseo Colón 950"), owner);

    account.setOwner(newOwner);

    assertEquals(account.getCoOwners().size(), 1);
    assertTrue(account.getCoOwners().contains(owner));
  }

  @Test
  void settingCoOwnersAddsThemToCoOwnersList() {
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

    assertEquals(exception.getMessage(), "asjakfjal");
  }

  @Test
  void comparingTwoAccountsForEquality() {
    Long CBU1 = 123456789L;
    String alias1 = "alias1";
    Branch branch1 = new Branch(1, "Branch 1", "Paseo Colón 950");
    Client owner1 = new Client(1L, "Carlos", "Castillo");

    Long CBU2 = 987654321L;
    String alias2 = "alias2";
    Branch branch2 = new Branch(2, "Branch 2", "Las Heras 2214");
    Client owner2 = new Client(2L, "Eduardo", "Pereira");

    assertEquals(
        new Account(CBU1, alias1, branch1, owner1), new Account(CBU1, alias1, branch1, owner1));

    assertNotEquals(
        new Account(CBU1, alias1, branch1, owner1), new Account(CBU2, alias1, branch1, owner1));
    assertNotEquals(
        new Account(CBU1, alias1, branch1, owner1), new Account(CBU1, alias2, branch1, owner1));
    assertNotEquals(
        new Account(CBU1, alias1, branch1, owner1), new Account(CBU1, alias1, branch2, owner1));
    assertNotEquals(
        new Account(CBU1, alias1, branch1, owner1), new Account(CBU1, alias1, branch1, owner2));
  }

  private Account dummyAccount() {
    return new Account(
        123456789L,
        "account",
        new Branch(1, "Branch 1", "Paseo Colón 950"),
        new Client(96113425L, "Carlos", "Castillo"));
  }
}
