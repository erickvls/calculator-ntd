package ntd.calculator.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import ntd.calculator.api.exceptions.OperationNotFoundException;

public enum OperationType {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    SQUARE_ROOT,
    RANDOM_STRING;


    @JsonCreator
    public static OperationType fromString(String value) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.name().equalsIgnoreCase(value)) {
                return operationType;
            }
        }
        throw new OperationNotFoundException(String.format("Invalid operation type: %s. Accepted values: %s",
                value, String.join(", ",
                        ADDITION.name(), SUBTRACTION.name(), MULTIPLICATION.name(), DIVISION.name(), SQUARE_ROOT.name(), RANDOM_STRING.name())));
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
