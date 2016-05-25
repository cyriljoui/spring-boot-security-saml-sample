package com.mutum.framework.security.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TokenData {

    private String subject;
    private List<String> roles;
    private long expires;

}
