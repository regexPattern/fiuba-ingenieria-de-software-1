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
      throw new IllegalStateException("There is an account already registerd with the same CBU.");
    } else if (accountsByAlias.containsKey(account.getAlias())) {
      throw new IllegalStateException(
          "There is an account already registered with the same alias.");
    }

    accounts.add(account);
    accountsByCbu.put(account.getCbu(), account);
    accountsByAlias.put(account.getAlias(), account);
  }

  public ArrayList<Account> getAccounts() {
    return new ArrayList<>(accounts);
  }

  public Transaction transfer(Long senderCbu, Long receiverCbu, Double amount) {
    Account sender = accountsByCbu.get(senderCbu);
    Account receiver = accountsByCbu.get(receiverCbu);
    return transfer(sender, receiver, amount);
  }

  public Transaction transferFromAccountToAccount(
      String senderAlias, String receiverAlias, Double amount) {
    Account sender = accountsByAlias.get(senderAlias);
    Account receiver = accountsByAlias.get(receiverAlias);
    return transfer(sender, receiver, amount);
  }

  private Transaction transfer(Account sender, Account receiver, Double amount) {
    if (sender == null) {
      throw new IllegalArgumentException("Sender account has not been registered yet.");
    } else if (receiver == null) {
      throw new IllegalArgumentException("Receiver account has not been registered yet.");
    }

    return sender.transfer(receiver, amount);
  }

  // TODO: Acá también tengo que manejar todo lo de account deletion.
}
