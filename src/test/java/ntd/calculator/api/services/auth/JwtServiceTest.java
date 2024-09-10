package ntd.calculator.api.services.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import ntd.calculator.api.config.ConfigProperties;
import ntd.calculator.api.config.properties.Jwt;
import ntd.calculator.api.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private ConfigProperties configProperties;

    @Mock
    private Jwt jwt;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        var secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        var encodedSecretKey = Encoders.BASE64.encode(secretKey.getEncoded());
        long oneDayInMillis = 24 * 60 * 60 * 1000L;

        when(configProperties.getJwt()).thenReturn(jwt);
        when(configProperties.getJwt().getExpiration()).thenReturn(oneDayInMillis);
        when(configProperties.getJwt().getSecretKey()).thenReturn(encodedSecretKey);
    }

    @Test
    void shouldCreateToken() {
        when(userDetails.getUsername()).thenReturn("user");

        var token = jwtService.createToken(userDetails);
        var claims = Jwts.parserBuilder()
                .setSigningKey(jwtService.getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("user", claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    void shouldReturnTrueWhenTokenIsValid() {
        when(userDetails.getUsername()).thenReturn("user");

        var token = jwtService.createToken(userDetails);
        var isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseWhenTokenIsExpired() {
        when(userDetails.getUsername()).thenReturn("user");

        var issuedAt = Date.from(Instant.now().minus(20, ChronoUnit.SECONDS));
        var expiration = Date.from(Instant.now().minus(10, ChronoUnit.SECONDS));

        Map<String, Object> claims = new HashMap<>();
        var token = Jwts.builder()
                .setClaims(claims)
                .setSubject("user")
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(jwtService.getSignInkey(), SignatureAlgorithm.HS256)
                .compact();

        var isValid = jwtService.isTokenValid(token, userDetails);

        assertFalse(isValid);
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {
        String invalidToken = "invalidToken";

        assertThrows(io.jsonwebtoken.MalformedJwtException.class, () -> jwtService.extractUsername(invalidToken));
    }
}