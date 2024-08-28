package ntd.calculator.api.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Jwt {
    @NotBlank
    private String secretKey;
    @NotBlank
    private long expiration;
}
