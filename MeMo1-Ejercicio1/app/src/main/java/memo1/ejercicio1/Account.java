package memo1.ejercicio1;

public class Account {
    private Long cbu;
    private double balance;

    public Account() {
        this.balance = 0.0;
    }

    public Account(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    public Account(Long cbu, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.cbu = cbu;
        this.balance = balance;
    }

    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
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

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {
        if (amount < 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    public boolean transfer(Account receiver, double amount) {
        if (receiver == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        } else if (receiver.cbu == cbu || amount < 0 || balance - amount < 0) {
            return false;
        }

        receiver.setBalance(receiver.balance + amount);
        balance -= amount;

        return true;
    }
}
