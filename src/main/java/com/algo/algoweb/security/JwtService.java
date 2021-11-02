package com.algo.algoweb.security;

import java.util.Date;
import java.util.function.Function;

import com.algo.algoweb.domain.User;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {
  private final JwtKeyProvider jwtKeyProvider;

  public String generateToken(User user) {
    Claims claims = Jwts.claims().setSubject(user.getId().toString());
    Date now = new Date();

    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(now)
      .setExpiration(new Date(now.getTime() + 1000 * 60 * 30))
      .signWith(jwtKeyProvider.getPrivateKey(), SignatureAlgorithm.RS256)
      .compact();
  }

  public String generateRefreshToken(User user) {
    Claims claims = Jwts.claims().setSubject(user.getId().toString());
    Date now = new Date();

    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(now)
      .setExpiration(new Date(now.getTime() + 1000 * 60 * 60 * 24 * 7))
      .signWith(jwtKeyProvider.getPrivateKey(), SignatureAlgorithm.RS256)
      .compact();
  }

  private Claims getAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(jwtKeyProvider.getPublicKey()).build().parseClaimsJws(token).getBody();
  }
  
  public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Integer getUserId(String token) {
    return Integer.parseInt(getClaim(token, Claims::getSubject));
  }

  public Date getExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }

  public boolean isExpired(String token) {
    final Date expiration = getExpiration(token);
    return expiration.before(new Date());
  }

  public boolean validateToken(String token, User user) {
    final Integer userId = getUserId(token);
    return (userId == user.getId() && !isExpired(token));
  }
}
