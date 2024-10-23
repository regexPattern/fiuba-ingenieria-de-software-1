package memo1.ejercicio1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.UUID;

public class TransferLog {
	public enum TransferType {
		Transfer,
		Deposit,
		Withdrawal
	}

	private UUID correlativeCode;
	private TransferType type;
	private Double amount;
	private LocalDateTime dateTime;
	private HashSet<Long> associatedAccountsCbus = new HashSet<>();

	private TransferLog(TransferType type, Double amount) {
		this.correlativeCode = UUID.randomUUID();
		this.dateTime = LocalDateTime.now();
	}

	public TransferLog(TransferType type, Double amount, Account account) {
		this.associatedAccountsCbus.add(account.getCbu());
	}

	public TransferLog(TransferType type, Double amount, Account sender, Account receiver) {
		this(type, amount);
		this.associatedAccountsCbus.add(sender.getCbu());
		this.associatedAccountsCbus.add(receiver.getCbu());
	}

	public UUID getCorrelativeCode() {
		return correlativeCode;
	}

	public LocalDate getDate() {
		return dateTime.toLocalDate();
	}

	public LocalTime getTime() {
		return dateTime.toLocalTime();
	}

	public String getType() {
		switch (type) {
			case Transfer: return "transfer";
			case Deposit: return "deposit";
			case Withdrawal: return "withdrawal";
		}

		return null;
	}

	public Double getAmount() {
		return amount;
	}

	public HashSet<Long> getAssociatedAccountsCbus() {
		return associatedAccountsCbus;
	}
}
