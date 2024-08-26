package ntd.calculator.api.service.auth;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ntd.calculator.api.Role;
import ntd.calculator.api.dto.auth.AuthenticationRequest;
import ntd.calculator.api.dto.auth.AuthenticationResponse;
import ntd.calculator.api.model.user.User;
import ntd.calculator.api.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(AuthenticationRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(true)
                .build();
        repository.save(user);
        return createAuthenticationResponse(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
        );
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        return createAuthenticationResponse(user);
    }

    private AuthenticationResponse createAuthenticationResponse(UserDetails user){
        var jwtToken = jwtService.createToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
