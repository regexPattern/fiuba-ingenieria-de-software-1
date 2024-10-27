package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TransferLogTest {
	@Test
	void creatingATransferLogWithAnUnsupportedTypeThrowsException() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> new TransferLog("other", 100.0, new Account(123456789L, "")));

		assertEquals(exception.getMessage(),
				"Unsupported transfer type (supported types: 'transfer', 'deposit', 'withdrawal')");
	}
}
