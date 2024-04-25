package org.korolev.dens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.korolev.dens.functions.Ln;
import org.korolev.dens.functions.Sin;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FunctionSystemTest {

    private final BigDecimal precision = new BigDecimal("0.00000000001");
    @Mock
    private Sin mockSin;
    @Mock
    private Ln mockLn;

    @Test
    void callWithNullArgument() {
        FunctionSystem fs = new FunctionSystem();
        assertThrows(NullPointerException.class, () -> fs.calculate(null, precision));
    }

    @Test
    void callWithNullPrecision() {
        FunctionSystem fs = new FunctionSystem();
        assertThrows(NullPointerException.class, () -> fs.calculate(new BigDecimal(15), null));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-2, 0, 1, 2})
    void callWithInvalidPrecision(double precision) {
        FunctionSystem fs = new FunctionSystem();
        assertThrows(IllegalArgumentException.class,
                () -> fs.calculate(new BigDecimal(15), BigDecimal.valueOf(precision)));
    }

    @Test
    void calcWithZeroSin() {
        FunctionSystem fs = new FunctionSystem();
        when(mockSin.calculate(eq(new BigDecimal(-15)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("0.00"));
        fs.setSin(mockSin);
        assertThrows(ArithmeticException.class, () -> fs.calculate(new BigDecimal(-15), precision));
    }

    @Test
    void calcWithZeroLn() {
        FunctionSystem fs = new FunctionSystem();
        when(mockLn.calculate(eq(new BigDecimal(15)), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("0.00"));
        fs.setLn(mockLn);
        assertThrows(ArithmeticException.class, () -> fs.calculate(new BigDecimal(15), precision));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/values.csv", numLinesToSkip = 1, delimiter = ';')
    void calcWithNormalValues(double paramX, double paramY) {
        FunctionSystem fs = new FunctionSystem();
        assertEquals(fs.calculate(BigDecimal.valueOf(paramX), precision).doubleValue(), paramY, 0.00001);
    }

}
