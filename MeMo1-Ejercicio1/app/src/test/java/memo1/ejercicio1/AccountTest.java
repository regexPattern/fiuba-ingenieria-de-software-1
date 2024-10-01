package memo1.ejercicio1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void defaultConstructorShouldInitializeBalanceToZero() {
        Account account = new Account();
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void constructorShouldSetBalanceCorrectly() {
        Account account = new Account(100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void constructorShouldThrowExceptionIfBalanceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Account(-50.0));
    }

    @Test
    void constructorWithCbuShouldInitializeCorrectly() {
        Account account = new Account(123456789L, 100.0);
        assertEquals(123456789L, account.getCbu());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void setBalanceShouldThrowExceptionIfBalanceIsNegative() {
        Account account = new Account();
        assertThrows(IllegalArgumentException.class, () -> account.setBalance(-1.0));
    }

    @Test
    void depositShouldIncreaseBalance() {
        Account account = new Account();
        account.deposit(50.0);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void depositShouldReturnFalseForNegativeAmount() {
        Account account = new Account();
        assertFalse(account.deposit(-10.0));
    }

    @Test
    void withdrawShouldDecreaseBalance() {
        Account account = new Account(100.0);
        assertTrue(account.withdraw(50.0));
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void withdrawShouldReturnFalseIfAmountExceedsBalance() {
        Account account = new Account(100.0);
        assertFalse(account.withdraw(150.0));
    }

    @Test
    void withdrawShouldReturnFalseForNegativeAmount() {
        Account account = new Account(100.0);
        assertFalse(account.withdraw(-10.0));
    }

    @Test
    void withdrawShouldAllowExactAmount() {
        Account account = new Account(100.0);
        assertTrue(account.withdraw(100.0));
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void transferShouldThrowExceptionIfTargetAccountIsNull() {
        Account account = new Account(100.0);
        assertThrows(IllegalArgumentException.class, () -> account.transfer(null, 100.0));
    }

    @Test
    void transferShouldReturnFalseIfTargetAccountIsTheSameAsSender() {
        Account account = new Account(123L, 100.0);
        assertFalse(account.transfer(account, 100.0));
        assertFalse(account.transfer(new Account(account.getCbu(), 100.0), 100.0));
    }

    @Test
    void transferShouldReturnFalseIfAmountIsNegative() {
        Account account = new Account(123L, 100.0);
        assertFalse(account.transfer(new Account(), -100.0));
    }

    @Test
    void transferShouldDecreaseBalanceFromSenderAccount() {
        Account account = new Account(123L, 100.0);
        account.transfer(new Account(), 30.0);

        assertEquals(account.getBalance(), 70.0);
    }

    @Test
    void transferShouldIncreaseBalanceFromTargetAccount() {
        Account account = new Account(123L, 100.0);
        Account receiver = new Account(456L, 0.0);

        account.transfer(receiver, 30.0);
        assertEquals(receiver.getBalance(), 30.0);

        account.transfer(receiver, 20.0);
        assertEquals(receiver.getBalance(), 50.0);
    }

    @Test
    void transferShouldReturnFalseWhenTheAmountExceedsTheSenderAccountBalance() {
        Account account = new Account(123L, 100.0);
        assertFalse(account.transfer(new Account(), 2000.0));
    }
}
