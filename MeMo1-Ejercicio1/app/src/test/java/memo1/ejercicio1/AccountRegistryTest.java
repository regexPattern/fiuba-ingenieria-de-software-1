package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountRegistryTest {
	@Test
	void anAccountRegistryIsCreatedWithNoAccounts() {
		AccountRegistry accountRegistry = new AccountRegistry();

		assertEquals(accountRegistry.getRegisteredAccounts().size(), 0);
	}
}
