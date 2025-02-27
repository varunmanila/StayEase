package com.example.StayEase.Service;

import com.example.StayEase.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService{

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    // Generate JWT Token
    public String generateToken(UserDetails userDetails,String email) {
        return generateToken(new HashMap<>(), userDetails,email);
    }

    public String generateToken(Map<String, Object> additionalClaims, UserDetails userDetails,String email) {
        return Jwts.builder()
                .setClaims(additionalClaims)
                .setSubject(email)
                .claim("firstname",  ((User) userDetails).getFirst_name())
                .claim("lastname", ((User) userDetails).getLast_name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) //ONE DAY
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate JWT token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return username.equals( ((User) userDetails).getEmail()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public String extractUserName(String token) {
         Claims claims = extractAllClaims(token);

        System.out.println("JWT Claims: " + claims); // Debugging output
         return claims.getSubject();
//        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
    }

    // To extract payload (claims) from the JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Ensure same key and algorithm
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret); // Decode Base64
        return Keys.hmacShaKeyFor(keyBytes);
    }

}