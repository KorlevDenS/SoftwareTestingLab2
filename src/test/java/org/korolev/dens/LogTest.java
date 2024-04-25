package org.korolev.dens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.korolev.dens.functions.Ln;
import org.korolev.dens.functions.Log;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogTest {

    @Mock
    private Ln mockLn;
    @Spy
    private Ln spyLn;

    @Test
    void callLnCalculate() {
        Log log = new Log(spyLn,5);
        log.calculate(new BigDecimal(15), new BigDecimal("0.0001"));
        verify(spyLn, times(2)).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void calcPositiveWithMock() {
        when(mockLn.calculate(eq(new BigDecimal(100)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("4.6051657"));
        when(mockLn.calculate(eq(new BigDecimal(3)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("1.0986122"));

        Log log = new Log(mockLn, 3);
        BigDecimal expected = new BigDecimal("4.1918028");
        assertEquals(expected, log.calculate(new BigDecimal(100), new BigDecimal("0.0000001")));
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 0, 1})
    void createLogWithZeroOneOrNegativeBase(int param) {
        assertThrows(IllegalArgumentException.class, () -> new Log(param));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.02, 1, 2.256, 15, 50.543, 200})
    void calcWithPositive(double param) {
        int[] bases = {2, 3, 5, 10};
        for (int base : bases) {
            Log ln = new Log(base);
            assertEquals(Math.log(param) / Math.log(base),
                    ln.calculate(BigDecimal.valueOf(param), new BigDecimal("0.000000001")).doubleValue(), 0.0001);
        }
    }

}
