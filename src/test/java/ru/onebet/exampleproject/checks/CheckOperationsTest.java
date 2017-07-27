package ru.onebet.exampleproject.checks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CheckOperationsTest {

    @Autowired
    private CheckOperations checkOperations;

    @Test
    public void testTryToParseBigDecimalFromString() throws Exception {
        BigDecimal bdOne = checkOperations.tryToParseBigDecimalFromString("250.0");
        BigDecimal bdTwo = checkOperations.tryToParseBigDecimalFromString("350.000");
        BigDecimal bdThree = checkOperations.tryToParseBigDecimalFromString("200");
        BigDecimal bdFour = checkOperations.tryToParseBigDecimalFromString("50.00");

        assertEquals(new BigDecimal("250.00"), bdOne);
        assertEquals(new BigDecimal("350.00"), bdTwo);
        assertEquals(new BigDecimal("200.00"), bdThree);
        assertEquals(new BigDecimal("50.00"), bdFour);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToParseBigDecimalFromStringWithIllegalErgument() {
        BigDecimal bdOne = checkOperations.tryToParseBigDecimalFromString("asd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToParseBigDecimalFromStringWithNegativeArgument() {
        BigDecimal bdOne = checkOperations.tryToParseBigDecimalFromString("-50.00");
    }

    @Test
    public void testTryToParseDateFromString() throws Exception {
        LocalDateTime neededDate = checkOperations.tryToParseDateFromString("25.05.2015 16:30");

        assertEquals(2015, neededDate.getYear());
        assertEquals(5, neededDate.getMonth().getValue());
        assertEquals(25, neededDate.getDayOfMonth());
        assertEquals(16, neededDate.getHour());
        assertEquals(30, neededDate.getMinute());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToParseDateFromStringWithIllegalArgument() {
        LocalDateTime neededDate = checkOperations.tryToParseDateFromString("asd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToParseDateFromStringWithIllegalPattern() {
        LocalDateTime neededDate = checkOperations.tryToParseDateFromString("25.05.15 16:30");
    }
}
