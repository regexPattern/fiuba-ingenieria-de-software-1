package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BranchTest {
  @Test
  void constructorOpensTheBranch() {
    Branch branch = new Branch(1L, "Suc. Recoleta", "Santa Fe 2000 CABA");

    assertTrue(branch.getOpen());
  }
}
