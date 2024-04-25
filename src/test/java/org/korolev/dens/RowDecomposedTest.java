package org.korolev.dens;


import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.korolev.dens.functions.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RowDecomposedTest {

    private final BigDecimal precision = new BigDecimal("0.00000000001");

    @ParameterizedTest
    @MethodSource("functions")
    void calcWithNullArgument(RowDecomposedFunction function) {
        assertThrows(NullPointerException.class, () -> function.calculate(null, precision));
    }

    @ParameterizedTest
    @MethodSource("functions")
    void calcWithNullPrecision(RowDecomposedFunction function) {
        assertThrows(NullPointerException.class, () -> function.calculate(new BigDecimal(15), null));
    }

    @ParameterizedTest
    @MethodSource("functions")
    void calcWithInvalidPrecision(RowDecomposedFunction function) {
        assertThrows(IllegalArgumentException.class, () -> function.calculate(new BigDecimal(15), new BigDecimal(0)));
        assertThrows(IllegalArgumentException.class, () -> function.calculate(new BigDecimal(15), new BigDecimal(2)));
    }

    @ParameterizedTest
    @MethodSource("baseFunctions")
    void calcWithDissimilarRow(RowDecomposedFunction function) {
        function.setMaxIterations(100);
        assertThrows(ArithmeticException.class, () -> function.calculate(new BigDecimal(1000), precision));
    }

    private Stream<Arguments> functions() {
        return Stream.of(
                Arguments.of(new Sin()),
                Arguments.of(new Cos()),
                Arguments.of(new Tan()),
                Arguments.of(new Ln()),
                Arguments.of(new Log(2)),
                Arguments.of(new Log(3)),
                Arguments.of(new Log(5)));
    }

    private Stream<Arguments> baseFunctions() {
        return Stream.of(
                Arguments.of(new Sin()),
                Arguments.of(new Ln()));
    }

}
