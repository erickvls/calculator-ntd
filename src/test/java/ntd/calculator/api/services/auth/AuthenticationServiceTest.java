package ntd.calculator.api.services.auth;

import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.requests.auth.AuthenticationRequest;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.repositories.AccountRepository;
import ntd.calculator.api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtServiceImpl jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void shouldRegisterUser() {
        var request = new AuthenticationRequest("user", "password");
        var user = User.builder()
                .username("user")
                .password("encodedPassword")
                .status(true)
                .build();

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.createToken(any(UserDetails.class))).thenReturn("jwtToken");
        when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        var response = authenticationService.register(request);

        assertEquals("jwtToken", response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUserAlreadyExists() {
        // Arrange
        var request = new AuthenticationRequest("user", "password");

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> authenticationService.register(request));
    }

    @Test
    void shouldAuthenticateUser() {
        var request = new AuthenticationRequest("user", "password");
        var user = User.builder()
                .username("user")
                .password("encodedPassword")
                .status(true)
                .build();

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.createToken(any(UserDetails.class))).thenReturn("jwtToken");

        var response = authenticationService.authenticate(request);

        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void shouldThrowBadCredentialsExceptionWhenCredentialsAreInvalid() {
        // Arrange
        var request = new AuthenticationRequest("invalidUser", "invalidPassword");

        doThrow(new BadCredentialsException("Invalid username or password"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(request));

        verify(userRepository, never()).findByUsername(anyString());
    }
}

