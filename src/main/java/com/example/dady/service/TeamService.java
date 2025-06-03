package com.example.dady.service;

import com.example.dady.model.Team;
import com.example.dady.model.User;
import java.util.List;
import java.util.Set;

public interface TeamService {
    Team createTeam(String name, Set<User> users);
    Team findById(Long id);
    List<Team> findAll();
}