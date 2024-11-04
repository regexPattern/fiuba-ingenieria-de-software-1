package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BranchTest {
  @Test
  void aBranchIsClosedWhenCreated() {
    Branch branch = new Branch(001, "Suc. Recoleta", "Santa Fe 2000 CABA");

    assertFalse(branch.isOpen());
  }
}
