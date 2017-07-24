package ru.onebet.exampleproject.checks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CheckOperationsTest {

    @Autowired
    private CheckOperations sCheck;

    @Test
    public void testTryToParseBigDecimalFromString() throws Exception {
        BigDecimal bdOne = sCheck.tryToParseBigDecimalFromString("250.0");
        BigDecimal bdTwo = sCheck.tryToParseBigDecimalFromString("350.000");
        BigDecimal bdThree = sCheck.tryToParseBigDecimalFromString("200");
        BigDecimal bdFour = sCheck.tryToParseBigDecimalFromString("50.00");

        assertEquals(new BigDecimal("250.00"),bdOne);
        assertEquals(new BigDecimal("350.00"),bdTwo);
        assertEquals(new BigDecimal("200.00"),bdThree);
        assertEquals(new BigDecimal("50.00"),bdFour);

    }

    @Test
    public void testBeeSureThatAmountMoreThenZero() throws Exception {
        BigDecimal bdOne = sCheck.beeSureThatAmountMoreThenZero("25.00");
        BigDecimal bdTwo = sCheck.beeSureThatAmountMoreThenZero("50.0");

        assertEquals(new BigDecimal("25.00"),bdOne);
        assertEquals(new BigDecimal("50.00"),bdTwo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBeeSureThatAmountMoreThenZeroWithExceprion() {
        BigDecimal bdOne = sCheck.beeSureThatAmountMoreThenZero("-25.00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToParseBigDecimalFromStringWithExceprion() {
        BigDecimal bdOne = sCheck.beeSureThatAmountMoreThenZero("asd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToParseDateFromString() {
        Date date = sCheck.tryToParseDateFromString("asd");
    }
}
