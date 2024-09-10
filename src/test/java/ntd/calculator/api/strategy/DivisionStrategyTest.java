package ntd.calculator.api.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DivisionStrategyTest {

    private final CalculatorStrategy divisionStrategy = new DivisionStrategy();

    @Test
    void shouldReturnQuotientOfOperands() {
        var firstOperand = BigDecimal.valueOf(10);
        var secondOperand = BigDecimal.valueOf(2);

        var result = divisionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(5).setScale(0, RoundingMode.HALF_UP), result);
    }

    @Test
    void shouldReturnCorrectQuotientForDecimalOperands() {
        var firstOperand = BigDecimal.valueOf(10);
        var secondOperand = BigDecimal.valueOf(3);

        var result = divisionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(3), result);
    }

    @Test
    void shouldThrowExceptionForDivisionByZero() {
        var firstOperand = BigDecimal.valueOf(10);
        var secondOperand = BigDecimal.ZERO;

        var exception = assertThrows(ArithmeticException.class, () -> {
            divisionStrategy.calculate(firstOperand, secondOperand);
        });

        assertEquals("Not possible divide a number by 0", exception.getMessage());
    }

    @Test
    void shouldReturnNegativeQuotientForMixedSigns() {
        var firstOperand = BigDecimal.valueOf(-10);
        var secondOperand = BigDecimal.valueOf(2);

        var result = divisionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(-5).setScale(0, RoundingMode.HALF_UP), result);
    }

    @Test
    void shouldReturnCorrectQuotientForNegativeOperands() {
        var firstOperand = BigDecimal.valueOf(-10);
        var secondOperand = BigDecimal.valueOf(-2);

        var result = divisionStrategy.calculate(firstOperand, secondOperand);

        assertEquals(BigDecimal.valueOf(5).setScale(0, RoundingMode.HALF_UP), result);
    }
}