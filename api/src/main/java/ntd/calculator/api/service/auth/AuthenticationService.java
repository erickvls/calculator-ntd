package ntd.calculator.api.service.auth;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ntd.calculator.api.dto.auth.AuthenticationRequest;
import ntd.calculator.api.dto.auth.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public AuthenticationResponse register(AuthenticationRequest request) {
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
