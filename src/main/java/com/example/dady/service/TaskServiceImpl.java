package com.example.dady.service;

import com.example.dady.model.Task;
import com.example.dady.model.User;
import com.example.dady.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(String title, String description, User user) {
        Task task = new Task(title, description, "TODO", user);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskStatus(Long taskId, String newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksByUserAndStatus(User user, String status) {
        return taskRepository.findByUserAndStatus(user, status);
    }
}