package ntd.calculator.api.strategy;

import ntd.calculator.api.enums.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class StrategyContext {

    private final Map<OperationType, CalculatorStrategy> strategyMap = new EnumMap<>(OperationType.class);

    /**
     * Constructor for StrategyContext.
     * This constructor injects and registers all available strategies for different operations.
     *
     * @param additionStrategy      Strategy for addition operation.
     * @param subtractionStrategy   Strategy for subtraction operation.
     * @param multiplicationStrategy Strategy for multiplication operation.
     * @param divisionStrategy      Strategy for division operation.
     * @param squareRootStrategy    Strategy for square root operation.
     */
    @Autowired
    public StrategyContext(AdditionStrategy additionStrategy,
                           SubtractionStrategy subtractionStrategy,
                           MultiplicationStrategy multiplicationStrategy,
                           DivisionStrategy divisionStrategy,
                           SquareRootStrategy squareRootStrategy) {
        // Register strategies with corresponding operation types
        strategyMap.put(OperationType.ADDITION, additionStrategy);
        strategyMap.put(OperationType.SUBTRACTION, subtractionStrategy);
        strategyMap.put(OperationType.MULTIPLICATION, multiplicationStrategy);
        strategyMap.put(OperationType.DIVISION, divisionStrategy);
        strategyMap.put(OperationType.SQUARE_ROOT, squareRootStrategy);
    }

    /**
     * Retrieves the appropriate CalculatorStrategy based on the operation type.
     *
     * @param operationType The type of operation (e.g., ADDITION, SUBTRACTION, etc.).
     * @return The CalculatorStrategy associated with the given operation type.
     * @throws IllegalArgumentException if the operation type is not valid or not registered.
     */
    public CalculatorStrategy getStrategy(OperationType operationType) {
        CalculatorStrategy strategy = strategyMap.get(operationType);
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid operation type: " + operationType);
        }
        return strategy;
    }
}