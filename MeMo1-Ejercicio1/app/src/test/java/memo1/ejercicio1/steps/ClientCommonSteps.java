package memo1.ejercicio1.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import memo1.ejercicio1.Client;

public class ClientCommonSteps {
  private Client client;

  public Client getClient() {
    return client;
  }

  @Before
  public void reset() {
    client = null;
  }

  @Given("A client with DNI {long}, name {string} and surname {string}")
  public void createClient(Long dni, String name, String surname) {
    client = new Client(dni, name, surname);
  }
}
