package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MarriageRegistryTest {

  @Test
  void defaultConstructorInitializesMarriageRegistryWithNoMarriages() {
    MarriageRegistry marriageRegistry = new MarriageRegistry();

    assertEquals(marriageRegistry.getMarriages().size(), 0);
  }

  @Test
  void registeringMarriageRelationshipBetweenClients() {
    MarriageRegistry marriageRegistry = new MarriageRegistry();
    Client client1 = dummyClient1();
    Client client2 = dummyClient2();
    String marriageDate = "10/09/2020";

    marriageRegistry.register(client1, client2, marriageDate);

    assertEquals(marriageRegistry.getMarriages().size(), 1);
    assertTrue(
        marriageRegistry
            .getMarriages()
            .contains(new MarriageRelationship(client1.getDni(), client2.getDni(), marriageDate)));
  }

  @Test
  void registeringMarriageWithNullFirstClientThrowsException() {
    MarriageRegistry marriageRegistry = new MarriageRegistry();
    Client client = dummyClient1();

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> marriageRegistry.register(null, client, "10/09/2020"));

    assertEquals(exception.getMessage(), "Both clients must exist.");
    assertEquals(marriageRegistry.getMarriages().size(), 0);

    exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> marriageRegistry.register(client, null, "10/09/2020"));

    assertEquals(exception.getMessage(), "Both clients must exist.");
    assertEquals(marriageRegistry.getMarriages().size(), 0);
  }

  @Test
  void registeringMarriageBetweenAlreadyMarriedClientsThrowsException() {
    MarriageRegistry marriageRegistry = new MarriageRegistry();
    Client client1 = dummyClient1();
    Client client2 = dummyClient2();
    String marriageDate = "10/09/2020";

    marriageRegistry.register(client1, client2, marriageDate);

    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () -> marriageRegistry.register(client1, client2, marriageDate));

    assertEquals(exception.getMessage(), "These clients are already married.");
    assertEquals(marriageRegistry.getMarriages().size(), 1);
  }

  @Test
  void disolvingExistingMarriageRemovesItFromRegistry() {
    MarriageRegistry marriageRegistry = new MarriageRegistry();
    Client client1 = dummyClient1();
    Client client2 = dummyClient2();
    String marriageDate = "10/09/2020";

    marriageRegistry.register(client1, client2, marriageDate);

    marriageRegistry.disolve(client1, client2);

    assertEquals(marriageRegistry.getMarriages().size(), 0);
  }

  @Test
  void disolvingMarriageWithNullClientsThrowsException() {
    MarriageRegistry marriageRegistry = new MarriageRegistry();
    Client client = dummyClient1();

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> marriageRegistry.disolve(null, client));

    assertEquals(exception.getMessage(), "Both clients must exist.");

    exception =
        assertThrows(IllegalArgumentException.class, () -> marriageRegistry.disolve(client, null));

    assertEquals(exception.getMessage(), "Both clients must exist.");
  }

  @Test
  void disolvingNonExistentMarriageThrowsException() {
    MarriageRegistry marriageRegistry = new MarriageRegistry();
    Client client1 = dummyClient1();
    Client client2 = dummyClient2();

    Exception exception =
        assertThrows(IllegalStateException.class, () -> marriageRegistry.disolve(client1, client2));

    assertEquals(exception.getMessage(), "These clients are not married to each other.");
    assertEquals(marriageRegistry.getMarriages().size(), 0);
  }

  private Client dummyClient1() {
    return new Client(12345678L, "Carlos", "Castillo");
  }

  private Client dummyClient2() {
    return new Client(87654321L, "Lea", "Seydoux");
  }
}
