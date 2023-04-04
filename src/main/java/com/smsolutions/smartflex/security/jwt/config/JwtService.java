package com.smsolutions.smartflex.security.jwt.config;

import com.smsolutions.smartflex.security.FlexUserDetails;
import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JwtService {
    Claims extractClaims(String token);
    Key getKey();
    String generateToken(FlexUserDetails flexUserDetails);
    boolean isValidToken(String token);
}
