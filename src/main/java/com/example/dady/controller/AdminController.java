package com.example.dady.controller;

import com.example.dady.model.User;
import com.example.dady.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showAdminPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !isAdmin(user)) {
            return "redirect:/login";
        }
        model.addAttribute("users", userService.findAll()); // Placeholder for all users
        return "admin";
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equals(user.getUsername()); // Simple check, enhance with roles
    }
}