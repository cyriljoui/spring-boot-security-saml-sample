package com.mutum.framework.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.jsonwebtoken.Claims.SUBJECT;


public class JwtTokenHandler implements TokenHandler {
    public static final String CLAIM_ROLES = "roles";
    private final byte[] secret;

    public JwtTokenHandler(byte[] secret) {
        this.secret = secret;
    }

    public TokenData parseDataFromToken(String token) {
        try {

            Claims body = parseTokenBody(token);
            String subject = body.getSubject();
            List<String> roles = (List<String>) body.get(CLAIM_ROLES);

            return TokenData.builder()
                            .subject(subject)
                            .expires(body.getExpiration().getTime())
                            .roles(roles)
                            .build();
        } catch (RuntimeException exception) {
            return null;
        }
    }

    private Claims parseTokenBody(String token) {
        return Jwts.parser()
                   .setSigningKey(secret)
                   .parseClaimsJws(token)
                   .getBody();
    }

    public String createTokenFromData(TokenData tokenData) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, tokenData.getSubject());
        claims.put(CLAIM_ROLES, tokenData.getRoles());
        return Jwts.builder()
                   .setClaims(claims)
                   .setExpiration(new Date(tokenData.getExpires()))
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }
}
