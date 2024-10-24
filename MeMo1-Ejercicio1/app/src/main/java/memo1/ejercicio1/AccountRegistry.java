package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AccountRegistry {
	private HashSet<Account> registeredAccounts = new HashSet<>();
	private HashMap<Long, Account> registeredAccountsByCbu = new HashMap<>();
	private HashMap<String, Account> registeredAccountsByAlias = new HashMap<>();

	public void registerAccount(Account account) {
		if (registeredAccountsByCbu.containsKey(account.getCbu())) {
			throw new IllegalStateException("There is an account already registerd with the same CBU.");
		} else if (registeredAccountsByAlias.containsKey(account.getAlias())) {
			throw new IllegalStateException("There is an account already registered with the same alias.");
		}

		registeredAccounts.add(account);
		registeredAccountsByCbu.put(account.getCbu(), account);
		registeredAccountsByAlias.put(account.getAlias(), account);
	}

	public ArrayList<Account> getRegisteredAccounts() {
		return new ArrayList<>(registeredAccounts);
	}

	private TransferLog transferFromAccountToAccount(Account sender, Account receiver, Double amount) {
		if (sender == null) {
			throw new IllegalArgumentException("Sender account does not exist.");
		} else if (receiver == null) {
			throw new IllegalArgumentException("Receiver account does not exist.");
		}

		return sender.transfer(receiver, amount);
	}

	public TransferLog transferFromAccountToAccount(Long senderCbu, Long receiverCbu, Double amount) {
		Account sender = registeredAccountsByCbu.get(senderCbu);
		Account receiver = registeredAccountsByCbu.get(receiverCbu);
		return transferFromAccountToAccount(sender, receiver, amount);
	}

	public TransferLog transferFromAccountToAccount(String senderAlias, String receiverAlias, Double amount) {
		Account sender = registeredAccountsByAlias.get(senderAlias);
		Account receiver = registeredAccountsByAlias.get(receiverAlias);
		return transferFromAccountToAccount(sender, receiver, amount);
	}
}
