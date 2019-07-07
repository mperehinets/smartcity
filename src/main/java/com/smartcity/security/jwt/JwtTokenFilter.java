package com.smartcity.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.exceptions.InvalidJwtAuthenticationException;
import com.smartcity.exceptions.json.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) req;
            String token = jwtTokenProvider.resolveToken(httpServletRequest);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null) {
                    authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(req, res);

        } catch (InvalidJwtAuthenticationException exception) {
            ((HttpServletResponse) res).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(ExceptionResponse.builder().url(((HttpServletRequest) req).getRequestURI())
                    .message("Token is invalid or expired!").build());

            res.getWriter().write(message);
        }

    }

}
