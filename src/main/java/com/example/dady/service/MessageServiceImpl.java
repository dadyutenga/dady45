package com.example.dady.service;

import com.example.dady.model.Message;
import com.example.dady.model.User;
import com.example.dady.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message sendMessage(User sender, User receiver, String content) {
        Message message = new Message(sender, receiver, content);
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findMessagesForUser(User user) {
        return messageRepository.findAll().stream()
                .filter(message -> message.getReceiver().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}