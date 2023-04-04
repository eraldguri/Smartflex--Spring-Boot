package com.smsolutions.smartflex.security;

import com.smsolutions.smartflex.entity.User;
import com.smsolutions.smartflex.exception.BaseException;
import com.smsolutions.smartflex.repository.UserRepository;
import com.smsolutions.smartflex.utils.constants.ExceptionMessage;
import com.smsolutions.smartflex.utils.constants.StatusString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

public class FlexUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository mUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FlexUserDetails flexUserDetails = getUserDetails(username);

        if (ObjectUtils.isEmpty(flexUserDetails)) {
            throw new BaseException(StatusString.BAD_REQUEST, ExceptionMessage.INVALID_CREDENTIALS);
        }

        return flexUserDetails;
    }

    private FlexUserDetails getUserDetails(String username) {
        User user = mUserRepository.findByUsername(username);

        if (ObjectUtils.isEmpty(user)) {
            throw new BaseException(StatusString.BAD_REQUEST, ExceptionMessage.INVALID_CREDENTIALS);
        }

        return new FlexUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

}
