package ru.onebet.exampleproject.checks;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class CheckOperations {

    public BigDecimal tryToParseBigDecimalFromString(String amount) {
        try {
            BigDecimal amountBd = new BigDecimal(amount);
            amountBd = amountBd.setScale(2,BigDecimal.ROUND_HALF_UP);
            if (amountBd.signum() < 0) throw new NumberFormatException();
            return  amountBd;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect amount entered. Please enter in format as 00.00." +
                    "Amount MUST be above zero.");
        }
    }

    public LocalDateTime tryToParseDateFromString(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy HH:mm");
            try {
                LocalDateTime dateTimeTryOne = LocalDateTime.parse(time, formatter);
                return  dateTimeTryOne;
            } catch (DateTimeParseException x) {
                LocalDateTime dateTimeTryTwo = LocalDateTime.parse(time + " 00:00", formatter);
                return dateTimeTryTwo;
            }
        } catch (DateTimeParseException p) {
            throw new IllegalArgumentException("Failed withDate format. Please enter *d.MM.yyyy HH:mm* if you want create Event." +
                    " Please enter *d.MM.yyyy* if you want create Event.");
        }
    }
}
