package ntd.calculator.api.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * This service handles the logout process in an application using JWT (JSON Web Token) for authentication.

 * This class implements the LogoutHandler interface from Spring Security, allowing it to be automatically
 * invoked when a logout request is made.

 * Unlike approaches where the JWT is stored in a database and can be explicitly revoked or marked as expired,
 * this implementation does not save tokens. Instead, the security context is simply cleared during logout,
 * effectively ending the user's current session.

 * This approach is suitable for scenarios where it is unnecessary to manage or revoke tokens before their natural
 * expiration, simplifying the logout logic and improving process efficiency.

 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }

        // if we had saved the token into database we could
        // set revoked and expired, instead we can simply clear context
        SecurityContextHolder.clearContext();
    }
}
