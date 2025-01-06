package memo1.ejercicio1;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Test;

class MarriageRelationshipTest {

  @Test
  void constructorSetsClientDnisAndMarriageDateCorrectly() {
    long client1Dni = 12345678L;
    long client2Dni = 87654321L;
    String marriageDate = "10/09/2020";

    MarriageRelationship relationship =
        new MarriageRelationship(client1Dni, client2Dni, marriageDate);

    ArrayList<Long> dnis = relationship.getClientDnis();

    assertEquals(dnis.size(), 2);
    assertTrue(dnis.contains(client1Dni));
    assertTrue(dnis.contains(client2Dni));
    assertEquals(
        relationship.getMarriageDate(), LocalDate.parse(marriageDate, Client.dateFormatter));
  }

  @Test
  void constructorThrowsExceptionIfClientMarriesItself() {
    long clientDni = 12345678L;

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> new MarriageRelationship(clientDni, clientDni, "10/09/2020"));

    assertEquals(exception.getMessage(), "Client cannot be married to itself.");
  }

  @Test
  void constructorThrowsExceptionIfMarriageDateIsInTheFuture() {
    long client1Dni = 12345678L;
    long client2Dni = 87654321L;
    String futureDate = "10/09/2025";

    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> new MarriageRelationship(client1Dni, client2Dni, futureDate));

    assertEquals(exception.getMessage(), "Marriage date cannot be a future date.");
  }

  @Test
  void involvesClientsReturnsTrueForMatchingClientsInAnyOrder() {
    long client1Dni = 12345678L;
    long client2Dni = 87654321L;
    MarriageRelationship relationship =
        new MarriageRelationship(client1Dni, client2Dni, "10/09/2020");

    assertTrue(relationship.involvesClients(client1Dni, client2Dni));
    assertTrue(relationship.involvesClients(client2Dni, client1Dni));
  }

  @Test
  void involvesClientsReturnsFalseForNonMatchingClients() {
    MarriageRelationship relationship =
        new MarriageRelationship(12345678L, 87654321L, "10/09/2020");

    assertFalse(relationship.involvesClients(11111111L, 22222222L));
  }
}
