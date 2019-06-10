package com.smartcity.exceptions.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.exceptions.json.ExceptionResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(ExceptionResponse
                .builder()
                .url(request.getRequestURI())
                .message(e.getLocalizedMessage())
                .build());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(403);
        response.getWriter().write(json);
    }
}
