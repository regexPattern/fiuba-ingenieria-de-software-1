package memo1.ejercicio1.steps;

import static org.junit.Assert.*;

import io.cucumber.java.en.*;
import java.util.ArrayList;
import memo1.ejercicio1.*;

public class BranchSteps {
  private Branch branch;
  private BranchRegistry branchRegistry;
  private Exception operationResult;

  @Given("I create a branch with code {int}, name {string} and address {string}")
  @Given("A branch with code {int}, name {string} and address {string}")
  @Given("An opened branch with code {int}, name {string} and address {string}")
  public void createAndRegisterABranch(int code, String name, String address) {
    branchRegistry = new BranchRegistry();
    branch = new Branch(code, name, address);
    branchRegistry.registerBranch(branch);
  }

  @Given("Another branch with code {int}, name {string} and address {string}")
  public void createAndRegisterAnoteBranch(int code, String name, String address) {
    branch = new Branch(code, name, address);
    branchRegistry.registerBranch(branch);
  }

  @Given("A closed branch with code {int}, name {string} and address {string}")
  public void createRegisterAndCloseABranch(int code, String name, String address) {
    branchRegistry = new BranchRegistry();
    branch = new Branch(code, name, address);
    branchRegistry.registerBranch(branch);
    branchRegistry.closeBranch(branch.getCode());
  }

  @Then("The branch code should be {int}")
  public void verifyBranchCode(int code) {
    assertEquals(branch.getCode(), code);
  }

  @And("The branch name should be {string}")
  public void verifyBranchName(String name) {
    assertEquals(branch.getName(), name);
  }

  @And("The branch address should be {string}")
  public void verifyBranchAddress(String address) {
    assertEquals(branch.getAddress(), address);
  }

  @When("I try to register another branch with code {int}, name {string} and address {string}")
  public void tryToRegisterAnotherBranch(int code, String name, String address) {
    Branch newBranch = new Branch(code, name, address);
    try {
      branchRegistry.registerBranch(newBranch);
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @Then("The branch register operation should be denied")
  @Then("The branch closing operation should be denied")
  @Then("The branch reopening operation should be denied")
  public void verifyOperationDenied() {
    assertNotNull(operationResult);
  }

  @When("I close the branch")
  public void closeBranch() {
    branchRegistry.closeBranch(branch.getCode());
  }

  @Then("The branch should be closed")
  public void verifyBranchClosed() {
    assertFalse(branch.isOpen());
  }

  @When("I reopen the branch")
  public void reOpenBranch() {
    branchRegistry.reOpenBranch(branch.getCode());
  }

  @Then("The branch should be reopened")
  public void verifyBranchReOpened() {
    assertTrue(branch.isOpen());
  }

  @When("I try to close a branch with code {int} that has not been registered")
  public void tryToCloseABranchThatHasNotBeenRegistered(int code) {
    try {
      branchRegistry.closeBranch(code);
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @When("I try to reopen a branch with code {int} that has not been registered")
  public void tryToReOpenABranchThatHasNotBeenRegistered(int code) {
    try {
      branchRegistry.reOpenBranch(code);
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @Then("The second branch should be registered")
  public void verifySecondBranchRegistered() {
    ArrayList<Branch> registeredBranches = branchRegistry.getRegisteredBranches();

    assertEquals(registeredBranches.size(), 2);
  }

  @Then("The branch registration operation should be denied")
  public void verifyBranchRegistrationDenied() {
    assertNotNull(operationResult);
  }

  @And("The second branch should not be registered")
  public void verifySecondBranchNotRegistered() {
    ArrayList<Branch> registeredBranches = branchRegistry.getRegisteredBranches();

    assertEquals(registeredBranches.size(), 2);
  }

  @And("Both branches should be located at the same address")
  public void verifyBothBranchesShareAdress() {
    ArrayList<Branch> registeredBranches = branchRegistry.getRegisteredBranches();

    assertEquals(registeredBranches.get(0).getAddress(), registeredBranches.get(1).getAddress());
  }

  @When("I update the branch with code {int} name to {string}")
  public void updateBranchName(int code, String name) {
    try {
      branchRegistry.updateBranchName(code, name);
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @And("I update the branch with code {int} address to {string}")
  public void updateBranchAddress(int code, String address) {
    try {
      branchRegistry.updateBranchAddress(code, address);
    } catch (Exception exception) {
      operationResult = exception;
    }
  }

  @Then("The branch update operation should fail")
  public void verifyOperationFail() {
    assertNotNull(operationResult);
  }

  @And("The branch with code {int} name should remain {string}")
  public void verifyNameOfBranchByCode(int code, String name) {
    assertEquals(branchRegistry.getBranch(code).getName(), name);
  }

  @And("The branch with code {int} address should remain {string}")
  public void verifyAddressOfBranchByCode(int code, String address) {
    assertEquals(branchRegistry.getBranch(code).getAddress(), address);
  }

  @Then("The branch update operation should not fail")
  public void verifyOperationDidntFail() {
    assertNull(operationResult);
  }
}
