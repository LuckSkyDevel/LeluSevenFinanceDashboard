package com.finance.leluseven.shared.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final SecretKey key;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Getter
    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    public TokenService() {
        this.key = Keys.hmacShaKeyFor(this.jwtSecret.getBytes());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return "access".equals(extractAllClaims(token).get("type"));
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Object roles = extractAllClaims(token).get("roles");
        if (roles instanceof Collection) {
            return ((Collection<?>) roles).stream().map(Object::toString).toList();
        }

        return List.of();
    }

    public String generateAccessToken(String nomUsuario, Collection<String> perfis) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + jwtExpiration * 1000);

        return Jwts.builder().subject(nomUsuario)
                .claim("type", "access")
                .claim("roles", perfis)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key).compact();
    }

    public String generateRefreshToken(String nomUsuario) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + refreshExpiration * 1000);

        return Jwts.builder().subject(nomUsuario)
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(exp)
                .signWith(key).compact();
    }

}
