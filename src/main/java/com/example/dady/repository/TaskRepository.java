package com.example.dady.repository;

import com.example.dady.model.Task;
import com.example.dady.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserAndStatus(User user, String status);
}