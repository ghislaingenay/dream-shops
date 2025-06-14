package com.shoppingcart.dream_shops.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.shoppingcart.dream_shops.security.user.ShopUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtils {
  @Value("${auth.token.jwtSecret}")
  private String jwtSecret;
  @Value("${auth.token.expirationTime}")
  private int expirationTime;

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  @SuppressWarnings("deprecation")
  public String generateTokenForUser(Authentication authentication) {
    ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();
    List<String> roles = userPrincipal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    return Jwts.builder()
        .subject(userPrincipal.getUsername())
        .claim("id", userPrincipal.getId())
        .claim("roles", roles)
        .issuedAt(new Date())
        .expiration(new Date((new Date().getTime()) + expirationTime))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token)
        .getPayload().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);
      return true;
    } catch (ExpiredJwtException | UnsupportedJwtException | SignatureException | MalformedJwtException
        | IllegalArgumentException e) {
      throw new JwtException(e.getMessage());
    }
  }
}
