package memo1.ejercicio1.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import memo1.ejercicio1.Client;
import memo1.ejercicio1.ClientRegistry;

public class ClientCommonSteps {

  private Client client;
  private ClientRegistry clientRegistry;

  public Client getClient() {
    return client;
  }

  public ClientRegistry getClientRegistry() {
    return clientRegistry;
  }

  @Before
  public void reset() {
    client = null;
    clientRegistry = new ClientRegistry();
  }

  @Given("A client with DNI {long}, name {string} and surname {string}")
  public void createClient(long dni, String name, String surname) {
    client = new Client(dni, name, surname);
    clientRegistry.register(client);
  }
}
