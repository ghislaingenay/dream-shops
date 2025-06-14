package com.shoppingcart.dream_shops.security.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shoppingcart.dream_shops.security.jwt.AuthTokenFilter;
import com.shoppingcart.dream_shops.security.jwt.JwtAuthEntryPoint;
import com.shoppingcart.dream_shops.security.user.ShopUserDetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class ShopConfig {
  private final ShopUserDetailsService shopUserDetailsService;
  private final JwtAuthEntryPoint authEntryPoint;

  private static final String[] SECURED_URLS = {
      "/api/v1/products/**", // Example secured URL for products
      "/api/v1/orders/**", // Example secured URL for orders
      "/api/v1/users/**" // Example secured URL for user-related operations
  };

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
    // return new PasswordEncoder() {
    // @Override
    // public String encode(CharSequence rawPassword) {
    // // Implement your password encoding logic here
    // return rawPassword.toString(); // Placeholder, replace with actual encoding
    // }

    // @Override
    // public boolean matches(CharSequence rawPassword, String encodedPassword) {
    // // Implement your password matching logic here
    // return rawPassword.toString().equals(encodedPassword); // Placeholder,
    // replace with actual matching
    // }
    // };
  }

  @Bean
  public AuthTokenFilter authTokenFilter() {
    return new AuthTokenFilter(null, shopUserDetailsService);
  }

  // public AuthenticationManager
  // authenticationManager(AuthenticationConfiguration authConfig) throws
  // Exception {
  // return authentication -> {
  // // Implement your authentication logic here
  // String username = authentication.getName();
  // String password = (String) authentication.getCredentials();
  // // Validate username and password against your user service
  // if (shopUserDetailsService.loadUserByUsername(username) != null
  // && passwordEncoder().matches(password, "storedPassword")) {
  // return authentication; // Return the authenticated object
  // }
  // throw new RuntimeException("Authentication failed");
  // };
  // }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  // Defines a bean for DaoAuthenticationProvider, which is used by Spring
  // Security to authenticate users
  // using a custom UserDetailsService and a password encoder.
  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    // Set the custom user details service for loading user data
    provider.setUserDetailsService(shopUserDetailsService);
    // Set the password encoder for verifying user passwords
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection (not needed for stateless APIs)
        .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint)) // Set custom entry point
                                                                                            // for unauthorized access
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Ensure no HTTP
                                                                                                      // session is
                                                                                                      // created; use
                                                                                                      // JWT for
                                                                                                      // authentication
        .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URLS.toArray(String[]::new))
            .authenticated() // Require authentication for secured URLs
            .anyRequest().permitAll()); // Allow all other requests without authentication

    http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Insert JWT filter before
                                                                                         // username/password filter

    return http.build(); // Build and return the configured security filter chain
  }
}
