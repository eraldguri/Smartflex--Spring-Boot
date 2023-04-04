package com.smsolutions.smartflex.security;

import com.smsolutions.smartflex.dto.UserDto;
import com.smsolutions.smartflex.entity.BaseResponseDto;

public interface UserService {
    BaseResponseDto registerAccount(UserDto userDto);
}
