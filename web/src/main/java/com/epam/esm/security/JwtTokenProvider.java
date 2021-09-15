package com.epam.esm.security;

import com.epam.esm.model.UserRole;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class for creating and validate JWT
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.header}")
    private String authorization;
    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    private JwtUserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(JwtUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, String roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", roles);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validity = now.plusHours(1);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Timestamp.valueOf(now))
                .setExpiration(Timestamp.valueOf(validity))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(Timestamp.valueOf(LocalDateTime.now()));
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(authorization);
    }

    private List<String> getRoleNames(Set<UserRole> userRoles) {
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> result.add(role.name()));
        return result;
    }

}
