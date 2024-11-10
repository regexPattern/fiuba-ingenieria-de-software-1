package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Account {
  private long cbu;
  private String alias;
  private Branch branch;
  private Client owner;
  private HashSet<Client> coOwners = new HashSet<>();
  private double balance;

  public Account(long cbu, String alias, Branch branch, Client owner) {
    if (alias == null) {
      throw new IllegalArgumentException("Alias cannot be null.");
    } else if (branch == null) {
      throw new IllegalArgumentException("Branch cannot be null.");
    } else if (owner == null) {
      throw new IllegalArgumentException("Owner cannot be null.");
    } else if (!branch.getOpen()) {
      throw new IllegalStateException("Branch cannot be closed.");
    }

    this.cbu = cbu;
    this.alias = alias;
    this.branch = branch;
    this.owner = owner;
    this.balance = 0.0;
  }

  public Account(long cbu, String alias, Branch branch, Client owner, double balance) {
    this(cbu, alias, branch, owner);

    if (balance < 0) {
      throw new IllegalArgumentException("Balance cannot be negative.");
    }

    this.balance = balance;
  }

  public long getCbu() {
    return cbu;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias, List<String> takenAliases) {
    if (alias == getAlias()) {
      throw new IllegalStateException("Account alias is already set to this value.");
    } else if (takenAliases.contains(alias)) {
      throw new IllegalStateException("Alias already in use by another account.");
    }

    this.alias = alias;
  }

  public double getBalance() {
    return balance;
  }

  public Transaction transfer(Account receiver, double amount) {
    if (receiver == null) {
      throw new IllegalArgumentException("Receiver account cannot be null.");
    } else if (receiver.equals(this)) {
      throw new IllegalArgumentException("Receiver account cannot be same as sender.");
    }

    withdraw(amount);
    receiver.deposit(amount);

    return new Transaction("transfer", amount, this, receiver);
  }

  public Transaction withdraw(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount cannot be negative.");
    } else if (amount > balance) {
      throw new IllegalArgumentException("Not enough funds.");
    }

    balance -= amount;

    return new Transaction("withdrawal", amount, this);
  }

  public Transaction deposit(double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount has to be positive.");
    }

    balance += amount;

    return new Transaction("deposit", amount, this);
  }

  public Client getOwner() {
    return owner;
  }

  public void setOwner(Client client) {
    if (client == null) {
      throw new IllegalArgumentException("New owner cannot be null.");
    } else if (client.equals(getOwner())) {
      throw new IllegalStateException("New owner cannot be the same as current owner.");
    }

    Client oldOwner = getOwner();
    owner = client;

    setCoOwner(oldOwner);

    if (coOwners.contains(client)) {
      coOwners.remove(client);
    }
  }

  public void removeCoOwner(Client client) {
    if (client == null) {
      throw new IllegalArgumentException("Co-owner to remove cannot be null.");
    } else if (!coOwners.contains(client)) {
      throw new IllegalStateException("Client is not a co-owner of this account.");
    }

    coOwners.remove(client);
  }

  public ArrayList<Client> getCoOwners() {
    return new ArrayList<>(coOwners);
  }

  public void setCoOwner(Client client) {
    if (client == null) {
      throw new IllegalArgumentException("New co-owner cannot be null.");
    } else if (client.equals(owner)) {
      throw new IllegalStateException("Account owner cannot be set as co-owner.");
    } else if (coOwners.contains(client)) {
      throw new IllegalStateException("Client is already an account co-owner.");
    }

    coOwners.add(client);
  }

  public Branch getBranch() {
    return branch;
  }

  public void setBranch(Branch branch) {
    if (!branch.getOpen()) {
      throw new IllegalStateException("Branch cannot be closed.");
    }

    this.branch = branch;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cbu, alias);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Account otherAccount = (Account) obj;

    return Objects.equals(getCbu(), otherAccount.getCbu())
        && Objects.equals(getAlias(), otherAccount.getAlias())
        && Objects.equals(getBranch(), otherAccount.getBranch())
        && Objects.equals(getOwner(), otherAccount.getOwner());
  }
}
