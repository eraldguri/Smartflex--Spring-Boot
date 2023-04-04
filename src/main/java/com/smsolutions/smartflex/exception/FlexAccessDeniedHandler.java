package com.smsolutions.smartflex.exception;

import com.smsolutions.smartflex.entity.BaseResponseDto;
import com.smsolutions.smartflex.utils.Helpers;
import com.smsolutions.smartflex.utils.constants.ConfigurationConstants;
import com.smsolutions.smartflex.utils.constants.ExceptionMessage;
import com.smsolutions.smartflex.utils.constants.StatusString;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class FlexAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        BaseResponseDto responseDto = new BaseResponseDto();
        responseDto.setMessage(ExceptionMessage.DO_NOT_HAVE_ACCESS_TO_RESOURCE);
        responseDto.setCode(StatusString.FORBIDDEN);

        String json = Helpers.JSON_WRITER.writeValueAsString(responseDto);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(ConfigurationConstants.APPLICATION_JSON);
        response.setCharacterEncoding(ConfigurationConstants.UTF_8);
        response.getWriter().write(json);
    }
}
