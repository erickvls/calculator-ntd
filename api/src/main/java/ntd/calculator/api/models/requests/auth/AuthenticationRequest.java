package ntd.calculator.api.models.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "Username cannot be blank")
    @Email(message = "Username must be a valid email")
    private String username;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
