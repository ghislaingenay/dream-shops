package com.shoppingcart.dream_shops.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shoppingcart.dream_shops.security.user.ShopUserDetails;
import com.shoppingcart.dream_shops.security.user.ShopUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

// AuthTokenFilter is a custom filter that checks for a JWT in the Authorization header of each HTTP request.
// If a valid JWT is found, it sets the authentication in the Spring Security context.
public class AuthTokenFilter extends OncePerRequestFilter {
  // Utility for JWT operations (parsing, validation, extracting username)
  private final JwtUtils jwtUtils;
  // Service to load user details from the database or user store
  private final ShopUserDetailsService userDetailsService;

  public AuthTokenFilter(JwtUtils jwtUtils, ShopUserDetailsService userDetailsService) {
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // Extract JWT from the Authorization header
    String jwt = parseJwt(request);
    // If the JWT is present and valid
    if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
      // Extract the username/email from the JWT
      String email = jwtUtils.getUsernameFromToken(jwt);
      // Load user details using the extracted email
      ShopUserDetails userDetails = userDetailsService.loadUserByUsername(email);
      // Create an authentication object with the user details and authorities
      Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      // Set the authentication in the Spring Security context
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    // Continue the filter chain (important to allow the request to proceed)
    filterChain.doFilter(request, response);
  }

  // Helper method to extract the JWT from the Authorization header
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    // Check if the header is present and starts with "Bearer "
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      // Return the JWT part after "Bearer "
      return headerAuth.substring(7);
    }
    return null;
  }

}
