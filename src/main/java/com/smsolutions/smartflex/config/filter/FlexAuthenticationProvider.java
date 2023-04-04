package com.smsolutions.smartflex.config.filter;

import com.smsolutions.smartflex.entity.Role;
import com.smsolutions.smartflex.entity.User;
import com.smsolutions.smartflex.exception.BaseException;
import com.smsolutions.smartflex.repository.UserRepository;
import com.smsolutions.smartflex.utils.constants.ExceptionMessage;
import com.smsolutions.smartflex.utils.constants.StatusString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FlexAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository mUserRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();

        final String password = authentication.getCredentials().toString();

        User user;
        try {
            user = mUserRepository.findByUsername(username);
        } catch (Exception e) {
            throw new BaseException(StatusString.UNAUTHORIZED, ExceptionMessage.USER_NOT_FOUND);
        }

        final List<GrantedAuthority> authorities = getAuthorities(user.getRoles().stream().toList());

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    private List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> results = new ArrayList<>();
        Set<String> permissions = new HashSet<>();

        if (!ObjectUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                permissions.add(role.getName());
            });
        }

        permissions.forEach(permission -> {
            results.add(new SimpleGrantedAuthority(permission));
        });

        return results;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
