package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BranchRegistry {
  private HashMap<Long, Branch> branches = new HashMap<>();
  private HashSet<String> usedBranchNames = new HashSet<>();
  private HashSet<String> useBranchAddresses = new HashSet<>();

  public void register(Branch branch) {
    if (branches.containsKey(branch.getCode())) {
      throw new IllegalStateException("Code already in use by another branch.");
    } else if (usedBranchNames.contains(branch.getName())) {
      throw new IllegalStateException("Name already in use by another branch.");
    }

    branch.setOpen(true);

    branches.put(branch.getCode(), branch);
    usedBranchNames.add(branch.getName());
  }

  public ArrayList<Branch> getBranchCodes() {
    return new ArrayList<>(branches.values());
  }

  public void reOpen(long code) {
    Branch branch = getBranch(code);

    if (branch == null) {
      throw new IllegalArgumentException("Branch with given code has not been registered.");
    }

    branch.setOpen(true);
  }

  public boolean close(long code, AccountRegistry accountRegistry) {
    Branch branch = getBranch(code);

    if (branch == null) {
      throw new IllegalStateException("Branch with given code has not been registered.");
    }

    boolean branchHasAccounts = false;
    for (Account acc : accountRegistry.getAccounts()) {
      if (acc.getBranch().equals(branch)) {
        branchHasAccounts = true;
        break;
      }
    }

    if (branchHasAccounts) {
      throw new IllegalStateException("Branch has still account associated with it.");
    }

    if (!branch.getOpen()) {
      return false;
    } else {
      branch.setOpen(false);
      return true;
    }
  }

  public boolean updateBranchName(long code, String name) {
    Branch branch = getBranch(code);

    if (branch == null) {
      throw new IllegalStateException("Branch with given code has not been registered.");
    }

    if (!usedBranchNames.contains(name)) {
      usedBranchNames.remove(branch.getName());
      branch.setName(name);
      usedBranchNames.add(name);
    } else {
      throw new IllegalStateException("Name already in use by another branch.");
    }

    return true;
  }

  public boolean updateBranchAddress(long code, String address) {
    Branch branch = getBranch(code);

    if (branch == null) {
      throw new IllegalStateException("Branch with given code has not been registered.");
    }

    if (!usedBranchNames.contains(address)) {
      branch.setAddress(address);
      useBranchAddresses.add(address);
    }

    return true;
  }

  public Branch getBranch(long code) {
    return branches.get(code);
  }
}
