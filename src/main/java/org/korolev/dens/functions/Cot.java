package org.korolev.dens.functions;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Cot extends RowDecomposedFunction {

    private final Tan tan;

    public Cot() {
        this.tan = new Tan();
    }

    public Cot(Tan tan) {
        this.tan = tan;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        validate(x, precision);
        BigDecimal tanVal = tan.calculate(x, precision);
        if (tanVal.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException(String.format("cot(%s) does not exits, tan(%s) = 0", x, x));
        }
        return BigDecimal.ONE.divide(tanVal, precision.scale(), RoundingMode.HALF_EVEN);
    }
}
