package com.smsolutions.smartflex.config.filter;

import com.smsolutions.smartflex.utils.constants.ConfigurationConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.io.IOException;

@Configuration
public class CorsFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setHeader(ConfigurationConstants.ACCESS_CONTROL_ALLOW_ORIGIN, ConfigurationConstants.CONFIG_ALL);
        httpServletResponse.setHeader(ConfigurationConstants.ACCESS_CONTROL_ALLOW_METHODS, ConfigurationConstants.CONFIG_ALL);
        httpServletResponse.setHeader(ConfigurationConstants.ACCESS_CONTROL_ALLOW_HEADERS, ConfigurationConstants.AUTHORIZATION_CONTENT_TYPE);
        httpServletResponse.setHeader(ConfigurationConstants.ACCESS_CONTROL_MAX_AGE, ConfigurationConstants.MAX_AGE);

        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }

        chain.doFilter(request, response);
    }
}
