package memo1.ejercicio1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Client {

  private long dni;
  private String name;
  private String surName;
  private LocalDate birthDate;
  private String address;
  private Client partner;

  public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public Client(long dni, String name, String surName) {
    this.dni = dni;
    this.name = name;
    this.surName = surName;
  }

  public Client(long dni, String name, String surName, String birthDateString, String address) {
    this(dni, name, surName);
    this.address = address;

    LocalDate birthDate = LocalDate.parse(birthDateString, dateFormatter);
    if (birthDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Birth date cannot be a future date.");
    }

    this.birthDate = birthDate;
  }

  public long getDni() {
    return dni;
  }

  public String getName() {
    return name;
  }

  public String getSurName() {
    return surName;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public String getAddress() {
    return address;
  }

  public LocalDate setPartner(Client client, String marriageDateString) {
    LocalDate marriageDate = LocalDate.parse(marriageDateString, dateFormatter);

    partner = client;
    client.partner = this;

    return marriageDate;
  }

  public Client getPartner() {
    return partner;
  }

  public void update(String name, String surName, String birthDateString, String address) {
    if (name != null && !name.isEmpty()) {
      this.name = name;
    }

    if (surName != null && !surName.isEmpty()) {
      this.surName = surName;
    }

    if (birthDateString != null && !birthDateString.isEmpty()) {
      LocalDate newBirthDate = LocalDate.parse(birthDateString, dateFormatter);
      if (newBirthDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Birth date cannot be a future date.");
      }
      this.birthDate = newBirthDate;
    }

    if (address != null && !address.isEmpty()) {
      this.address = address;
    }
  }
}
