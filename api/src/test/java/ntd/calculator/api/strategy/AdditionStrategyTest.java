package ntd.calculator.api.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdditionStrategyTest {

    private final CalculatorStrategy additionStrategy = new AdditionStrategy();

    @Test
    void shouldReturnSumOfOperands() {
        var firstOperand = BigDecimal.valueOf(10);
        var secondOperand = BigDecimal.valueOf(20);

        var result = additionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(30), result);
    }

    @Test
    void shouldReturnCorrectSumForNegativeOperands() {
        var firstOperand = BigDecimal.valueOf(-10);
        var secondOperand = BigDecimal.valueOf(-20);

        var result = additionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(-30), result);
    }

    @Test
    void shouldReturnNegativeSumForMixedSigns() {
        var firstOperand = BigDecimal.valueOf(-10);
        var secondOperand = BigDecimal.valueOf(+20);

        var result = additionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(10), result);
    }
}