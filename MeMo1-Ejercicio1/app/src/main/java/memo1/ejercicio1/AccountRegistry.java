package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashSet;

public class AccountRegistry {
	private ArrayList<Account> registeredAccounts;
	private HashSet<Long> takenCbus;
	private HashSet<String> takenAliases;

	public AccountRegistry() {
		registeredAccounts = new ArrayList<>();
		takenCbus = new HashSet<>();
		takenAliases = new HashSet<>();
	}

	public void registerAccount(Account account) {
		if (takenCbus.contains(account.getCbu())) {
			throw new IllegalStateException("There is an account already registerd with the same CBU.");
		} else if (takenAliases.contains(account.getAlias())) {
			throw new IllegalStateException("There is an account already registered with the same alias.");
		}

		takenCbus.add(account.getCbu());
		takenAliases.add(account.getAlias());
		registeredAccounts.add(account);
	}

	public ArrayList<Account> getRegisteredAccounts() {
		return registeredAccounts;
	}
}
