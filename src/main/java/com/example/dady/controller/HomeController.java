package com.example.dady.controller;

import com.example.dady.model.User;
import com.example.dady.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final TaskService taskService;

    @Autowired
    public HomeController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", user.getUsername());
        model.addAttribute("todoTasks", taskService.getTasksByUserAndStatus(user, "TODO"));
        model.addAttribute("inProgressTasks", taskService.getTasksByUserAndStatus(user, "IN_PROGRESS"));
        model.addAttribute("doneTasks", taskService.getTasksByUserAndStatus(user, "DONE"));
        return "home";
    }

    @PostMapping("/task/create")
    public String createTask(@RequestParam String title, @RequestParam String description, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        taskService.createTask(title, description, user);
        return "redirect:/home";
    }

    @PostMapping("/task/update-status")
    public String updateTaskStatus(@RequestParam Long taskId, @RequestParam String status) {
        taskService.updateTaskStatus(taskId, status);
        return "redirect:/home";
    }
}