package ntd.calculator.api.strategy;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.enums.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StrategyContext {
    private final Map<OperationType, CalculatorStrategy> strategyMap = new EnumMap<>(OperationType.class);

    @Autowired
    public StrategyContext(AdditionStrategy additionStrategy,
                           SubtractionStrategy subtractionStrategy) {
        // Register strategies
        strategyMap.put(OperationType.ADDITION, additionStrategy);
        strategyMap.put(OperationType.SUBTRACTION, subtractionStrategy);
    }

    public CalculatorStrategy getStrategy(OperationType operationType) {
        CalculatorStrategy strategy = strategyMap.get(operationType);
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid operation type: " + operationType);
        }
        return strategy;
    }
}
