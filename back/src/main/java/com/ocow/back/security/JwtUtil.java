package com.ocow.back.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

	private String secretKey;//Secret key : 64 chars

  public String extractUsername(String token) {
      return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
      return Jwts
              .parserBuilder()
              .setSigningKey(getSignKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
  }

  private Boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetailsImpl userDetails) {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String generateToken(String userName){
      Map<String,Object> claims=new HashMap<>();
      return createToken(claims,userName);
  }

  private String createToken(Map<String, Object> claims, String userName) {
      return Jwts.builder()
              .setClaims(claims)
              .setSubject(userName)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis()+1000*60*20)) //20 min expiration time
              .signWith(getSignKey(), SignatureAlgorithm.HS256)
              .compact();
  }

  private Key getSignKey() {
      byte[] keyBytes= Decoders.BASE64.decode(secretKey);
      return Keys.hmacShaKeyFor(keyBytes);
  }
  
  public void setSecretKey(String secretKey) {
  	this.secretKey = secretKey;
  }
}
