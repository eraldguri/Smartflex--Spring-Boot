package com.smsolutions.smartflex.security;

import com.smsolutions.smartflex.dto.UserDto;
import com.smsolutions.smartflex.entity.BaseResponseDto;
import com.smsolutions.smartflex.entity.Role;
import com.smsolutions.smartflex.entity.User;
import com.smsolutions.smartflex.exception.BaseException;
import com.smsolutions.smartflex.repository.RoleRepository;
import com.smsolutions.smartflex.repository.UserRepository;
import com.smsolutions.smartflex.security.UserService;
import com.smsolutions.smartflex.utils.constants.ExceptionMessage;
import com.smsolutions.smartflex.utils.constants.StatusString;
import com.smsolutions.smartflex.utils.constants.SuccessMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;
    private final RoleRepository mRoleRepository;
    private final BCryptPasswordEncoder mPasswordEncoder;

    @Override
    public BaseResponseDto registerAccount(UserDto userDto) {
        BaseResponseDto response = new BaseResponseDto();

        validateAccount(userDto);

        User user = insertUser(userDto);

        try {
            mUserRepository.save(user);
            response.setCode(StatusString.OK);
            response.setMessage(SuccessMessages.ACCOUNT_CREATED);
        } catch (Exception e) {
            response.setCode(StatusString.SERVICE_UNAVAILABLE);
            response.setMessage(ExceptionMessage.SERVICE_UNAVAILABLE);
        }

        return response;
    }

    private User insertUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(mPasswordEncoder.encode(userDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(mRoleRepository.findByName(userDto.getRole()));
        user.setRoles(roles);

        user.setCreatedAt(userDto.getCreatedAt());
        user.setUpdatedAt(userDto.getUpdatedAt());

        return user;
    }

    private void validateAccount(UserDto userDto) {
        // Validate null data
        if (ObjectUtils.isEmpty(userDto)) {
            throw new BaseException(StatusString.BAD_REQUEST, ExceptionMessage.DATA_NOT_EMPTY);
        }

        // Check for duplicated username
        User user = mUserRepository.findByUsername(userDto.getUsername());
        if (!ObjectUtils.isEmpty(user)) {
            throw new BaseException(StatusString.BAD_REQUEST, ExceptionMessage.USERNAME_REQUIRED);
        }

        // Validate role
        List<String> roles = mRoleRepository.findAll().stream().map(Role::getName).toList();
        if (!roles.contains(userDto.getRole())) {
            throw new BaseException(StatusString.BAD_REQUEST, ExceptionMessage.INVALID_ROLE);
        }
    }
}
