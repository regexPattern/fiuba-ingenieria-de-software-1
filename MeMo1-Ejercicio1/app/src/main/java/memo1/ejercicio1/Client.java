package memo1.ejercicio1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Client {
	private Long dni;
	private String name;
	private String surName;
	private LocalDate birthDate;
	private String address;

	static public DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public Client(Long dni, String name, String surName) {
		this.dni = dni;
		this.name = name;
		this.surName = surName;
	}

	public Client(Long dni, String name, String surName, String birthDateString, String address)
			throws Exception {
		this(dni, name, surName);
		this.address = address;

		LocalDate birthDate = LocalDate.parse(birthDateString, dateFormatter);
		if (birthDate.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Birthdate cannot be a future date.");
		}
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
}
