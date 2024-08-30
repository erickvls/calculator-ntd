package ntd.calculator.api.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MultiplicationStrategy implements CalculatorStrategy{
    @Override
    public BigDecimal calculate(BigDecimal firstOperand, BigDecimal secondOperand) {
        return firstOperand.multiply(secondOperand);
    }
}
