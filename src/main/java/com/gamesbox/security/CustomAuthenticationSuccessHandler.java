package com.gamesbox.security;

import com.gamesbox.entity.User;
import com.gamesbox.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService){
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String userName = authentication.getName();

        User theUser = userService.findByUsername(userName);

        // now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);

        // forward to home page
        response.sendRedirect(request.getContextPath() + "/");
    }

}
