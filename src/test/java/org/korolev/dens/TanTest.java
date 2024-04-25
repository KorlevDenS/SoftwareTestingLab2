package org.korolev.dens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.korolev.dens.functions.Cos;
import org.korolev.dens.functions.Sin;
import org.korolev.dens.functions.Tan;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TanTest {

    private final BigDecimal precision = new BigDecimal("0.00000000001");
    private final Tan tg = new Tan();
    @Spy
    private Cos spyCos;
    @Spy
    private Sin spySin;
    @Mock
    private Cos mockCos;
    @Mock
    private Sin mockSin;

    @Test
    void callSinCosCalculate() {
        Tan tan = new Tan(spySin, spyCos);
        tan.calculate(new BigDecimal(15), precision);
        verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void calcWithMockSinCos() {
        when(mockSin.calculate(eq(new BigDecimal(15)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("0.6502876"));
        when(mockCos.calculate(eq(new BigDecimal(15)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("-0.7596876"));
        Tan tan = new Tan(mockSin, mockCos);
        assertEquals(BigDecimal.valueOf(-0.8559934), tan.calculate(new BigDecimal(15), new BigDecimal("0.0000001")));
    }

    @Test
    void calcWithZeroCos() {
        when(mockSin.calculate(eq(new BigDecimal(15)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("0.6502876"));
        when(mockCos.calculate(eq(new BigDecimal(15)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("0.00"));
        Tan tan = new Tan(mockSin, mockCos);
        assertThrows(ArithmeticException.class, () -> tan.calculate(BigDecimal.valueOf(15), new BigDecimal("0.0000001")));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200, -112, -77, -15, -2, -1, 0, 1, 15, 77, 114, 250})
    void calcWithIntParams(double param) {
        assertEquals(Math.tan(param), tg.calculate(BigDecimal.valueOf(param), precision).doubleValue(), 0.00001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.991, -112.05, -77.1, -15.4567, -2.23, -0.01, 0.432, 1.0304, 15.432, 77.235, 114.9093, 250.8})
    void calcWithParams(double param) {
        assertEquals(Math.tan(param), tg.calculate(BigDecimal.valueOf(param), precision).doubleValue(), 0.00001);
    }

}
