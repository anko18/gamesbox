package com.gamesbox.controller;

import com.gamesbox.dto.UserDTO;
import com.gamesbox.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLogin(Model model, UserDTO userDto){
        model.addAttribute("user", userDto);
        return "login";
    }

   @GetMapping("/account")
    public String getAccount(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "account";
    }



}
