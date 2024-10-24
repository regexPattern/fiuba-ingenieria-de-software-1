package memo1.ejercicio1;

import static org.junit.Assert.*;
import io.cucumber.java.en.*;

public class BranchSteps {
	private Branch branch;
	private BranchRegistry branchRegistry;
	private Exception operationResult;

	@Given("I create a branch with code {int}, name {string} and address {string}")
	@Given("A branch with code {int}, name {string} and address {string}")
	public void createABranch(int code, String name, String address) {
		branchRegistry = new BranchRegistry();
		branch = new Branch(code, name ,address);
		branchRegistry.registerBranch(branch);
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
	public void verifyOperationDenied() {
		assertNotNull(operationResult);
	}
}
