package ru.onebet.exampleproject.checks;

import org.springframework.stereotype.Component;
import ru.onebet.exampleproject.model.ComandOfDota;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CheckOperations {

    public BigDecimal tryToParseBigDecimalFromString(String amount) {
        try {
            BigDecimal amountBd = new BigDecimal(amount);
            amountBd = amountBd.setScale(2,BigDecimal.ROUND_HALF_DOWN);
            return  amountBd;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect amount antered. Please enter in format as 00.00");
        }
    }

    public BigDecimal beeSureThatAmountMoreThenZero(String amount) {
        BigDecimal amountBd = tryToParseBigDecimalFromString(amount);
        if (BigDecimal.ZERO.max(amountBd) == BigDecimal.ZERO) throw new IllegalArgumentException("amount can't be less thar 0");
        return amountBd;
    }

    public Date tryToParseDateFromString(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date date = null;
        try {
            date = sdf.parse(time);
            return date;
        } catch (ParseException p) {
            throw new IllegalArgumentException("Failed date format. Need dd.MM.yyyy hh:mm");
        }
    }
}
