package ntd.calculator.api.services.auth;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.enums.TokenType;
import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.requests.auth.AuthenticationRequest;
import ntd.calculator.api.models.requests.auth.AuthenticationResponse;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.repositories.AccountRepository;
import ntd.calculator.api.repositories.UserRepository;
import ntd.calculator.api.services.AuthenticationService;
import ntd.calculator.api.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static ntd.calculator.api.utility.CalculatorConstants.INITIAL_VALUE;

/**
 * Service responsible for handling user registration and authentication.
 * It manages user creation, account initialization, and JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(AuthenticationRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(true)
                .build();
        userRepository.save(user);

        var account = Account.builder()
                .user(user)
                .balance(new BigDecimal(INITIAL_VALUE))
                .build();
        accountRepository.save(account);
        return createAuthenticationResponse(user);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return createAuthenticationResponse(user);
    }

    private AuthenticationResponse createAuthenticationResponse(UserDetails user) {
        var jwtToken = jwtService.createToken(user);
        return AuthenticationResponse.builder()
                .tokenType(TokenType.BEARER)
                .token(jwtToken)
                .build();
    }
}
