package com.vdenotaris.spring.boot.security.saml.web.config;

import com.mutum.framework.security.token.JwtTokenHandler;
import com.mutum.framework.security.token.TokenAuthenticationService;
import com.mutum.framework.security.token.TokenHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.DatatypeConverter;

/**
 * Mutum JWT config.
 */
@Configuration
public class MutumConfig {

    @Value("${mutum.security.jwt.secret}")
    private String secretKey;

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return new TokenAuthenticationService(tokenHandler());
    }

    @Bean
    public TokenHandler tokenHandler() {
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(secretKey);

        return new JwtTokenHandler(secretBytes);
    }

}
