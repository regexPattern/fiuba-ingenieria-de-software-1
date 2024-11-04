package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientRegistry {
  private HashMap<Long, Client> registeredClients = new HashMap<>();

  public void registerClient(Client client) {
    if (registeredClients.putIfAbsent(client.getDni(), client) != null) {
      throw new IllegalStateException("DNI is already in use by another client.");
    }
  }

  public ArrayList<Client> getRegisteredClients() {
    return new ArrayList<>(registeredClients.values());
  }

  public Client unregisterClient(long dni) {
    return registeredClients.remove(dni);
  }
}
