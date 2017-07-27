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
            DateTimeFormatter formatterForCreate = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            DateTimeFormatter formatterForFind = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDateTime dateTimeTryOne = LocalDateTime.parse(time, formatterForCreate);
                return  dateTimeTryOne;
            } catch (DateTimeParseException p) {
                LocalDateTime dateTimeTryTwo = LocalDateTime.parse(time, formatterForFind);
                return dateTimeTryTwo;
            }
        } catch (DateTimeParseException p) {
            throw new IllegalArgumentException("Failed date format. Please enter *dd.MM.yyyy HH:mm* if you want create Event." +
                    " Please enter *dd.MM.yyyy* if you want create Event.");
        }
    }
}
