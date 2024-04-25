package org.korolev.dens.functions;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tan extends RowDecomposedFunction {

    private final Sin sin;
    private final Cos cos;

    public Tan() {
        sin = new Sin();
        cos = new Cos();
    }

    public Tan(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        validate(x, precision);
        BigDecimal sinVal = sin.calculate(x, precision);
        BigDecimal cosVal = cos.calculate(x, precision);
        if (cosVal.compareTo(sinVal) == 0) {
            throw new ArithmeticException(String.format("tan(%s) does not exist, cos(%s) = 0", x, x));
        }
        return sinVal.divide(cosVal, precision.scale(), RoundingMode.HALF_EVEN);
    }

}
