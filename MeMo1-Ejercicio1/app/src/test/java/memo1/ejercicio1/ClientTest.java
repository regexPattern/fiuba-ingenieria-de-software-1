package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

class ClientTest {

  @Test
  void constructorShouldSetBirthDateCorrectly() {
    String birthDateString = "10/09/2001";

    Client client =
        new Client(123456789L, "Carlos", "Castillo", birthDateString, "Paseo Colón 950");

    LocalDate expectedBirthDate = LocalDate.parse(birthDateString, Client.dateFormatter);

    assertEquals(client.getBirthDate(), expectedBirthDate);
  }

  @Test
  void constructorShouldThrowExceptionIfBirthDateFormatIsNotAValidDate() {
    long dni = 123456789L;
    String name = "Carlos";
    String surName = "Castillo";
    String address = "Paseo Colón 950";

    String invalidDateFormatDateString = "2004/10/01";
    String invalidDateString = "A/B/2001";

    assertThrows(
        DateTimeParseException.class,
        () -> new Client(dni, name, surName, invalidDateFormatDateString, address));
    assertThrows(
        DateTimeParseException.class,
        () -> new Client(dni, name, surName, invalidDateString, address));
  }

  @Test
  void constructorShouldThrowExceptionIfBirthDateIsAFutureDate() {
    LocalDate todayDate = LocalDate.now();
    LocalDate futureDate = todayDate.plusDays(1);
    String futureDateString = futureDate.format(Client.dateFormatter);

    Exception exception =
        assertThrows(
            IllegalArgumentException.class, () -> new Client(0L, "", "", futureDateString, ""));

    assertEquals(exception.getMessage(), "Birth date cannot be a future date.");
  }

  @Test
  void settingAClientAsMarriedToAnotherSetsBothClientsPartners() {
    Client client1 = new Client(123456789L, "Carlos", "Castillo");
    Client client2 = new Client(987654121L, "Lea", "Seydoux");

    client1.setPartner(client2, "12/12/1999");

    assertEquals(client1.getPartner(), client2);
    assertEquals(client2.getPartner(), client1);
  }

  @Test
  void settingAClientsPartnerReturnsTheMarriageDate() {
    Client client1 = new Client(123456789L, "Carlos", "Castillo");
    Client client2 = new Client(987654121L, "Lea", "Seydoux");

    String marriageDateString = "12/12/1999";

    LocalDate expectedMarriageDate = LocalDate.parse(marriageDateString, Client.dateFormatter);
    LocalDate marriageDate = client1.setPartner(client2, marriageDateString);

    assertEquals(marriageDate, expectedMarriageDate);
  }

  @Test
  public void updatingClient() {
    Client client = new Client(123456789, "Carlos", "Castillo", "10/09/2001", "Paseo Colón 950");

    client.update("Carlos Alberto", "Castillo Ruiz", "10/09/1980", "Av. Las Heras 2214");

    assertEquals(client.getName(), "Carlos Alberto");
    assertEquals(client.getSurName(), "Castillo Ruiz");
    assertEquals(client.getBirthDate().format(Client.dateFormatter), "10/09/1980");
    assertEquals(client.getAddress(), "Av. Las Heras 2214");
    assertEquals(client.getDni(), 123456789);
  }

  @Test
  public void updatingClientPartially() {
    Client client = new Client(123456789, "Carlos", "Castillo", "10/09/2001", "Paseo Colón 950");

    client.update("Carlos Alberto", null, null, null);
    assertEquals(client.getName(), "Carlos Alberto");

    client.update(null, "Castillo Ruiz", null, null);
    assertEquals(client.getName(), "Carlos Alberto");

    client.update(null, null, "11/09/2001", null);
    assertEquals(client.getBirthDate().format(Client.dateFormatter), "11/09/2001");

    client.update(null, null, null, "Av. Las Heras 2214");
    assertEquals(client.getAddress(), "Av. Las Heras 2214");
  }

  @Test
  public void updatingClientWithFutureBirthDateThrowsException() {
    Client client = new Client(123456789, "Carlos", "Castillo", "10/09/2001", "Paseo Colón 950");

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              LocalDate futureDate = LocalDate.now().plusYears(2);
              client.update(null, null, futureDate.format(Client.dateFormatter), null);
            });

    assertEquals(exception.getMessage(), "Birth date cannot be a future date.");
    assertEquals("10/09/2001", client.getBirthDate().format(Client.dateFormatter));
  }

  @Test
  public void updatingClientWithEmptyValuesLeavesItUnchanged() {
    Client client = new Client(123456789, "Carlos", "Castillo", "10/09/2001", "Paseo Colón 950");

    client.update("", "", "", "");

    assertEquals(client.getName(), "Carlos");
    assertEquals(client.getSurName(), "Castillo");
    assertEquals(client.getBirthDate().format(Client.dateFormatter), "10/09/2001");
    assertEquals(client.getAddress(), "Paseo Colón 950");
  }
}
