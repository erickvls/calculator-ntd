package ntd.calculator.api.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiplicationStrategyTest {

    private final CalculatorStrategy multiplicationStrategy = new MultiplicationStrategy();

    @Test
    void shouldReturnProductOfOperands() {
        var firstOperand = BigDecimal.valueOf(10);
        var secondOperand = BigDecimal.valueOf(20);

        var result = multiplicationStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(200), result);
    }

    @Test
    void shouldReturnZeroWhenOneOperandIsZero() {
        var firstOperand = BigDecimal.valueOf(0);
        var secondOperand = BigDecimal.valueOf(20);

        var result = multiplicationStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void shouldReturnNegativeProductForMixedSigns() {
        var firstOperand = BigDecimal.valueOf(-10);
        var secondOperand = BigDecimal.valueOf(20);

        var result = multiplicationStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(-200), result);
    }
}