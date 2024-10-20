package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientRegistry {
	private HashMap<Long, Client> signedUpClients = new HashMap<>();

	public void signUpClient(Client client) {
		if (signedUpClients.putIfAbsent(client.getDni(), client) != null) {
			throw new IllegalStateException("DNI is already in use by another client.");
		}
	}

	public ArrayList<Client> getSignedUpClients() {
		return new ArrayList<>(signedUpClients.values());
	}

	public Client removeClient(Long dni) {
		return signedUpClients.remove(dni);
	}
}
