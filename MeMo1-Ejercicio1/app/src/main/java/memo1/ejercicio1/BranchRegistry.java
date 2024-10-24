package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;

public class BranchRegistry {
	private HashMap<Integer, Branch> registeredBranches = new HashMap<>();

	public BranchRegistry() {
	}

	public void registerBranch(Branch branch) {
		if (registeredBranches.putIfAbsent(branch.getCode(), branch) != null) {
			throw new IllegalStateException("Code already in use by another branch.");
		}
	}

	public ArrayList<Branch> getRegisteredBranches() {
		return new ArrayList<>(registeredBranches.values());
	}
}
