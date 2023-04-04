package com.smsolutions.smartflex.utils.constants;

import org.springframework.http.HttpStatus;

public class StatusString {
    public static final String BAD_REQUEST = String.valueOf(HttpStatus.BAD_REQUEST.value());
    public static final String OK = String.valueOf(HttpStatus.OK.value());
    public static final String SERVICE_UNAVAILABLE = String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value());
    public static final String UNAUTHORIZED = String.valueOf(HttpStatus.UNAUTHORIZED.value());
}
