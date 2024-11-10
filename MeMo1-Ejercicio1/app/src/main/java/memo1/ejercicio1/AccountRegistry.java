package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AccountRegistry {

  private HashSet<Account> accounts = new HashSet<>();
  private HashMap<Long, Account> accountsByCbu = new HashMap<>();
  private HashMap<String, Account> accountsByAlias = new HashMap<>();

  public void register(Account account) {
    if (accountsByCbu.containsKey(account.getCbu())) {
      throw new IllegalStateException("CBU already in use by another account.");
    } else if (accountsByAlias.containsKey(account.getAlias())) {
      throw new IllegalStateException("Alias already in use by another account.");
    }

    accounts.add(account);
    accountsByCbu.put(account.getCbu(), account);
    accountsByAlias.put(account.getAlias(), account);
  }

  public void unRegister(long cbu) {
    Account account = accountsByCbu.get(cbu);

    if (account == null) {
      throw new IllegalArgumentException("Account has not been registered yet.");
    } else if (account.getBalance() > 0) {
      throw new IllegalStateException("Cannot unregister an account that still has funds.");
    }

    accounts.remove(account);
    accountsByCbu.remove(account.getCbu());
    accountsByAlias.remove(account.getAlias());
  }

  public ArrayList<Account> getAccounts() {
    return new ArrayList<>(accounts);
  }

  public ArrayList<Long> getCbus() {
    return new ArrayList<>(accountsByCbu.keySet());
  }

  public ArrayList<String> getAliases() {
    return new ArrayList<>(accountsByAlias.keySet());
  }

  public Transaction transfer(long senderCbu, long receiverCbu, double amount) {
    Account sender = accountsByCbu.get(senderCbu);
    Account receiver = accountsByCbu.get(receiverCbu);

    return transfer(sender, receiver, amount);
  }

  public Transaction transfer(String senderAlias, String receiverAlias, double amount) {
    Account sender = accountsByAlias.get(senderAlias);
    Account receiver = accountsByAlias.get(receiverAlias);

    return transfer(sender, receiver, amount);
  }

  private Transaction transfer(Account sender, Account receiver, double amount) {
    if (sender == null) {
      throw new IllegalArgumentException("Sender account has not been registered yet.");
    } else if (receiver == null) {
      throw new IllegalArgumentException("Receiver account has not been registered yet.");
    }

    return sender.transfer(receiver, amount);
  }
}
