package ntd.calculator.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import ntd.calculator.api.services.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(userDetails.getUsername()).thenReturn("user");
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldDoNothingIfAuthorizationHeaderIsMissing() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).extractUsername(any());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldAuthenticateUserIfJwtIsValid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");
        when(jwtService.extractUsername("validJwtToken")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        doReturn(true).when(jwtService).isTokenValid("validJwtToken", userDetails); // Usando doReturn para evitar o erro

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService).extractUsername("validJwtToken");
        verify(userDetailsService).loadUserByUsername("user");
        verify(jwtService).isTokenValid("validJwtToken", userDetails);
        verify(filterChain).doFilter(request, response);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("user", authentication.getName());
        assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
    }

    @Test
    void shouldNotAuthenticateIfTokenIsInvalid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidJwtToken");
        when(jwtService.extractUsername("invalidJwtToken")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("invalidJwtToken", userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService).extractUsername("invalidJwtToken");
        verify(userDetailsService).loadUserByUsername("user");
        verify(jwtService).isTokenValid("invalidJwtToken", userDetails);
        verify(filterChain).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldSkipAuthenticationIfUserIsAlreadyAuthenticated() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");
        when(jwtService.extractUsername("validJwtToken")).thenReturn("user");

        // Mock the existing authentication
        var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService).extractUsername("validJwtToken");
        verify(userDetailsService, never()).loadUserByUsername(any());
        verify(jwtService, never()).isTokenValid(any(), any());
        verify(filterChain).doFilter(request, response);

        assertEquals(authToken, SecurityContextHolder.getContext().getAuthentication());
    }
}
