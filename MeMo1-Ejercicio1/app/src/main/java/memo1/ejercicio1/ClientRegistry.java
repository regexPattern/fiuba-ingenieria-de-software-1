package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashSet;

public class ClientRegistry {
	static private HashSet<Client> clients = new HashSet<>();
	static private HashSet<Long> takenDnis = new HashSet<>();

	public boolean signUp(Client client) {
		if (!takenDnis.add(client.getDni())) {
			throw new IllegalStateException("DNI already in use by another client.");
		}

		clients.add(client);
		return true;
	}

	public ArrayList<Client> getClients() {
		return new ArrayList<>(clients);
	}
}
