package com.mutum.framework.security.token;


public interface TokenHandler {

    TokenData parseDataFromToken(String token);

    String createTokenFromData(TokenData tokenData);

}
