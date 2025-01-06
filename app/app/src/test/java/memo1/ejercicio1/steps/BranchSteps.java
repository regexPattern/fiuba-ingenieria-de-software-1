package memo1.ejercicio1.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import memo1.ejercicio1.Account;
import memo1.ejercicio1.AccountRegistry;
import memo1.ejercicio1.Branch;
import memo1.ejercicio1.Client;

public class BranchSteps {
  private final BranchCommonSteps branchSteps;
  private final OperationResultCommonSteps operationResultSteps;

  private AccountRegistry accountRegistry;

  public BranchSteps(
      BranchCommonSteps branchSteps, OperationResultCommonSteps operationResultSteps) {
    this.branchSteps = branchSteps;
    this.operationResultSteps = operationResultSteps;
  }

  @Before
  public void reset() {
    accountRegistry = new AccountRegistry();
  }

  @Then("The branch code should be {long}")
  public void verifyBranchCode(long code) {
    assertEquals(branchSteps.getBranch().getCode(), code);
  }

  @And("The branch name should be {string}")
  public void verifyBranchName(String name) {
    assertEquals(branchSteps.getBranch().getName(), name);
  }

  @And("The branch should be open")
  public void verifyBranchOpen() {
    assertTrue(branchSteps.getBranch().getOpen());
  }

  @And("The branch address should be {string}")
  public void verifyBranchAddress(String address) {
    assertEquals(branchSteps.getBranch().getAddress(), address);
  }

  @When("I register another branch with code {long}, name {string} and address {string}")
  @When("I try to register another branch with code {long}, name {string} and address {string}")
  public void registerAnotherBranch(long code, String name, String address) {
    Branch branch = new Branch(code, name, address);
    operationResultSteps.execute(() -> branchSteps.getBranchRegistry().register(branch));
  }

  @And("An account with CBU {long}, alias {string} and owner with DNI {long}, linked to the branch")
  public void registerAccountWithBranch(long cbu, String alias, long dni) {
    accountRegistry.register(
        new Account(
            cbu, alias, branchSteps.getBranch(), new Client(dni, "someName", "someSurName")));
  }

  @When("I close the branch")
  @When("I try to close the branch")
  public void closeBranch() {
    operationResultSteps.execute(
        () ->
            branchSteps
                .getBranchRegistry()
                .close(branchSteps.getBranch().getCode(), accountRegistry));
  }

  @Then("The branch should be closed")
  public void verifyBranchClosed() {
    assertFalse(branchSteps.getBranch().getOpen());
  }

  @When("I reopen the branch")
  public void reOpenBranch() {
    branchSteps.getBranchRegistry().reOpen(branchSteps.getBranch().getCode());
  }

  @Then("The branch should be reopened")
  public void verifyBranchReOpened() {
    assertTrue(branchSteps.getBranch().getOpen());
  }

  @When("I try to close a branch with code {long} that has not been registered")
  public void tryToCloseABranchThatHasNotBeenRegistered(long code) {
    operationResultSteps.execute(
        () -> branchSteps.getBranchRegistry().close(code, new AccountRegistry()));
  }

  @When("I try to reopen a branch with code {long} that has not been registered")
  public void tryToReOpenABranchThatHasNotBeenRegistered(long code) {
    operationResultSteps.execute(() -> branchSteps.getBranchRegistry().reOpen(code));
  }

  @Then("The second branch should be registered")
  public void verifySecondBranchRegistered() {
    ArrayList<Branch> registeredBranches = branchSteps.getBranchRegistry().getBranchCodes();

    assertEquals(registeredBranches.size(), 2);
  }

  @And("The second branch should not be registered")
  public void verifySecondBranchNotRegistered() {
    ArrayList<Branch> registeredBranches = branchSteps.getBranchRegistry().getBranchCodes();

    assertEquals(registeredBranches.size(), 1);
  }

  @And("Both branches should be located at the same address")
  public void verifyBothBranchesShareAdress() {
    ArrayList<Branch> registeredBranches = branchSteps.getBranchRegistry().getBranchCodes();

    assertEquals(registeredBranches.get(0).getAddress(), registeredBranches.get(1).getAddress());
  }

  @When("I update the branch with code {long} name to {string}")
  public void updateBranchName(long code, String name) {
    operationResultSteps.execute(
        () -> branchSteps.getBranchRegistry().updateBranchName(code, name));
  }

  @And("I update the branch with code {long} address to {string}")
  public void updateBranchAddress(long code, String address) {
    operationResultSteps.execute(
        () -> branchSteps.getBranchRegistry().updateBranchAddress(code, address));
  }

  @And("The branch with code {long} name should remain {string}")
  public void verifyNameOfBranchByCode(long code, String name) {
    assertEquals(branchSteps.getBranchRegistry().getBranch(code).getName(), name);
  }

  @And("The branch with code {long} address should remain {string}")
  public void verifyAddressOfBranchByCode(long code, String address) {
    assertEquals(branchSteps.getBranchRegistry().getBranch(code).getAddress(), address);
  }
}
