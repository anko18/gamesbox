package com.gamesbox.controller;

import com.gamesbox.dto.UserDTO;
import com.gamesbox.entity.User;
import com.gamesbox.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    //trim empty strings to null
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model, UserDTO userDto) {
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult theBindingResult, HttpSession session, Model model) {
        String userName = userDto.getUsername();

        if (theBindingResult.hasErrors()){
            return "registration";
        }

        User existingUsername = userService.findByUsername(userDto.getUsername());
        if (existingUsername != null) {
            model.addAttribute("userexists", "User with this username already exists!");
            return "registration";
        }
        User existingEmail = userService.findByEmail(userDto.getUsername());
        if (existingEmail != null) {
            model.addAttribute("emailexists", "User with this email already exists!");
            return "registration";
        }
        userService.save(userDto);
        // place user in the web http session for later use
        session.setAttribute("user", userDto);

        return "registration-confirmation";
    }
}
