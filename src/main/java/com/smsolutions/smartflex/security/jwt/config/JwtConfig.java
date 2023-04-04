package com.smsolutions.smartflex.security.jwt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {

    @Value("${jwt.url:/jwt/login}")
    private String url;

    @Value("${jwt.header:Authorization}")
    private String header;

    @Value("${jwt.prefix:Bearer}")
    private String prefix;

    @Value("${jwt.expiration:#{60*60}}")
    private int expiration;

    @Value("${jwt.secret:fUjXn2r5u8x/A?D(G+KbPeSgVkYp3s6v}")
    private String secret;

}
