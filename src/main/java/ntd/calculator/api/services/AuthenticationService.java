package ntd.calculator.api.services;

import ntd.calculator.api.models.requests.auth.AuthenticationRequest;
import ntd.calculator.api.models.requests.auth.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(AuthenticationRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
