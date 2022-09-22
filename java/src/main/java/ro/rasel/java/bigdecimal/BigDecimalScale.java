package ro.rasel.java.bigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

public class BigDecimalScale {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(123.123456789d);
        System.out.println(MessageFormat.format("unscaled value is {0}", bigDecimal.toString()));
        for (int i = 0; i < 10; i++) {
            final BigDecimal scaled = bigDecimal.setScale(i, RoundingMode.HALF_DOWN);
            System.out.println(MessageFormat.format("scaled to {0} is {1}", i, scaled.toString()));
        }
    }
}
