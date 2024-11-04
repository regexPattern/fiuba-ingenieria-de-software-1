package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BranchRegistry {
  private HashMap<Long, Branch> branchesByCode = new HashMap<>();
  private HashSet<String> usedBranchNames = new HashSet<>();
  private HashSet<String> useBranchAddresses = new HashSet<>();

  public void register(Branch branch) {
    if (branchesByCode.putIfAbsent(branch.getCode(), branch) != null) {
      throw new IllegalStateException("Code already in use by another branch.");
    } else if (usedBranchNames.contains(branch.getName())) {
      throw new IllegalStateException("Name already in use by another branch.");
    }

    branch.setOpen(true);
    usedBranchNames.add(branch.getName());
  }

  public ArrayList<Branch> getBranchCodes() {
    return new ArrayList<>(branchesByCode.values());
  }

  public boolean reOpen(long code) {
    Branch branch = getBranch(code);

    if (branch == null) {
      return false;
    }

    branch.setOpen(true);
    return true;
  }

  public Boolean close(long code) {
    Branch branch = getBranch(code);

    if (branch == null) {
      throw new IllegalStateException("Branch with given code has not been registered.");
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
    return branchesByCode.get(code);
  }
}
