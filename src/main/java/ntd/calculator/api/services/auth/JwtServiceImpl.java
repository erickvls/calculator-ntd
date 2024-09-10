package ntd.calculator.api.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import ntd.calculator.api.config.ConfigProperties;
import ntd.calculator.api.services.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service responsible for handling JWT (JSON Web Token) operations.
 * This includes creating tokens, extracting claims, validating tokens,
 * and managing token expiration.
 */
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final ConfigProperties configProperties;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String createToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final var username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String createToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ) {
        var expiration = configProperties.getJwt().getExpiration();
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInkey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // If token is expired it will throw this exception
            // but, we are going to handle it in a separate method (isValidToken)
            return e.getClaims();
        }
    }

    protected Key getSignInkey() {
        var keyBytes = Decoders.BASE64.decode(configProperties.getJwt().getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
