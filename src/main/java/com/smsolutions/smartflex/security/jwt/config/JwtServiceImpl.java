package com.smsolutions.smartflex.security.jwt.config;

import com.smsolutions.smartflex.exception.BaseException;
import com.smsolutions.smartflex.security.FlexUserDetails;
import com.smsolutions.smartflex.utils.constants.ConfigurationConstants;
import com.smsolutions.smartflex.utils.constants.ExceptionMessage;
import com.smsolutions.smartflex.utils.constants.StatusString;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtConfig mJwtConfig;

    private final UserDetailsService mUserDetailsService;

    @Override
    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key getKey() {
        byte[] key = Decoders.BASE64.decode(mJwtConfig.getSecret());
        return Keys.hmacShaKeyFor(key);
    }

    @Override
    public String generateToken(FlexUserDetails flexUserDetails) {
        Instant now = Instant.now();

        List<String> roles = new ArrayList<>();

        flexUserDetails.getAuthorities().forEach(role -> {
            roles.add(role.getAuthority());
        });

        return Jwts.builder()
                .setSubject(flexUserDetails.getUsername())
                .claim(ConfigurationConstants.AUTHORITIES,
                        flexUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                )
                .claim(ConfigurationConstants.ROLES, roles)
                .claim(ConfigurationConstants.IS_ENABLE, flexUserDetails.isEnabled())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(mJwtConfig.getExpiration())))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {
        final String username = extractUsername(token);

        UserDetails userDetails = mUserDetailsService.loadUserByUsername(username);

        return !ObjectUtils.isEmpty(userDetails);
    }

    private String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new BaseException(StatusString.UNAUTHORIZED, ExceptionMessage.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new BaseException(StatusString.UNAUTHORIZED, ExceptionMessage.TOKEN_NOT_SUPPORTED);
        } catch (MalformedJwtException e) {
            throw new BaseException(StatusString.UNAUTHORIZED, ExceptionMessage.MALFORMED_TOKEN);
        } catch (SignatureException e) {
            throw new BaseException(StatusString.UNAUTHORIZED, ExceptionMessage.INVALID_TOKEN_FORMAT);
        } catch (Exception e) {
            throw new BaseException(StatusString.UNAUTHORIZED, e.getLocalizedMessage());
        }

        return claims;
    }
}
