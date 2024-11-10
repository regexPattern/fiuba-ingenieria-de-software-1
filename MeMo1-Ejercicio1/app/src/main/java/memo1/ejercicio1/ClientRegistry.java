package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientRegistry {

  private HashMap<Long, Client> registeredClients = new HashMap<>();

  public void register(Client client) {
    if (registeredClients.putIfAbsent(client.getDni(), client) != null) {
      throw new IllegalStateException("DNI is already in use by another client.");
    }
  }

  public ArrayList<Client> getClients() {
    return new ArrayList<>(registeredClients.values());
  }

  public Client unregister(
      long dni, AccountRegistry accountRegistry, MarriageRegistry marriageRegistry) {
    Client client = registeredClients.get(dni);

    if (client == null) {
      throw new IllegalStateException("Client not found.");
    }

    for (Account acc : accountRegistry.getAccounts()) {
      if (acc.getOwner().getDni() == dni) {
        throw new IllegalStateException("Cannot delete a client who owns an account.");
      }

      for (Client coOwner : acc.getCoOwners()) {
        if (coOwner.getDni() == dni) {
          throw new IllegalStateException("Cannot delete a client who co-owns an account.");
        }
      }
    }

    for (MarriageRelationship marriage : marriageRegistry.getMarriages()) {
      if (marriage.getClientDnis().contains(dni)) {
        throw new IllegalStateException("Cannot delete a client who is currently married.");
      }
    }

    return registeredClients.remove(dni);
  }
}
