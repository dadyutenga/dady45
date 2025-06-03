package com.example.dady.service;

import com.example.dady.model.User;
import java.util.List;

public interface UserService {
    User registerUser(String username, String password);
    User findByUsername(String username);
    User findById(Long id);
    List<User> findAll();
}