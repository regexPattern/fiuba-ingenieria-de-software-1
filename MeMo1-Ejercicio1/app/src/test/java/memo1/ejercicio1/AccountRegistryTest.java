package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountRegistryTest {
	@Test
	void anAccountRegistryIsCreatedWithNoAccounts() {
		AccountRegistry accountRegistry = new AccountRegistry();

		assertEquals(accountRegistry.getRegisteredAccounts().size(), 0);
	}

	@Test
	void registryShouldThrowExceptionWhenTransferingToNonExcitingAccount() {
		AccountRegistry accountRegistry = new AccountRegistry();
		Account sender = new Account(123456789L, "sender", 100.0);

		accountRegistry.registerAccount(sender);

		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> accountRegistry.transferFromAccountToAccount(sender.getCbu(), 987654321L, 20.0));

		assertEquals(exception.getMessage(), "Receiver account does not exist.");

		exception = assertThrows(IllegalArgumentException.class,
				() -> accountRegistry.transferFromAccountToAccount(sender.getAlias(), "receiver", 20.0));

		assertEquals(exception.getMessage(), "Receiver account does not exist.");
	}

	@Test
	void registryShouldThrowExceptionWhenTransferingFromNonExcitingAccount() {
		AccountRegistry accountRegistry = new AccountRegistry();
		Account receiver = new Account(987654321L, "sender", 100.0);

		accountRegistry.registerAccount(receiver);

		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> accountRegistry.transferFromAccountToAccount(123456789L, receiver.getCbu(), 20.0));

		assertEquals(exception.getMessage(), "Sender account does not exist.");

		exception = assertThrows(IllegalArgumentException.class,
				() -> accountRegistry.transferFromAccountToAccount("receiver", receiver.getAlias(), 20.0));

		assertEquals(exception.getMessage(), "Sender account does not exist.");
	}
}
