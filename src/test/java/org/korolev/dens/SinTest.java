package org.korolev.dens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.korolev.dens.functions.Sin;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinTest {

    private final BigDecimal precision = new BigDecimal("0.000000001");
    private final Sin sin = new Sin();

    @ParameterizedTest
    @ValueSource(doubles = {-200, -112, -77, -15, -2, -1, 0, 1, 15, 77, 114, 250})
    void calcWithIntParams(double param) {
        assertEquals(Math.sin(param), sin.calculate(BigDecimal.valueOf(param), precision).doubleValue(), 0.00001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.991, -112.05, -77.1, -15.4567, -2.23, -0.01, 0.432, 1.0304, 15.432, 77.235, 114.9093, 250.8})
    void calcWithParams(double param) {
        assertEquals(Math.sin(param), sin.calculate(BigDecimal.valueOf(param), precision).doubleValue(), 0.00001);
    }

    @Test
    void calsSinOfPi() {
        assertEquals(Math.sin(Math.PI), sin.calculate(BigDecimal.valueOf(Math.PI), precision).doubleValue(), 0.00001);
    }

}
