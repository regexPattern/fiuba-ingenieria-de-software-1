package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ClientRegistryTest {
  @Test
  void aClientRegistryIsCreatedWithNoClients() {
    ClientRegistry clientRegistry = new ClientRegistry();

    assertEquals(clientRegistry.getRegisteredClients().size(), 0);
  }

  @Test
  void registerClientsToAClientRegistry() {
    ClientRegistry clientRegistry = new ClientRegistry();
    Client client1 = new Client(123456789L, "Carlos", "Castillo");
    Client client2 = new Client(987654321L, "Eduardo", "Pereira");

    clientRegistry.registerClient(client1);
    clientRegistry.registerClient(client2);

    ArrayList<Client> registeredClients = clientRegistry.getRegisteredClients();

    assertEquals(registeredClients.size(), 2);
    assertTrue(registeredClients.contains(client1));
    assertTrue(registeredClients.contains(client2));
  }

  @Test
  void tryingToUnregisterAClientThatIsNotYetRegisteredReturnsNull() {
    ClientRegistry clientRegistry = new ClientRegistry();
    long dni = 987654321L;

    clientRegistry.registerClient(new Client(123456789L, "Carlos", "Castillo"));

    assertNull(clientRegistry.unregisterClient(dni));
  }

  @Test
  void unregisteringAClientFromAClientRegistryReturnsTheClient() {
    ClientRegistry clientRegistry = new ClientRegistry();
    Client client = new Client(123456789L, "Carlos", "Castillo");

    clientRegistry.registerClient(client);

    assertEquals(clientRegistry.unregisterClient(client.getDni()), client);
    assertEquals(clientRegistry.getRegisteredClients().size(), 0);
  }

  @Test
  void anAccountCanBeAddedBackToAClientRegistryAfterBeingUnregistered() {
    ClientRegistry clientRegistry = new ClientRegistry();
    Client client = new Client(123456789L, "Carlos", "Castillo");

    clientRegistry.registerClient(client);

    assertEquals(clientRegistry.getRegisteredClients().get(0), client);

    clientRegistry.unregisterClient(client.getDni());

    assertEquals(clientRegistry.getRegisteredClients().size(), 0);

    clientRegistry.registerClient(client);

    assertEquals(clientRegistry.getRegisteredClients().get(0), client);
  }
}
