package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountTest {
    @Test
    void constructorShouldSetCbuAndAliasCorrectly() {
        Long cbu = 123456789L;
        String alias = "account";

        Account account = new Account(cbu, alias);

        assertEquals(account.getCbu(), cbu);
        assertEquals(account.getAlias(), alias);
    }

    @Test
    void constructorShouldInitializeBalanceToZero() {
        Account account = new Account(123456789L, "account");
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void constructorShouldSetBalanceCorrectly() {
        Account account = new Account(123456789L, "account", 100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void constructorShouldThrowExceptionIfBalanceIsNegative() {
        double balance = -50.0;

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Account(123456789L, "account", balance));

        assertEquals(exception.getMessage(), "Balance cannot be negative.");
    }

    @Test
    void setBalanceShouldThrowExceptionIfBalanceIsNegative() {
        Account account = dummyAccount();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.setBalance(-1.0));

        assertEquals(exception.getMessage(), "Balance cannot be negative.");
    }

    @Test
    void depositShouldIncreaseBalance() {
        Account account = dummyAccount();

        account.deposit(51.2);

        assertEquals(51.2, account.getBalance());
    }

    @Test
    void depositShouldThrowExceptionForNegativeAmount() {
        Account account = dummyAccount();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.deposit(-10.0));

        assertEquals(exception.getMessage(), "Amount has to be positive.");
    }

    @Test
    void withdrawShouldDecreaseBalance() {
        Account account = dummySenderAccount(100.0);

        assertDoesNotThrow(() -> account.withdraw(30.0));

        assertEquals(account.getBalance(), 70.0);
    }

    @Test
    void withdrawShouldThrowExceptionIfAmountExceedsBalance() {
        Account account = dummySenderAccount(100.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(150.0));

        assertEquals(exception.getMessage(), "Not enough funds.");
    }

    @Test
    void withdrawShouldThrowExceptionForNegativeAmount() {
        Account account = dummySenderAccount(100.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10.0));

        assertEquals(exception.getMessage(), "Amount cannot be negative.");
    }

    @Test
    void withdrawShouldAllowExactAmount() {
        Account account = dummySenderAccount(100.0);

        assertDoesNotThrow(() -> account.withdraw(100.0));

        assertEquals(account.getBalance(), 0.0);
    }

    @Test
    void transferShouldThrowExceptionIfTargetAccountIsNull() {
        Account account = dummySenderAccount(100.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.transfer(null, 100.0));

        assertEquals(exception.getMessage(), "Receiver account cannot be null.");
    }

    @Test
    void transferShouldThrowExceptionIfTargetAccountIsTheSameAsSender() {
        Account sender = dummySenderAccount(100.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> sender.transfer(sender, 100.0));

        assertEquals(exception.getMessage(), "Receiver account cannot be same as sender.");

        exception = assertThrows(IllegalArgumentException.class,
                () -> sender.transfer(new Account(sender.getCbu(), sender.getAlias(), 100.0), 100.0));

        assertEquals(exception.getMessage(), "Receiver account cannot be same as sender.");
    }

    @Test
    void transferShouldThrowExceptionIfAmountIsNegative() {
        Account sender = dummySenderAccount(100.0);
        Account receiver = dummyReceiverAccount(0.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> sender.transfer(receiver, -100.0));

        assertEquals(exception.getMessage(), "Amount cannot be negative.");
    }

    @Test
    void transferShouldDecreaseBalanceFromSenderAccount() {
        Account sender = dummySenderAccount(100.0);
        Account receiver = dummyReceiverAccount(0.0);

        sender.transfer(receiver, 30.0);

        assertEquals(sender.getBalance(), 70.0);
    }

    @Test
    void transferShouldIncreaseBalanceFromTargetAccount() {
        Account sender = dummySenderAccount(100.0);
        Account receiver = dummyReceiverAccount(0.0);

        sender.transfer(receiver, 30.0);
        assertEquals(receiver.getBalance(), 30.0);

        sender.transfer(receiver, 20.0);
        assertEquals(receiver.getBalance(), 50.0);
    }

    @Test
    void transferShouldThrowExceptionWhenTheAmountExceedsTheSenderAccountBalance() {
        Account sender = dummySenderAccount(100.0);
        Account receiver = dummyReceiverAccount(0.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> sender.transfer(receiver, 2000.0));

        assertEquals(exception.getMessage(), "Not enough funds.");
    }

    @Test
    void transferShouldAllowExactAmount() {
        Account sender = dummySenderAccount(100.0);
        Account receiver = dummyReceiverAccount(0.0);

        assertDoesNotThrow(() -> sender.transfer(receiver, sender.getBalance()));
        assertEquals(sender.getBalance(), 0.0);
    }

    private Account dummyAccount() {
        return dummySenderAccount(0.0);
    }

    private Account dummySenderAccount(double balance) {
        return new Account(123456789L, "sender", balance);
    }

    private Account dummyReceiverAccount(double balance) {
        return new Account(987654321L, "receiver", balance);
    }
}
