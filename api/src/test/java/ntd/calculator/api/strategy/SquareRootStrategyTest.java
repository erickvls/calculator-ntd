package ntd.calculator.api.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SquareRootStrategyTest {

    private final CalculatorStrategy squareRootStrategy = new SquareRootStrategy();

    @Test
    void calculate_ShouldReturnSquareRootOfOperand() {
        var firstOperand = BigDecimal.valueOf(16);
        var secondOperand = BigDecimal.ZERO; // Not used

        var result = squareRootStrategy.calculate(firstOperand, secondOperand);

        // Assert
        assertEquals(BigDecimal.valueOf(4).round(MathContext.DECIMAL64), result);
    }

    @Test
    void calculate_ShouldThrowExceptionForNegativeOperand() {
        var firstOperand = BigDecimal.valueOf(-16);
        var secondOperand = BigDecimal.ZERO; // Not used

        var exception = assertThrows(ArithmeticException.class, () -> {
            squareRootStrategy.calculate(firstOperand, secondOperand);
        });

        assertEquals("Cannot calculate square root of a negative number", exception.getMessage());
    }
}