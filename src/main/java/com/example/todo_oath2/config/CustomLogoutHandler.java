package com.example.todo_oath2.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {
    private static Logger logger = LoggerFactory.getLogger(CustomLogoutHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            String email = authentication.getName();
            logger.info("User {} logout", email);
            SecurityContextHolder.clearContext();
        }
    }
}
