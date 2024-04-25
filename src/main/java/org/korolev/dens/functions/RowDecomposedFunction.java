package org.korolev.dens.functions;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class RowDecomposedFunction {

    private int maxIterations = 10000;

    public abstract BigDecimal calculate(final BigDecimal x, final BigDecimal precision);

    protected void validate(final BigDecimal x, final BigDecimal precision) {
        Objects.requireNonNull(x, "Argument 'x' must not be null");
        Objects.requireNonNull(precision, "Argument 'precision' must not be null");
        if (precision.compareTo(BigDecimal.ZERO) <= 0 || precision.compareTo(BigDecimal.ONE) >= 0) {
            throw new IllegalArgumentException("Argument 'precision' must be between 0 and 1");
        }
    }

    protected boolean continueIteration(BigDecimal precision, BigDecimal previousVal, BigDecimal currentVal, int i) {
        return precision.compareTo((previousVal.subtract(currentVal)).abs()) < 0 && i < this.maxIterations;
    }

    protected void checkReachedPrecision(BigDecimal precision, BigDecimal previousVal, BigDecimal currentVal, int i) {
        if (precision.compareTo((previousVal.subtract(currentVal)).abs()) >= 0) {
            return;
        }
        throw new ArithmeticException(String.format(
                "Precision was not reached. Iterations: %d. MaxIterations: %d, Result: %s",
                i, this.maxIterations, currentVal));
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }
}
