package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class BranchRegistryTest {
  @Test
  void aBranchRegistryIsCreatedWithNoBranches() {
    BranchRegistry branchRegistry = new BranchRegistry();

    assertEquals(branchRegistry.getRegisteredBranches().size(), 0);
  }

  @Test
  void registerBranchesToABranchRegistry() {
    BranchRegistry branchRegistry = new BranchRegistry();
    Branch branch1 = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");
    Branch branch2 = new Branch(2L, "Suc. Recoleta", "Santa Fe 2000 CABA");

    branchRegistry.register(branch1);
    branchRegistry.register(branch2);

    ArrayList<Branch> registeredBranches = branchRegistry.getRegisteredBranches();

    assertEquals(registeredBranches.size(), 2);
    assertTrue(registeredBranches.contains(branch1));
    assertTrue(registeredBranches.contains(branch2));
  }

  @Test
  void registeringABranchSetsItAsOpened() {
    BranchRegistry branchRegistry = new BranchRegistry();
    Branch branch = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    assertTrue(branch.isOpen());
  }

  @Test
  void registeringABranchWithARepeatedCodeThrowsException() {
    BranchRegistry branchRegistry = new BranchRegistry();

    Branch branch1 = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");
    Branch branch2 = new Branch(2L, "Suc. Recoleta", "Santa Fe 1000 CABA");

    branchRegistry.register(branch1);

    Exception exception =
        assertThrows(IllegalStateException.class, () -> branchRegistry.register(branch2));

    assertEquals(exception.getMessage(), "Code already in use by another branch.");
  }

  @Test
  void closingARegisteredBranchThatIsOpenReturnsTrue() {
    BranchRegistry branchRegistry = new BranchRegistry();
    Branch branch = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    assertTrue(branchRegistry.close(branch.getCode()));
  }

  @Test
  void closingARegisteredBranchThatIsAlreadyClosedReturnsTrue() {
    BranchRegistry branchRegistry = new BranchRegistry();
    Branch branch = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);
    branchRegistry.close(branch.getCode());

    assertFalse(branchRegistry.close(branch.getCode()));
  }

  @Test
  void tryingToCloseABranchThatIsNotYetRegisteredThrowsException() {
    BranchRegistry branchRegistry = new BranchRegistry();

    branchRegistry.register(new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA"));

    Exception exception = assertThrows(IllegalStateException.class, () -> branchRegistry.close(2L));

    assertEquals(exception.getMessage(), "Branch with given code has not been registered.");
  }

  @Test
  void anAccountCanBeReOpenedAfterBeingClosed() {
    BranchRegistry branchRegistry = new BranchRegistry();
    Branch branch = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    branchRegistry.close(branch.getCode());
    branchRegistry.reOpen(branch.getCode());

    assertTrue(branch.isOpen());
  }

  @Test
  void closingAnAlreadyCloseBranchKeepsItClosed() {
    BranchRegistry branchRegistry = new BranchRegistry();
    Branch branch = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    branchRegistry.close(branch.getCode());
    branchRegistry.close(branch.getCode());

    assertFalse(branch.isOpen());
  }

  @Test
  void updatingABranchNameChangeItsName() {
    BranchRegistry branchRegistry = new BranchRegistry();
    Branch branch = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    branchRegistry.updateBranchName(branch.getCode(), "New Name");

    assertEquals(branch.getName(), "New Name");
  }

  @Test
  void updatingANonRegisteredBranchThrowsException() {
    BranchRegistry branchRegistry = new BranchRegistry();

    Exception exception =
        assertThrows(
            IllegalStateException.class, () -> branchRegistry.updateBranchName(900L, "New Name"));

    assertEquals(exception.getMessage(), "Branch with given code has not been registered.");
  }

  @Test
  void updatingABranchNameAllowsAnotherBranchToReuseThePreviousName() {
    BranchRegistry branchRegistry = new BranchRegistry();

    String previousName = "Suc. Belgrano";

    Branch branch1 = new Branch(1L, previousName, "Cabildo 1000 CABA");
    branchRegistry.register(branch1);
    branchRegistry.updateBranchName(branch1.getCode(), "New Name");

    Branch branch2 = new Branch(2L, previousName, "Santa Fe 1000 CABA");
    branchRegistry.register(branch2);

    assertEquals(branchRegistry.getRegisteredBranches().size(), 2);
  }
}
