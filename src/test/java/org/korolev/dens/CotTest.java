package org.korolev.dens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.korolev.dens.functions.Cot;
import org.korolev.dens.functions.Tan;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CotTest {

    private final BigDecimal precision = new BigDecimal("0.00000000001");
    private final Cot cot = new Cot();
    @Spy
    private Tan spyTan;
    @Mock
    private Tan mockTan;

    @Test
    void callTanCalculate() {
        Cot cot = new Cot(spyTan);
        cot.calculate(new BigDecimal(15), precision);
        verify(spyTan, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void calcWithMockTan() {
        when(mockTan.calculate(eq(new BigDecimal(15)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("-0.8559934"));
        Cot cot = new Cot(mockTan);
        assertEquals(BigDecimal.valueOf(-1.1682333), cot.calculate(new BigDecimal(15), new BigDecimal("0.0000001")));
    }

    @Test
    void calcWithZeroTan() {
        when(mockTan.calculate(eq(new BigDecimal(15)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("0.00"));
        Cot cot = new Cot(mockTan);
        assertThrows(ArithmeticException.class, () -> cot.calculate(BigDecimal.valueOf(15), new BigDecimal("0.0000001")));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200, -112, -77, -15, -2, -1, 1, 15, 77, 114, 250})
    void calcWithIntParams(double param) {
        assertEquals(1 / Math.tan(param),
                this.cot.calculate(BigDecimal.valueOf(param), precision).doubleValue(), 0.00001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.991, -112.05, -77.1, -15.4567, -2.23, -0.01, 0.432, 1.0304, 15.432, 77.235, 114.9093, 250.8})
    void calcWithParams(double param) {
        assertEquals(1 / Math.tan(param),
                this.cot.calculate(BigDecimal.valueOf(param), precision).doubleValue(), 0.00001);
    }

}
