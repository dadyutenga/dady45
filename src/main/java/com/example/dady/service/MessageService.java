package com.example.dady.service;

import com.example.dady.model.Message;
import com.example.dady.model.User;
import java.util.List;

public interface MessageService {
    Message sendMessage(User sender, User receiver, String content);
    List<Message> findMessagesForUser(User user);
}