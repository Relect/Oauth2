package com.example.todo_oath2.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class ErrorHandlingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlingFilter.class);

    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,@NonNull HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Пропускаем фильтрацию дальше
            filterChain.doFilter(request, response);
        } catch (AuthenticationException ex) {
            handleAuthenticationException(response, ex);
        } catch (AccessDeniedException ex) {
            handleAccessDeniedException(response, ex);
        }
    }

    // Обработка ошибки 401 - Unauthorized
    private void handleAuthenticationException(HttpServletResponse response, AuthenticationException ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + ex.getMessage() + "\"}");
        logger.error("Authentication error: {}", ex.getMessage());
    }

    // Обработка ошибки 403 - Forbidden
    private void handleAccessDeniedException(HttpServletResponse response, AccessDeniedException ex) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Forbidden\", \"message\": \"" + ex.getMessage() + "\"}");
        logger.error("Access denied: {}", ex.getMessage());
    }
}
