package com.example.dady.controller;

import com.example.dady.model.Message;
import com.example.dady.model.Team;
import com.example.dady.model.User;
import com.example.dady.service.MessageService;
import com.example.dady.service.TeamService;
import com.example.dady.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public TeamController(TeamService teamService, UserService userService, MessageService messageService) {
        this.teamService = teamService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/teams")
    public String showTeamsPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Team> teams = teamService.findAll(); // Assuming a findAll method in TeamService
        model.addAttribute("teams", teams);
        model.addAttribute("users", userService.findAll()); // For team creation
        return "teams";
    }

    @PostMapping("/team/create")
    public String createTeam(@RequestParam String name, @RequestParam List<Long> userIds, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Set<User> users = new HashSet<>();
        for (Long userId : userIds) {
            User member = userService.findById(userId); // Assuming findById in UserService
            if (member != null) {
                users.add(member);
            }
        }
        teamService.createTeam(name, users);
        return "redirect:/teams";
    }

    @GetMapping("/messages")
    public String showMessagesPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Message> messages = messageService.findMessagesForUser(user); // Assuming this method in MessageService
        model.addAttribute("messages", messages);
        model.addAttribute("users", userService.findAll()); // For selecting message recipients
        return "messages";
    }

    @PostMapping("/message/send")
    public String sendMessage(@RequestParam String receiverUsername, @RequestParam String content, HttpSession session) {
        User sender = (User) session.getAttribute("user");
        if (sender == null) {
            return "redirect:/login";
        }
        User receiver = userService.findByUsername(receiverUsername);
        if (receiver == null) {
            return "redirect:/messages?error=UserNotFound";
        }
        messageService.sendMessage(sender, receiver, content);
        return "redirect:/messages";
    }
}