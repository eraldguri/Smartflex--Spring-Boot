package com.smsolutions.smartflex.controller;

import com.smsolutions.smartflex.dto.UserDto;
import com.smsolutions.smartflex.entity.BaseResponseDto;
import com.smsolutions.smartflex.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService mUserService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDto> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(mUserService.registerAccount(userDto));
    }

}
