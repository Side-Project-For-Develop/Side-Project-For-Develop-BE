package com.h10.sideproject.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.h10.sideproject.common.exception.ErrorCode;
import com.h10.sideproject.common.ResponseMessage;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.h10.sideproject.common.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.h10.sideproject.common.exception.ErrorCode.TOKEN_ERROR;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        String token = jwtUtil.resolveToken(bearerToken);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtUtil.validateToken(token)) {
            jwtExceptionHandler(response, TOKEN_ERROR);
            return;
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);
        setAuthentication(response, (String) info.get("id"));
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(HttpServletResponse response, String email) {
        try {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = jwtUtil.createAuthentication(email);
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        } catch (UsernameNotFoundException e) {
            jwtExceptionHandler(response, MEMBER_NOT_FOUND);
        }
    }

    public void jwtExceptionHandler(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getStatusCode());
        response.setContentType("application/json; charset=utf8");
        try {
            String json = new ObjectMapper().writeValueAsString(new ResponseMessage<>(errorCode, errorCode));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}