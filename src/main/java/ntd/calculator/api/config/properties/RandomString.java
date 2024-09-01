package ntd.calculator.api.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RandomString {
    @NotBlank
    private String clientUri;

    @NotBlank
    private String apiKey;
}
