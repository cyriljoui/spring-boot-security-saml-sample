package com.mutum.framework.security.token;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TokenAuthenticationService {
    private static final long TWENTY_ONE_DAYS = 1000 * 60 * 60 * 24 * 21;

    public final TokenHandler tokenHandler;

    public TokenAuthenticationService(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public void addAuthentication(HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        TokenData tokenData = TokenData.builder()
                .subject(userDetails.getUsername())
                .roles(roles)
                .expires(System.currentTimeMillis() + TWENTY_ONE_DAYS)
                .build();

        String tokenFromData = tokenHandler.createTokenFromData(tokenData);
        // Put token data to authorization response header
        response.addHeader(HttpHeaders.AUTHORIZATION, tokenFromData);
        // Write also token data to response directly
        response.getWriter().print(tokenFromData);
    }

    public TokenData readTokenData(HttpServletRequest request) {
        // Get the HTTP Authorization header from the request
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
            //throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        final String token = authorizationHeader.substring("Bearer".length()).trim();
        return tokenHandler.parseDataFromToken(token);
    }

    public String generateToken(String email) throws IOException {
        List<String> roles = Arrays.asList("USER");

        TokenData tokenData = TokenData.builder()
                .subject(email)
                .roles(roles)
                .expires(System.currentTimeMillis() + TWENTY_ONE_DAYS)
                .build();

        String tokenFromData = tokenHandler.createTokenFromData(tokenData);
        return tokenFromData;
    }

}
