package com.shoppingcart.dream_shops.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.dream_shops.http_exception.UnAuthorizedHttpException;
import com.shoppingcart.dream_shops.request.LoginRequest;
import com.shoppingcart.dream_shops.response.ApiResponse;
import com.shoppingcart.dream_shops.response.JwtResponse;
import com.shoppingcart.dream_shops.security.jwt.JwtUtils;
import com.shoppingcart.dream_shops.security.user.ShopUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateTokenForUser(authentication);
      ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
      JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
      return ResponseEntity.ok(new ApiResponse("Logged in.", jwtResponse));
    } catch (AuthenticationException e) {
      throw new UnAuthorizedHttpException("Invalid email or password");
    }
  }

}
