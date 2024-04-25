package org.korolev.dens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.korolev.dens.functions.Ln;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

public class LnTest {

    private final BigDecimal precision = new BigDecimal("0.000000001");

    @Test
    void calcWithZero() {
        Ln ln = new Ln();
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(BigDecimal.ZERO, precision));
    }

    @Test
    void calcWithNegative() {
        Ln ln = new Ln();
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(BigDecimal.valueOf(-1), precision));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.02, 1, 2.256, 15, 50.543, 200})
    void calcWithPositive(double param) {
        Ln ln = new Ln();
        assertEquals(Math.log(param), ln.calculate(BigDecimal.valueOf(param), precision).doubleValue(), 0.0001);
    }
}
