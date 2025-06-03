package com.example.dady.service;

import com.example.dady.model.Task;
import com.example.dady.model.User;
import java.util.List;

public interface TaskService {
    Task createTask(String title, String description, String status, User user, Long teamId);
    Task updateTaskStatus(Long taskId, String newStatus);
    List<Task> getTasksByUserAndStatus(User user, String status);
}