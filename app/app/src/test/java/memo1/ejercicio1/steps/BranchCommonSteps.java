package memo1.ejercicio1.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import memo1.ejercicio1.Branch;
import memo1.ejercicio1.BranchRegistry;

public class BranchCommonSteps {
  private Branch branch;
  private BranchRegistry branchRegistry;

  public Branch getBranch() {
    return branch;
  }

  public BranchRegistry getBranchRegistry() {
    return branchRegistry;
  }

  @Before
  public void reset() {
    branch = null;
    branchRegistry = new BranchRegistry();
  }

  @Given("A branch with code {long}, name {string} and address {string}")
  @Given("I create a branch with code {long}, name {string} and address {string}")
  @And("Another branch with code {long}, name {string} and address {string}")
  @Given("A branch with code {long}, name {string} and address {string} that has no accounts")
  @Given("An open branch with code {long}, name {string} and address {string}")
  public void createBranch(long code, String name, String address) {
    branch = new Branch(code, name, address);
    branchRegistry.register(branch);
  }

  @Given("A closed branch with code {long}, name {string} and address {string}")
  public void createClosedBranch(long code, String name, String address) {
    branch = new Branch(code, name, address);
    branch.setOpen(false);
    branchRegistry.register(branch);
  }
}
