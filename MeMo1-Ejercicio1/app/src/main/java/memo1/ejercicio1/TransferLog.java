package memo1.ejercicio1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.UUID;

public class TransferLog {
  private UUID correlativeCode;
  private String type;
  private Double amount;
  private LocalDateTime dateTime;
  private HashSet<Long> associatedAccountsCbus = new HashSet<>();

  private TransferLog(String type, Double amount) {
    if (!(type == "transfer" || type == "deposit" || type == "withdrawal")) {
      throw new IllegalArgumentException(
          "Unsupported transfer type (supported types: 'transfer', 'deposit', 'withdrawal')");
    }

    this.type = type;
    this.amount = amount;
    this.correlativeCode = UUID.randomUUID();
    this.dateTime = LocalDateTime.now();
  }

  public TransferLog(String type, Double amount, Account account) {
    this(type, amount);
    this.associatedAccountsCbus.add(account.getCbu());
  }

  public TransferLog(String type, Double amount, Account sender, Account receiver) {
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
    return type;
  }

  public Double getAmount() {
    return amount;
  }

  public HashSet<Long> getAssociatedAccountsCbus() {
    return associatedAccountsCbus;
  }
}
