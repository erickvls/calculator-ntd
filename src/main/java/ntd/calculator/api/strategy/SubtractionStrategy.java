package ntd.calculator.api.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SubtractionStrategy implements CalculatorStrategy {

    /**
     * Subtracts the second operand from the first operand.
     *
     * @param firstOperand  The minuend.
     * @param secondOperand The subtrahend.
     * @return The result of the subtraction.
     */
    @Override
    public BigDecimal calculate(BigDecimal firstOperand, BigDecimal secondOperand) {
        return firstOperand.subtract(secondOperand);
    }
}
