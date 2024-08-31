package ntd.calculator.api.services;

import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.strategy.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StrategyContextTest {

    @Mock
    private AdditionStrategy additionStrategy;

    @Mock
    private SubtractionStrategy subtractionStrategy;

    @Mock
    private MultiplicationStrategy multiplicationStrategy;

    @Mock
    private DivisionStrategy divisionStrategy;

    @Mock
    private SquareRootStrategy squareRootStrategy;

    @InjectMocks
    private StrategyContext strategyContext;

    @Test
    void shouldGetAdditionStrategy() {
        CalculatorStrategy strategy = strategyContext.getStrategy(OperationType.ADDITION);
        assertNotNull(strategy);
        assertEquals(additionStrategy, strategy);
    }

    @Test
    void shouldGetSubtractionStrategy() {
        CalculatorStrategy strategy = strategyContext.getStrategy(OperationType.SUBTRACTION);
        assertNotNull(strategy);
        assertEquals(subtractionStrategy, strategy);
    }

    @Test
    void shouldGetMultiplicationSubtraction() {
        CalculatorStrategy strategy = strategyContext.getStrategy(OperationType.MULTIPLICATION);
        assertNotNull(strategy);
        assertEquals(multiplicationStrategy, strategy);
    }


    @Test
    void shouldGetDivisionSubtraction() {
        CalculatorStrategy strategy = strategyContext.getStrategy(OperationType.DIVISION);
        assertNotNull(strategy);
        assertEquals(divisionStrategy, strategy);
    }

    @Test
    void shouldGetSquareRootSubtraction() {
        CalculatorStrategy strategy = strategyContext.getStrategy(OperationType.SQUARE_ROOT);
        assertNotNull(strategy);
        assertEquals(squareRootStrategy, strategy);
    }

    @Test
    void testGetStrategy_InvalidOperationType() {
        assertThrows(IllegalArgumentException.class, () -> strategyContext.getStrategy(null));
    }
}
