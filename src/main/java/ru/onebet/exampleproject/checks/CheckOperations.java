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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
            return dateTime;
        } catch (DateTimeParseException p) {
            throw new IllegalArgumentException("Failed date format. Need dd.MM.yyyy hh:mm");
        }
    }
}
