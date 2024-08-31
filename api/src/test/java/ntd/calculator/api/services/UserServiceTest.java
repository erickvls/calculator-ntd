package ntd.calculator.api.services;

import ntd.calculator.api.models.user.User;
import ntd.calculator.api.repositories.UserRepository;
import ntd.calculator.api.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserDetailsWhenUserExists() {
        var user = User.builder()
                .username("user")
                .password("password")
                .status(true)
                .build();

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        var result = userService.loadUserByUsername("user");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("password", result.getPassword());
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonexistentUser"));
    }
}