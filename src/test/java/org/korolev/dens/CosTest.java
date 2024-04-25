package org.korolev.dens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.korolev.dens.functions.Cos;
import org.korolev.dens.functions.Sin;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CosTest {

    private final BigDecimal precision = new BigDecimal("0.000000001");
    private final Cos cos = new Cos();
    @Mock
    private Sin mockSin;
    @Spy
    private Sin spySin;

    @Test
    void callSinCalculate() {
        Cos cos = new Cos(spySin);
        cos.calculate(new BigDecimal(15), new BigDecimal("0.0001"));
        verify(spySin, times(1)).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void calcWithMock() {
        when(mockSin.calculate(eq(new BigDecimal(100).add(new BigDecimal(Math.PI / 2))), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("0.8623186"));
        Cos cos = new Cos(mockSin);
        assertEquals(Math.cos(100), cos.calculate(new BigDecimal(100),
                new BigDecimal("0.0000001")).doubleValue(), 0.00001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200, -112, -77, -15, -2, -1, 0, 1, 15, 77, 114, 250})
    void calcWithIntParams(double param) {
        assertEquals(Math.cos(param), cos.calculate(BigDecimal.valueOf(param), precision).doubleValue(), 0.00001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.991, -112.05, -77.1, -15.4567, -2.23, -0.01, 0.432, 1.0304, 15.432, 77.235, 114.9093, 250.8})
    void calcWithParams(double param) {
        assertEquals(Math.cos(param), cos.calculate(BigDecimal.valueOf(param), precision).doubleValue(), 0.00001);
    }

    @Test
    void calsCosOfPi() {
        assertEquals(Math.cos(Math.PI), cos.calculate(BigDecimal.valueOf(Math.PI), precision).doubleValue(), 0.00001);
    }

}
