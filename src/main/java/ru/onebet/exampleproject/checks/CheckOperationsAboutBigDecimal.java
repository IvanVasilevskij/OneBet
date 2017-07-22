package ru.onebet.exampleproject.checks;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CheckOperationsAboutBigDecimal {

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
        BigDecimal zero = new BigDecimal("0.00");
        if (zero.max(amountBd) == zero) throw new IllegalArgumentException("amount can't be less thar 0");
        return amountBd;
    }
}
