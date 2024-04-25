package org.korolev.dens.functions;

import java.math.BigDecimal;

public class Cos extends RowDecomposedFunction {

    private final Sin sin;

    public Cos() {
        this.sin = new Sin();
    }

    public Cos(Sin sin) {
        this.sin = sin;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        validate(x, precision);
        BigDecimal turnedX = x.add(new BigDecimal(Math.PI / 2));
        return sin.calculate(turnedX, precision);
    }


}
