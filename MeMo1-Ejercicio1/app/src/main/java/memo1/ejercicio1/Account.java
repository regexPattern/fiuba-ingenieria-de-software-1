package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Account {
  private Long cbu;
  private String alias;
  private Branch branch;
  private Client owner;
  private HashSet<Client> coOwners = new HashSet<>();
  private Double balance;

  public Account(Long cbu, String alias, Branch branch, Client owner) {
    this.cbu = cbu;
    this.alias = alias;
    this.branch = branch;
    this.owner = owner;
  }

  public Account(Long cbu, String alias, Branch branch, Client owner, Double balance) {
    this(cbu, alias, branch, owner);

    if (balance < 0) {
      throw new IllegalArgumentException("Balance cannot be negative.");
    }

    this.balance = balance;
  }

  public Long getCbu() {
    return cbu;
  }

  // TODO: Tambien voy a necesitar una de update alias, pero que solo pueda ser
  // utilizada de manera interna para comprobar las colisiones. Probablemente
  // me convenga unifcar todo en una solo interfaz, para consistencia en como
  // seteo y geteo las props.
  public String getAlias() {
    return alias;
  }

  public Double getBalance() {
    return balance;
  }

  public Transaction transfer(Account receiver, Double amount) {
    if (receiver == null) {
      throw new IllegalArgumentException("Receiver account cannot be null.");
    } else if (receiver.equals(this)) {
      throw new IllegalArgumentException("Receiver account cannot be same as sender.");
    }

    withdraw(amount);
    receiver.deposit(amount);

    return new Transaction("transfer", amount, this, receiver);
  }

  public Transaction withdraw(Double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount cannot be negative.");
    } else if (amount > balance) {
      throw new IllegalArgumentException("Not enough funds.");
    }

    balance -= amount;

    return new Transaction("withdrawal", amount, this);
  }

  public Transaction deposit(Double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount has to be positive.");
    }

    balance += amount;

    return new Transaction("deposit", amount, this);
  }

  void setOwner(Client client) {
    if (owner != null) {
      throw new IllegalStateException("Cannot assign multiple owners.");
    }

    owner = client;
  }

  public Client getOwner() {
    return owner;
  }

  // TODO: Tendria que tener algun test que me diga si puedo volver a agregar
  // co-owners que ya son co-owners. Realmente esto si funciona porque tengo un
  // hashset, pero eso es implementacion interna.
  public void setCoOwner(Client client) {
    if (client == owner) {
      throw new IllegalStateException("Account owner cannot be set as co-owner.");
    }

    coOwners.add(client);
  }

  public ArrayList<Client> getCoOwners() {
    return new ArrayList<>(coOwners);
  }

  // TODO: Este metodo deberia existir pero solo para uso interno por parte del
  // account manager o del branch manager.
  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public Branch getBranch() {
    return branch;
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
        && Objects.equals(getAlias(), otherAccount.getAlias());
  }
}
