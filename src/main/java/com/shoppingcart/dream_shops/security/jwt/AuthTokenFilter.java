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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// AuthTokenFilter: Checks for JWT in requests and sets authentication if valid
public class AuthTokenFilter extends OncePerRequestFilter {
  // JWT utility for parsing and validation
  private final JwtUtils jwtUtils;
  // Loads user details by username/email
  private final ShopUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // Get JWT from Authorization header
    String jwt = parseJwt(request);
    // If JWT is present and valid
    if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
      // Extract username/email from JWT
      String email = jwtUtils.getUsernameFromToken(jwt);
      // Load user details
      ShopUserDetails userDetails = userDetailsService.loadUserByUsername(email);
      // Set authentication in security context
      Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    // Continue with the next filter or controller
    filterChain.doFilter(request, response);
  }

  // Extract JWT from Authorization header if present
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

}
