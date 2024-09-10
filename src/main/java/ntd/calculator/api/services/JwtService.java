package ntd.calculator.api.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);

    String createToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

}
