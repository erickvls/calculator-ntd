package ntd.calculator.api.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AdditionStrategy implements CalculatorStrategy {

    /**
     * Adds the two operands together.
     *
     * @param firstOperand  The first addend.
     * @param secondOperand The second addend.
     * @return The result of the addition.
     */
    @Override
    public BigDecimal calculate(BigDecimal firstOperand, BigDecimal secondOperand) {
        return firstOperand.add(secondOperand);
    }
}
