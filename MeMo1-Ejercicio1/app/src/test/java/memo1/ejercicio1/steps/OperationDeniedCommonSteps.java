package memo1.ejercicio1.steps;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import java.util.function.Supplier;

public class OperationDeniedCommonSteps {
  private Exception operationResult;

  public Exception getOperationResult() {
    return operationResult;
  }

  public <T> T execute(Supplier<T> operation) {
    try {
      return operation.get();
    } catch (Exception operationResult) {
      this.operationResult = operationResult;
      return null;
    }
  }

  public void execute(Runnable operation) {
    try {
      operation.run();
    } catch (Exception operationResult) {
      this.operationResult = operationResult;
    }
  }

  @Before
  public void reset() {
    operationResult = null;
  }

  @Then("The operation should be successful")
  public void verifyOperationSuccessful() {
    assertNull(operationResult);
  }

  @Then("The operation should be denied")
  public void verifyOperationDenied() {
    assertNotNull(operationResult);
  }
}
