package ntd.calculator.api.config;

import jakarta.validation.Valid;
import lombok.Data;
import ntd.calculator.api.config.properties.Jwt;
import ntd.calculator.api.config.properties.RandomString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "app")
@Validated
public class ConfigProperties {

    @Valid
    private Jwt jwt;

    @Valid
    private RandomString randomString;
}
