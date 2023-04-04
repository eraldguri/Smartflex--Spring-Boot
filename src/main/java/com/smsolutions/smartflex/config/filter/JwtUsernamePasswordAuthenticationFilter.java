package com.smsolutions.smartflex.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smsolutions.smartflex.dto.LoginRequestDto;
import com.smsolutions.smartflex.entity.BaseResponseDto;
import com.smsolutions.smartflex.security.FlexUserDetails;
import com.smsolutions.smartflex.security.jwt.config.JwtConfig;
import com.smsolutions.smartflex.security.jwt.config.JwtService;
import com.smsolutions.smartflex.utils.Helpers;
import com.smsolutions.smartflex.utils.constants.ConfigurationConstants;
import com.smsolutions.smartflex.utils.constants.StatusString;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;

public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtService mJwtService;

    private final ObjectMapper mObjectMapper;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager manager, JwtConfig jwtConfig, JwtService jwtService) {
        super(new AntPathRequestMatcher(jwtConfig.getUrl(), "POST"));
        this.mJwtService = jwtService;
        mObjectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        LoginRequestDto loginRequest = mObjectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        FlexUserDetails flexUserDetails = (FlexUserDetails) authResult.getPrincipal();

        String accessToken = mJwtService.generateToken(flexUserDetails);

        String json = Helpers.JSON_WRITER.writeValueAsString(accessToken);

        response.setContentType(ConfigurationConstants.CONTENT_TYPE);
        response.getWriter().write(json);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        BaseResponseDto responseDto = new BaseResponseDto();
        responseDto.setCode(StatusString.UNAUTHORIZED);
        responseDto.setMessage(failed.getLocalizedMessage());

        String json = Helpers.JSON_WRITER.writeValueAsString(responseDto);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(ConfigurationConstants.CONTENT_TYPE);
        response.getWriter().write(json);

    }
}
