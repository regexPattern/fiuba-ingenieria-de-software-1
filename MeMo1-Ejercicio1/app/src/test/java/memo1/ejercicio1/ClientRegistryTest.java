package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ClientRegistryTest {
	@Test
	void aClientRegistryIsCreatedWithNoClients() {
		ClientRegistry clientRegistry = new ClientRegistry();

		assertEquals(clientRegistry.getSignedUpClients().size(), 0);
	}

	@Test
	void signInUpClientsToAClientRegistry() {
		ClientRegistry clientRegistry = new ClientRegistry();
		Client client1 = new Client(123456789L, "Carlos", "Castillo");
		Client client2 = new Client(987654321L, "Eduardo", "Pereira");

		clientRegistry.signUpClient(client1);
		clientRegistry.signUpClient(client2);

		ArrayList<Client> registeredClients = clientRegistry.getSignedUpClients();

		assertEquals(registeredClients.size(), 2);
		assertTrue(registeredClients.contains(client1));
		assertTrue(registeredClients.contains(client2));
	}

	@Test
	void tryingToRemoveAClientThatIsNotYetRegisteredReturnsNull() {
		ClientRegistry clientRegistry = new ClientRegistry();
		Long dni = 987654321L;

		clientRegistry.signUpClient(new Client(123456789L, "Carlos", "Castillo"));

		assertNull(clientRegistry.removeClient(dni));
	}

	@Test
	void removingAClientFromAClientRegistryReturnsTheClient() {
		ClientRegistry clientRegistry = new ClientRegistry();
		Client client = new Client(123456789L, "Carlos", "Castillo");

		clientRegistry.signUpClient(client);

		assertEquals(clientRegistry.removeClient(client.getDni()), client);
		assertEquals(clientRegistry.getSignedUpClients().size(), 0);
	}

	@Test
	void anAccountCanBeAddedBackToAClientRegistryAfterBeingRemoved() {
		ClientRegistry clientRegistry = new ClientRegistry();
		Client client = new Client(123456789L, "Carlos", "Castillo");

		clientRegistry.signUpClient(client);

		assertEquals(clientRegistry.getSignedUpClients().get(0), client);

		clientRegistry.removeClient(client.getDni());

		assertEquals(clientRegistry.getSignedUpClients().size(), 0);

		clientRegistry.signUpClient(client);

		assertEquals(clientRegistry.getSignedUpClients().get(0), client);
	}
}
