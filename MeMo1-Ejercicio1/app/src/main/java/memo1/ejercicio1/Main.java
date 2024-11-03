package memo1.ejercicio1;

public class Main {
  public static void main(String[] args) {
    // Crear una instancia de Account
    Account account1 = new Account(123456789L, "account1");
    account1.setBalance(1000.0); // Establecer el balance inicial

    // Crear una instancia de Account usando el constructor con saldo inicial
    Account account2 = new Account(987654321L, "account2", 500.0);

    // Realizar operaciones de depósito y retiro
    account1.deposit(200.0); // Depositar 200 en la cuenta 1
    boolean successWithdraw1 = true;

    try {
      account1.withdraw(300.0); // Retirar 300 de la cuenta 1
    } catch (Exception exception) {
      successWithdraw1 = false;
    }

    account2.deposit(100.0); // Depositar 100 en la cuenta 2
    boolean successWithdraw2 = true;

    try {
      account2.withdraw(700.0); // Intentar retirar 700 de la cuenta 2 (debería fallar)
    } catch (Exception exception) {
      successWithdraw2 = false;
    }

    // Imprimir detalles de las cuentas
    printAccountBalance(account1, account2);

    // Verificar si las operaciones fueron exitosas
    System.out.println("Retiro en cuenta 1 fue " + (successWithdraw1 ? "exitoso" : "fallido"));
    System.out.println("Retiro en cuenta 2 fue " + (successWithdraw2 ? "exitoso" : "fallido"));
    System.out.println();

    // Transferir fondos de cuenta 1 a cuenta 2
    Double montoTransferencia = 330.0;
    boolean successTransferCbu = true;

    try {
      account1.transfer(account2, montoTransferencia);
    } catch (Exception exception) {
      successTransferCbu = false;
    }

    System.out.println(
        "Transferencia con monto de "
            + montoTransferencia
            + " desde cuenta 1 a cuenta 2 fue "
            + (successTransferCbu ? "exitosa" : "fallida"));

    printAccountBalance(account1, account2);

    // =====================
    // ==== EJERCICIO 3 ====
    // =====================
    //
    // Creación de cuentas asociadas a titulares
    Client client1 = new Client(96113425L, "Carlos", "Castillo");
    Account account3 = new Account(17642839L, "account3", 2000.0);
    account3.setOwner(client1);

    Client client2 = new Client(223356789L, "Eduardo", "Pereira");
    Client client3 = new Client(456789123L, "Flavio", "Castillo");

    account3.setCoOwner(client2);
    account3.setCoOwner(client3);

    // Transferencia de fondos mediante alias
    AccountRegistry accountRegister = new AccountRegistry();
    Branch branch = new Branch(001, "Suc. Belgrano", "Cabilo 980 CABA");

    accountRegister.registerAccount(account1, branch);
    accountRegister.registerAccount(account2, branch);
    accountRegister.registerAccount(account3, branch);

    TransferLog transferLog =
        accountRegister.transferFromAccountToAccount(
            account3.getAlias(), account1.getAlias(), 120.0);

    System.out.println("Registro de transferencia por alias:");
    System.out.println("Tipo: " + transferLog.getType());
    System.out.println("Monto: " + transferLog.getAmount());
    System.out.println("CBUs de cuentas involucradas: " + transferLog.getAssociatedAccountsCbus());
    System.out.println("Fecha: " + transferLog.getDate());
    System.out.println("Hora: " + transferLog.getTime());
  }

  static void printAccountBalance(Account account1, Account account2) {
    System.out.println("Cuenta 1:");
    System.out.println("CBU: " + account1.getCbu());
    System.out.println("Balance: " + account1.getBalance());

    System.out.println("Cuenta 2:");
    System.out.println("CBU: " + account2.getCbu());
    System.out.println("Balance: " + account2.getBalance());
  }
}
