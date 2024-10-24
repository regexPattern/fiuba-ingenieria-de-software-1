package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BranchRegistryTest {
	@Test
	void aBranchRegistryIsCreatedWithNoBranches() {
		BranchRegistry branchRegistry = new BranchRegistry();

		assertEquals(branchRegistry.getRegisteredBranches().size(), 0);
	}
}
