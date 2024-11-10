package memo1.ejercicio1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MarriageRelationship {

  private long client1Dni;
  private long client2Dni;
  private LocalDate marriageDate;

  public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public MarriageRelationship(long client1Dni, long client2Dni, String marriageDateString) {
    if (client1Dni == client2Dni) {
      throw new IllegalArgumentException("Client cannot be married to itself.");
    }

    this.client1Dni = client1Dni;
    this.client2Dni = client2Dni;

    LocalDate marriageDate = LocalDate.parse(marriageDateString, dateFormatter);

    if (marriageDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Marriage date cannot be a future date.");
    }

    this.marriageDate = marriageDate;
  }

  public ArrayList<Long> getClientDnis() {
    ArrayList<Long> dnis = new ArrayList<>();
    dnis.add(client1Dni);
    dnis.add(client2Dni);
    return dnis;
  }

  public LocalDate getMarriageDate() {
    return marriageDate;
  }

  public boolean involvesClients(long dni1, long dni2) {
    return (client1Dni == dni1 && client2Dni == dni2) || (client1Dni == dni2 && client2Dni == dni1);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof MarriageRelationship)) {
      return false;
    }

    MarriageRelationship other = (MarriageRelationship) obj;

    return ((this.client1Dni == other.client1Dni && this.client2Dni == other.client2Dni)
        || (this.client1Dni == other.client2Dni && this.client2Dni == other.client1Dni));
  }

  @Override
  public int hashCode() {
    return String.format("%d,%d", client1Dni, client2Dni).hashCode();
  }
}
