package com.example.publicnotes.controller;

import com.example.publicnotes.model.User;
import com.example.publicnotes.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/user/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(required = false) String registered) {
        model.addAttribute("registered", registered != null);
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, 
                          @RequestParam String password, 
                          HttpSession session,
                          Model model) {
        try {
            User user = userService.loginUser(email, password);
            session.setAttribute("userEmail", user.getEmail());
            return "redirect:/notes/create";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userEmail");
        return "redirect:/user/login";
    }
} 