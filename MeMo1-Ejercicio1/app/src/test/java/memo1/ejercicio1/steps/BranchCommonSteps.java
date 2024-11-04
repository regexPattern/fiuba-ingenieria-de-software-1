package memo1.ejercicio1.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import memo1.ejercicio1.Branch;

public class BranchCommonSteps {
  private Branch branch;

  public Branch getBranch() {
    return branch;
  }

  @Before
  public void reset() {
    branch = null;
  }

  @Given("A branch with code {long}, name {string} and address {string}")
  public void createBranch(long code, String name, String address) {
    branch = new Branch(code, name, address);
  }
}
