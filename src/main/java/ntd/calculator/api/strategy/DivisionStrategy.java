package ntd.calculator.api.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DivisionStrategy implements CalculatorStrategy {

    /**
     * Divides the first operand by the second operand.
     *
     * @param firstOperand  The dividend.
     * @param secondOperand The divisor. Must not be zero.
     * @return The result of the division, rounded using HALF_UP rounding mode.
     * @throws ArithmeticException if the second operand is zero.
     */
    @Override
    public BigDecimal calculate(BigDecimal firstOperand, BigDecimal secondOperand) {
        if (secondOperand.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        // Used HALF_UP for rounding the result
        return firstOperand.divide(secondOperand, RoundingMode.HALF_UP);
    }
}
