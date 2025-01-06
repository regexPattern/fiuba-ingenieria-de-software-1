package memo1.ejercicio1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Transaction {
  private UUID code;
  private String type;
  private Double amount;
  private LocalDateTime dateTime;
  private Account sender;
  private Account receiver;

  public Transaction(String type, Double amount, Account account) {
    this(type, amount, account, account);
  }

  public Transaction(String type, Double amount, Account sender, Account receiver) {
    if (!(type == "transfer" || type == "deposit" || type == "withdrawal")) {
      throw new IllegalArgumentException(
          "Unsupported transfer type (supported types: 'transfer', 'deposit', 'withdrawal')");
    }

    this.code = UUID.randomUUID();
    this.type = type;
    this.amount = amount;
    this.dateTime = LocalDateTime.now();
    this.sender = sender;
    this.receiver = receiver;
  }

  public UUID getCode() {
    return code;
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

  public Account getSender() {
    return sender;
  }

  public Account getReceiver() {
    return receiver;
  }
}
