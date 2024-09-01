package ntd.calculator.api.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtractionStrategyTest {

    private final CalculatorStrategy subtractionStrategy = new SubtractionStrategy();

    @Test
    void shouldReturnDifferenceOfOperands() {
        var firstOperand = BigDecimal.valueOf(20);
        var secondOperand = BigDecimal.valueOf(10);

        var result = subtractionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(10), result);
    }

    @Test
    void shouldReturnNegativeDifferenceWhenSecondOperandIsLarger() {
        var firstOperand = BigDecimal.valueOf(10);
        var secondOperand = BigDecimal.valueOf(20);

        var result = subtractionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(-10), result);
    }

    @Test
    void shouldReturnNegativeDifferenceForMixedSigns() {
        var firstOperand = BigDecimal.valueOf(-10);
        var secondOperand = BigDecimal.valueOf(+20);

        var result = subtractionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(-30), result);
    }

    @Test
    void shouldReturnNegativeDifferenceForEqualSigns() {
        var firstOperand = BigDecimal.valueOf(-1);
        var secondOperand = BigDecimal.valueOf(-5);

        var result = subtractionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(4), result);
    }
}