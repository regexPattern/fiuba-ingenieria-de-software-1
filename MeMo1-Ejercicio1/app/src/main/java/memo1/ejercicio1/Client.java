package memo1.ejercicio1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Client {
	private Long dni;
	private String name;
	private String surName;
	private LocalDate birthDate;
	private String address;
	private Client partner;

	static public DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public Client(Long dni, String name, String surName) {
		this.dni = dni;
		this.name = name;
		this.surName = surName;
	}

	public Client(Long dni, String name, String surName, String birthDateString, String address) {
		this(dni, name, surName);
		this.address = address;

		LocalDate birthDate = LocalDate.parse(birthDateString, dateFormatter);
		if (birthDate.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Birth date cannot be a future date.");
		}

		this.birthDate = birthDate;
	}

	public Long getDni() {
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
}
