package org.korolev.dens;

import lombok.Getter;
import lombok.Setter;
import org.korolev.dens.functions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class FunctionSystem extends RowDecomposedFunction{

    private Sin sin;
    private Cos cos;
    private Cot cot;

    private Ln ln;
    private Log log2;
    private Log log3;
    private Log log5;

    public FunctionSystem() {
        sin = new Sin();
        cos = new Cos();
        cot = new Cot();
        ln = new Ln();
        log2 = new Log(2);
        log3 = new Log(3);
        log5 = new Log(5);
    }

    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            BigDecimal sinVal = this.sin.calculate(x, precision);
            if (sinVal.compareTo(BigDecimal.ZERO) == 0) {
                throw new ArithmeticException(String.format("Division by zero: sin(%s) = 0", x));
            }
            BigDecimal cosVal = this.cos.calculate(x, precision);
            BigDecimal cotVal = this.cot.calculate(x, precision);
            return (cotVal.divide(sinVal, precision.scale(), RoundingMode.HALF_EVEN))
                    .subtract(cotVal)
                    .subtract(cosVal);
        } else {
            BigDecimal lnVal = this.ln.calculate(x, precision);
            if (lnVal.compareTo(BigDecimal.ZERO) == 0) {
                throw new ArithmeticException(String.format("Division by zero: ln(%s) = 0", x));
            }
            BigDecimal log2Val = this.log2.calculate(x, precision);
            BigDecimal log3Val = this.log3.calculate(x, precision);
            BigDecimal log5Val = this.log5.calculate(x, precision);
            return (log2Val.divide(lnVal, precision.scale(), RoundingMode.HALF_EVEN))
                    .add(log3Val)
                    .pow(2)
                    .pow(3)
                    .add(log5Val.multiply(log5Val))
                    .setScale(precision.scale(), RoundingMode.HALF_EVEN);
        }
    }

}
