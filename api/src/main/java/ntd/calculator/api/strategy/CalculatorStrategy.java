package ntd.calculator.api.strategy;

import java.math.BigDecimal;

public interface CalculatorStrategy {
    BigDecimal calculate(BigDecimal firstOperand, BigDecimal secondOperand);
}
