package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountRegistryTest {
	@Test
	void anAccountRegistryIsCreatedWithNoAccounts() {
		AccountRegistry registry = new AccountRegistry();

		assertEquals(registry.getRegisteredAccounts().size(), 0);
	}
}
