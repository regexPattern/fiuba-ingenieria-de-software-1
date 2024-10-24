package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

class ClientTest {
    @Test
    void constructorShouldSetBirthDateCorrectly() {
        String birthDateString = "10/09/2001";

        Client client = new Client(123456789L, "Carlos", "Castillo", birthDateString, "Paseo Colón 950");

        LocalDate expectedBirthDate = LocalDate.parse(birthDateString, Client.dateFormatter);

        assertEquals(client.getBirthDate(), expectedBirthDate);
    }

    @Test
    void constructorShouldThrowExceptionIfBirthDateFormatIsNotAValidDate() {
        Long dni = 123456789L;
        String name = "Carlos";
        String surName = "Castillo";
        String address = "Paseo Colón 950";

        String invalidDateFormatDateString = "2004/10/01";
        String invalidDateString = "A/B/2001";

        assertThrows(DateTimeParseException.class,
                () -> new Client(dni, name, surName, invalidDateFormatDateString, address));
        assertThrows(DateTimeParseException.class, () -> new Client(dni, name, surName, invalidDateString, address));
    }

    @Test
    void constructorShouldThrowExceptionIfBirthDateIsAFutureDate() {
        LocalDate todayDate = LocalDate.now();
        LocalDate futureDate = todayDate.plusDays(1);
        String futureDateString = futureDate.format(Client.dateFormatter);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Client(0L, "", "", futureDateString, ""));

        assertEquals(exception.getMessage(), "Birth date cannot be a future date.");
    }
}
