package memo1.ejercicio1;

public class Account {
    private Long cbu;
    private String alias;
    private double balance;

    public Account(Long cbu, String alias) {
        this.cbu = cbu;
        this.alias = alias;
    }

    public Account(Long cbu, String alias, double balance) {
        this(cbu, alias);
        setBalance(balance);
    }

    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public String getAlias() {
        return alias;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }

        this.balance = balance;
    }

    public TransferLog withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        } else if (amount > balance) {
            throw new IllegalArgumentException("Not enough funds.");
        }

        balance -= amount;

        return new TransferLog("withdrawal", amount, this);
    }

    public TransferLog deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount has to be positive.");
        }

        balance += amount;

        return new TransferLog("deposit", amount, this);
    }

    public TransferLog transfer(Account receiver, double amount) {
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver account cannot be null.");
        } else if (receiver.getCbu() == getCbu()) {
            throw new IllegalArgumentException("Receiver account cannot be same as sender.");
        }

        withdraw(amount);
        receiver.deposit(amount);

        return new TransferLog("transfer", amount, this, receiver);
    }
}
