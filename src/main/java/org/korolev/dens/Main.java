package org.korolev.dens;


import org.korolev.dens.functions.*;

import java.io.IOException;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws IOException {

        final Cos cos = new Cos();
        CsvWriter.write(
                "src/main/java/org/korolev/dens/csv/cos.csv",
                cos,
                new BigDecimal(-1),
                new BigDecimal(1),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Sin sin = new Sin();
        CsvWriter.write(
                "src/main/java/org/korolev/dens/csv/sin.csv",
                sin,
                new BigDecimal(-1),
                new BigDecimal(1),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Tan tan = new Tan();
        CsvWriter.write(
                "src/main/java/org/korolev/dens/csv/tan.csv",
                tan,
                new BigDecimal(-1),
                new BigDecimal(1),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Cot cot = new Cot();
        CsvWriter.write(
                "src/main/java/org/korolev/dens/csv/tan.csv",
                cot,
                new BigDecimal(1),
                new BigDecimal(2),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Ln ln = new Ln();
        CsvWriter.write(
                "src/main/java/org/korolev/dens/csv/ln.csv",
                ln,
                new BigDecimal(1),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.000000001"));

        final Log log2 = new Log(2);
        CsvWriter.write(
                "src/main/java/org/korolev/dens/csv/log3.csv",
                log2,
                new BigDecimal(1),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001"));

        final Log log3 = new Log(3);
        CsvWriter.write(
                "src/main/java/org/korolev/dens/csv/log5.csv",
                log3,
                new BigDecimal(1),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001"));

        final Log log5 = new Log(5);
        CsvWriter.write(
                "src/main/java/org/korolev/dens/csv/log10.csv",
                log5,
                new BigDecimal(1),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001"));

        final FunctionSystem func = new FunctionSystem();
        CsvWriter.write(
                "src/main/java/org/korolev/dens/csv/func.csv",
                func,
                new BigDecimal(2),
                new BigDecimal(5),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001"));

    }
}