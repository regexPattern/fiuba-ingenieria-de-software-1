package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashSet;

public class ClientRegistry {
	static private ArrayList<Client> clients = new ArrayList<>();
	static private HashSet<Long> takenDnis = new HashSet<>();

	public void signUpClient(Client client) {
		if (!takenDnis.add(client.getDni())) {
			throw new IllegalStateException("DNI already in use by another client.");
		}

		clients.add(client);
	}

	public ArrayList<Client> getClients() {
		return new ArrayList<>(clients);
	}
}
