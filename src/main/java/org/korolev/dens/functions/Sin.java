package org.korolev.dens.functions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Sin extends RowDecomposedFunction {

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        validate(x, precision);
        int iteration = 0;
        BigDecimal currentVal = BigDecimal.ZERO;
        BigDecimal previousVal;
        do {
            previousVal = currentVal;
            BigDecimal stepResult = (BigDecimal.valueOf(-1).pow(iteration))
                    .multiply(x.pow(2 * iteration + 1))
                    .divide(new BigDecimal(calculateFactorial(2 * iteration + 1)),
                            precision.scale(), RoundingMode.HALF_EVEN);
            currentVal = currentVal.add(stepResult);
            iteration++;
        } while (continueIteration(precision, previousVal, currentVal, iteration));
        checkReachedPrecision(precision, previousVal, currentVal, iteration);
        return currentVal;
    }

    private BigInteger calculateFactorial(int n) {
        if (n < 0) {
            throw new ArithmeticException(String.format("Impossible to calculate factorial of %d < 0", n));
        }
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

}
