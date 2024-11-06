package memo1.ejercicio1;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Agregá en Main.java la transferencia de fondos entre dos cuentas y mostrá
// por consola el nuevo saldo.
//
// Agregá en Main.java la creación de cuentas asociadas a titulares, la
// transferencia de fondos entre dos cuentas por alias y presentá por consola
// los resultados obtenidos.

public class Main {
  public static void main(String[] args) {
    Branch branchFiuba = new Branch(12124121L, "FIUBA", "Paseo Colón 950");

    Client client1 = new Client(96113425L, "Carlos", "Castillo");
    Client client2 = new Client(22335789L, "Eduardo", "Pereira");
    Client client3 = new Client(45678123L, "Flavio", "Castillo");

    Account account1 = new Account(123456789L, "account1", branchFiuba, client1, 1000.0);
    Account account2 = new Account(124128800L, "account2", branchFiuba, client2, 200.1);
    Account account3 = new Account(751800901L, "account3", branchFiuba, client3, 541.7);

    account1.setCoOwner(client2);
    account1.setCoOwner(client3);

    Transaction transferTransaction = account1.transfer(account2, 311.0);
    Transaction depositTransaction = account2.deposit(751.2);
    Transaction withdrawalTransaction = account3.withdraw(50.0);

    logTransaction(transferTransaction);
    logTransaction(depositTransaction);
    logTransaction(withdrawalTransaction);

    logAccountBalance(account1);
    logAccountBalance(account2);
    logAccountBalance(account3);

    AccountRegistry accountRegister = new AccountRegistry();

    accountRegister.register(account1);
    accountRegister.register(account2);
    accountRegister.register(account3);

    logAccountOwners(account1);
    logAccountOwners(account2);
    logAccountOwners(account3);

    Transaction transferByAliasTransaction =
        accountRegister.transfer(account3.getAlias(), account1.getAlias(), 120.0);

    logTransaction(transferByAliasTransaction);
  }

  private static void logTransaction(Transaction transaction) {
    System.out.println("TRANSACTION: " + transaction.getCode());
    System.out.println(" • TYPE: " + transaction.getType().toUpperCase());

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    System.out.println(" • DATE: " + transaction.getDate().format(dateFormatter));

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    System.out.println(" • TIME: " + transaction.getTime().format(timeFormatter));

    System.out.println(" • AMOUNT: $" + transaction.getAmount());

    if (transaction.getType() == "transfer") {
      System.out.println(" • LINKED ACCOUNTS:");
      System.out.println("    • SENDER (CBU): " + transaction.getSender().getCbu());
      System.out.println("    • RECEIVER (CBU): " + transaction.getReceiver().getCbu());
    } else {
      System.out.println(" • ACCOUNT (CBU): " + transaction.getSender().getCbu());
    }

    System.out.println();
  }

  private static void logAccountBalance(Account account) {
    System.out.println("ACCOUNT");
    System.out.println(" • CBU: " + account.getCbu());
    System.out.println(" • ALIAS: " + account.getAlias());
    System.out.println(" • BALANCE: $" + account.getBalance());

    System.out.println();
  }

  private static void logAccountOwners(Account account) {
    System.out.println("ACCOUNT (CBU): " + account.getCbu());
    System.out.println(" • OWNER (DNI): " + account.getOwner().getDni());

    ArrayList<Client> coOwners = account.getCoOwners();
    System.out.println(" • CO-OWNERS: " + coOwners.size());

    for (Client client : coOwners) {
      System.out.println("    • CO-OWNER (CBU): " + client.getDni());
    }

    System.out.println();
  }
}
