package com.example.dady.service;

import com.example.dady.model.Task;
import com.example.dady.model.User;
import com.example.dady.model.Team;
import com.example.dady.repository.TaskRepository;
import com.example.dady.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TeamRepository teamRepository) {
        this.taskRepository = taskRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Task createTask(String title, String description, String status, User user, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        Task task = new Task(title, description, status, user, team);
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