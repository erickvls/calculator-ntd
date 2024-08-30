package ntd.calculator.api.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;

@Component
public class SquareRootStrategy implements CalculatorStrategy{

    /**
     * Calculates the square root of the first operand.
     *
     * @param firstOperand  The number for which the square root will be calculated.
     * @param secondOperand This parameter is not used in this operation and can be ignored.
     * @return The square root of the first operand.
     * @throws IllegalArgumentException if the first operand is negative.
     */
    @Override
    public BigDecimal calculate(BigDecimal firstOperand, BigDecimal secondOperand) {
        if (firstOperand.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of a negative number");
        }
        // DECIMAL64 define the operation round
        return firstOperand.sqrt(MathContext.DECIMAL64);
    }
}
