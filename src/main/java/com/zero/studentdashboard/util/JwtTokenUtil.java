package com.zero.studentdashboard.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class JwtTokenUtil implements Serializable {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final Long validity = TimeUnit.MINUTES.toMillis(60);

    /**
     * This method takes username from token.
     *
     * @param token the received token from the service.
     * @return username.
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * This method takes the issued date of the token.
     *
     * @param token the received token from the service.
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    /**
     * This method takes the expiration date of the token.
     *
     * @param token the received token from the service.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * This method takes the Claims from token.
     *
     * @param token          the received token from the service.
     * @param claimsResolver Claim resolver is used to help resolve claims from token.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * This method takes the Claims from token.
     *
     * @param token the received token from the service.
     */
    private Claims getAllClaimsFromToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getEncoded()))
                .build();

        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body;
    }

    /**
     * This method checks if the token is expired.
     *
     * @param token the received token from the service.
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * This method defines if token expiration should be ignored or not.
     *
     * @param token the received token from the service.
     */
    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }

    /**
     * This method generates token by given details.
     *
     * @param userDetails provided user info.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> collect = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        claims.put("role", collect);
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key).compact();
    }

    /**
     * This method defines can token be refreshed.
     *
     * @param token the received token from the service.
     */
    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    /**
     * This method defines if token is valid.
     *
     * @param token the received token from the service.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}