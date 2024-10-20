package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

class ClientTest {
    @Test
    void constructorShouldSetBirthDateCorrectly() {
    }

    @Test
    void constructorShouldThrowExceptionIfBirthDateFormatIsNotAValidDate() {
        String invalidDateFormatDateString = "2004/10/01";
        String invalidDateString = "A/B/2001";

        assertThrows(DateTimeParseException.class, () -> new Client(0L, "", "", invalidDateFormatDateString, ""));
        assertThrows(DateTimeParseException.class, () -> new Client(0L, "", "", invalidDateString, ""));
    }

    @Test
    void constructorShouldThrowExceptionIfBirthDateIsAFutureDate() {
        LocalDate todayDate = LocalDate.now();
        LocalDate futureDate = todayDate.plusDays(1);
        String futureDateString = futureDate.format(Client.dateFormatter);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Client(0L, "", "", futureDateString, ""));

        assertEquals(exception.getMessage(), "Birthdate cannot be a future date.");
    }
}
