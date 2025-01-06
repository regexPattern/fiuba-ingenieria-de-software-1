package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashSet;

public class MarriageRegistry {

  private HashSet<MarriageRelationship> marriages = new HashSet<>();

  public void register(Client client1, Client client2, String marriageDateString) {
    if (client1 == null || client2 == null) {
      throw new IllegalArgumentException("Both clients must exist.");
    }

    MarriageRelationship relationship =
        new MarriageRelationship(client1.getDni(), client2.getDni(), marriageDateString);

    if (marriages.contains(relationship)) {
      throw new IllegalStateException("These clients are already married.");
    }

    marriages.add(relationship);
  }

  public void disolve(Client client1, Client client2) {
    if (client1 == null || client2 == null) {
      throw new IllegalArgumentException("Both clients must exist.");
    }

    MarriageRelationship relationship = findMarriage(client1.getDni(), client2.getDni());

    if (relationship == null) {
      throw new IllegalStateException("These clients are not married to each other.");
    }

    marriages.remove(relationship);
  }

  public ArrayList<MarriageRelationship> getMarriages() {
    return new ArrayList<>(marriages);
  }

  private MarriageRelationship findMarriage(long dni1, long dni2) {
    return marriages.stream().filter(m -> m.involvesClients(dni1, dni2)).findFirst().orElse(null);
  }
}
