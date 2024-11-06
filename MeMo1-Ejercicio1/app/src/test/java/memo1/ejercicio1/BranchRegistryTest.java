package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class BranchRegistryTest {
  @Test
  void constructorInitializesBranchRegistryWithNoBranches() {
    BranchRegistry branchRegistry = new BranchRegistry();

    assertEquals(branchRegistry.getBranchCodes().size(), 0);
  }

  @Test
  void registeringBranchesToBranchRegistry() {
    BranchRegistry branchRegistry = new BranchRegistry();

    Branch branch1 = new Branch(17512312L, "Suc. Belgrano", "Cabildo 1000 CABA");
    Branch branch2 = new Branch(24123129L, "Suc. Recoleta", "Santa Fe 2000 CABA");

    branchRegistry.register(branch1);
    branchRegistry.register(branch2);

    ArrayList<Branch> registeredBranches = branchRegistry.getBranchCodes();

    assertEquals(registeredBranches.size(), 2);
    assertTrue(registeredBranches.contains(branch1));
    assertTrue(registeredBranches.contains(branch2));
  }

  @Test
  void registeringBranchSetsItAsOpened() {
    BranchRegistry branchRegistry = new BranchRegistry();

    Branch branch = new Branch(17512312L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    assertTrue(branch.getOpen());
  }

  @Test
  void registeringBranchWithRepeatedCodeThrowsException() {
    BranchRegistry branchRegistry = new BranchRegistry();

    Branch branch = new Branch(17512312L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () ->
                branchRegistry.register(
                    new Branch(branch.getCode(), "Suc. Recoleta", "Santa Fe 1000 CABA")));

    assertEquals(exception.getMessage(), "Code already in use by another branch.");
  }

  @Test
  void closingBranchThatIsOpenReturnsTrue() {
    BranchRegistry branchRegistry = new BranchRegistry();

    Branch branch = new Branch(17512312L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    assertTrue(branchRegistry.close(branch.getCode()));
  }

  @Test
  void closingBranchThatIsAlreadyClosedReturnsTrue() {
    BranchRegistry branchRegistry = new BranchRegistry();

    Branch branch = new Branch(17512312L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);
    branchRegistry.close(branch.getCode());

    assertFalse(branchRegistry.close(branch.getCode()));
  }

  @Test
  void closingBranchThatIsNotYetRegisteredThrowsException() {
    BranchRegistry branchRegistry = new BranchRegistry();

    Exception exception =
        assertThrows(IllegalStateException.class, () -> branchRegistry.close(17512312L));

    assertEquals(exception.getMessage(), "Branch with given code has not been registered.");
  }

  @Test
  void accountCanBeReOpenedAfterBeingClosed() {
    BranchRegistry branchRegistry = new BranchRegistry();
    Branch branch = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    branchRegistry.close(branch.getCode());
    branchRegistry.reOpen(branch.getCode());

    assertTrue(branch.getOpen());
  }

  @Test
  void closingAnAlreadyCloseBranchKeepsItClosed() {
    BranchRegistry branchRegistry = new BranchRegistry();
    Branch branch = new Branch(1L, "Suc. Belgrano", "Cabildo 1000 CABA");

    branchRegistry.register(branch);

    branchRegistry.close(branch.getCode());
    branchRegistry.close(branch.getCode());

    assertFalse(branch.getOpen());
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
  void updatingNonRegisteredBranchThrowsException() {
    BranchRegistry branchRegistry = new BranchRegistry();

    Exception exception =
        assertThrows(
            IllegalStateException.class, () -> branchRegistry.updateBranchName(900L, "New Name"));

    assertEquals(exception.getMessage(), "Branch with given code has not been registered.");
  }

  @Test
  void updatingBranchNameAllowsAnotherBranchToReuseThePreviousName() {
    BranchRegistry branchRegistry = new BranchRegistry();

    String previousName = "Suc. Belgrano";

    Branch branch1 = new Branch(1L, previousName, "Cabildo 1000 CABA");
    branchRegistry.register(branch1);
    branchRegistry.updateBranchName(branch1.getCode(), "New Name");

    Branch branch2 = new Branch(2L, previousName, "Santa Fe 1000 CABA");
    branchRegistry.register(branch2);

    assertEquals(branchRegistry.getBranchCodes().size(), 2);
  }
}
