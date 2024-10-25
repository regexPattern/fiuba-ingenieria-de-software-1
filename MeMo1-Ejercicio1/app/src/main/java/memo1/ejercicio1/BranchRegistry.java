package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BranchRegistry {
	private HashMap<Integer, Branch> registeredBranches = new HashMap<>();
	private HashSet<String> registeredBranchNames = new HashSet<>();
	private HashSet<String> registeredBranchAddresses = new HashSet<>();

	public void registerBranch(Branch branch) {
		if (registeredBranches.putIfAbsent(branch.getCode(), branch) != null) {
			throw new IllegalStateException("Code already in use by another branch.");
		} else if (registeredBranchNames.contains(branch.getName())) {
			throw new IllegalStateException("Name already in use by another branch.");
		}

		branch.setOpen(true);
		registeredBranchNames.add(branch.getName());
	}

	public ArrayList<Branch> getRegisteredBranches() {
		return new ArrayList<>(registeredBranches.values());
	}

	public boolean reOpenBranch(Integer code) {
		Branch branch = getBranch(code);
		if (branch == null) {
			return false;
		}
		branch.setOpen(true);
		return true;
	}

	public boolean closeBranch(Integer code) {
		Branch branch = getBranch(code);

		if (branch == null) {
			throw new IllegalStateException("Branch with given code has not been registered.");
		}

		if (!branch.isOpen()) {
			return false;
		} else {
			branch.setOpen(false);
			return true;
		}
	}

	public boolean updateBranchName(Integer code, String name) {
		Branch branch = getBranch(code);

		if (branch == null) {
			throw new IllegalStateException("Branch with given code has not been registered.");
		}

		if (!registeredBranchNames.contains(name)) {
			registeredBranchNames.remove(branch.getName());
			branch.setName(name);
			registeredBranchNames.add(name);
		}

		return true;
	}

	public boolean updateBranchAddress(Integer code, String address) {
		Branch branch = getBranch(code);

		if (branch == null) {
			throw new IllegalStateException("Branch with given code has not been registered.");
		}

		if (!registeredBranchNames.contains(address)) {
			branch.setAddress(address);
			registeredBranchAddresses.add(address);
		}

		return true;
	}

	public Branch getBranch(int code) {
		return registeredBranches.get(code);
	}
}
