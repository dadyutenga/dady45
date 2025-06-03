package com.example.dady.service;

import com.example.dady.model.User;

public interface UserService {
    User registerUser(String username, String password);
    User findByUsername(String username);
}