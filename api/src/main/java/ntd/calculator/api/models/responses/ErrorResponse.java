package ntd.calculator.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private ErrorDetails errors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetails {
        private String type;
        private List<FieldError> field;

        @Data
        @AllArgsConstructor
        public static class FieldError {
            private String name;
            private String message;
        }
    }
}