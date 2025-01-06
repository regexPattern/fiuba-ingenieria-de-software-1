package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ClientRegistryTest {

  @Test
  void clientRegistryIsCreatedWithNoClients() {
    ClientRegistry clientRegistry = new ClientRegistry();

    assertEquals(clientRegistry.getClients().size(), 0);
  }

  @Test
  void registerClientsToClientRegistry() {
    ClientRegistry clientRegistry = new ClientRegistry();
    Client client1 = new Client(123456789L, "Carlos", "Castillo");
    Client client2 = new Client(987654321L, "Eduardo", "Pereira");

    clientRegistry.register(client1);
    clientRegistry.register(client2);

    ArrayList<Client> registeredClients = clientRegistry.getClients();

    assertEquals(registeredClients.size(), 2);
    assertTrue(registeredClients.contains(client1));
    assertTrue(registeredClients.contains(client2));
  }

  @Test
  void unregisteringNonExistentClientThrowsException() {
    ClientRegistry clientRegistry = new ClientRegistry();
    AccountRegistry accountRegistry = new AccountRegistry();
    MarriageRegistry marriageRegistry = new MarriageRegistry();

    long dni = 987654321L;

    clientRegistry.register(new Client(123456789L, "Carlos", "Castillo"));

    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () -> clientRegistry.unregister(dni, accountRegistry, marriageRegistry));

    assertEquals(exception.getMessage(), "Client not found.");
  }

  @Test
  void unregisteringClientFromAClientRegistryReturnsTheClient() {
    ClientRegistry clientRegistry = new ClientRegistry();
    AccountRegistry accountRegistry = new AccountRegistry();
    MarriageRegistry marriageRegistry = new MarriageRegistry();

    Client client = new Client(123456789L, "Carlos", "Castillo");

    clientRegistry.register(client);

    assertEquals(
        clientRegistry.unregister(client.getDni(), accountRegistry, marriageRegistry), client);
    assertEquals(clientRegistry.getClients().size(), 0);
  }

  @Test
  void accountCanBeAddedBackToAClientRegistryAfterBeingUnregistered() {
    ClientRegistry clientRegistry = new ClientRegistry();
    AccountRegistry accountRegistry = new AccountRegistry();
    MarriageRegistry marriageRegistry = new MarriageRegistry();

    Client client = new Client(123456789L, "Carlos", "Castillo");

    clientRegistry.register(client);

    assertEquals(clientRegistry.getClients().get(0), client);

    clientRegistry.unregister(client.getDni(), accountRegistry, marriageRegistry);

    assertEquals(clientRegistry.getClients().size(), 0);

    clientRegistry.register(client);

    assertEquals(clientRegistry.getClients().get(0), client);
  }

  @Test
  void unregisteringMarriedClientThrowsException() {
    ClientRegistry clientRegistry = new ClientRegistry();
    AccountRegistry accountRegistry = new AccountRegistry();
    MarriageRegistry marriageRegistry = new MarriageRegistry();

    Client client1 = new Client(123456789L, "Carlos", "Castillo");
    Client client2 = new Client(987654321L, "Maria", "Rodriguez");

    clientRegistry.register(client1);
    clientRegistry.register(client2);

    marriageRegistry.register(client1, client2, "01/01/2023");

    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () -> clientRegistry.unregister(client1.getDni(), accountRegistry, marriageRegistry));

    assertEquals("Cannot delete a client who is currently married.", exception.getMessage());
  }

  @Test
  void unregisteringDivorcedClientSucceeds() {
    ClientRegistry clientRegistry = new ClientRegistry();
    AccountRegistry accountRegistry = new AccountRegistry();
    MarriageRegistry marriageRegistry = new MarriageRegistry();

    Client client1 = new Client(123456789L, "Carlos", "Castillo");
    Client client2 = new Client(987654321L, "Maria", "Rodriguez");

    clientRegistry.register(client1);
    clientRegistry.register(client2);

    marriageRegistry.register(client1, client2, "01/01/2023");
    marriageRegistry.disolve(client1, client2);

    Client unregisteredClient =
        clientRegistry.unregister(client1.getDni(), accountRegistry, marriageRegistry);

    assertEquals(client1, unregisteredClient);
    assertEquals(1, clientRegistry.getClients().size());
  }
}
