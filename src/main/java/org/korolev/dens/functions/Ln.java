package org.korolev.dens.functions;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ln extends RowDecomposedFunction {

    /**
     * ln((1+y)/(1-y)) = 2sum(n=0 -> n=inf)((y^(2n+1))/(2n+1))
     * **/
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        validate(x, precision);
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(String.format("Ln argument x = %s must be greater than zero", x));
        }
        BigDecimal y = calcYForTaylorRow(x, precision);
        int iteration = 0;
        BigDecimal currentVal = BigDecimal.ZERO;
        BigDecimal previousVal;
        do {
            previousVal = currentVal;
            BigDecimal stepResult = (y.pow(2 * iteration + 1)).multiply(BigDecimal.TWO)
                    .divide(BigDecimal.valueOf(2L * iteration + 1), precision.scale(), RoundingMode.HALF_EVEN);
            currentVal = currentVal.add(stepResult);
            iteration++;
        } while (continueIteration(precision, previousVal, currentVal, iteration));
        checkReachedPrecision(precision, previousVal, currentVal, iteration);
        return currentVal;
    }

    private BigDecimal calcYForTaylorRow(BigDecimal x, BigDecimal precision) {
        return (x.subtract(BigDecimal.ONE)).divide(x.add(BigDecimal.ONE), precision.scale(), RoundingMode.HALF_EVEN);
    }

}
