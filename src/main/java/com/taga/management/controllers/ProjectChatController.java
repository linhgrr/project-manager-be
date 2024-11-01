package com.taga.management.controllers;

import com.taga.management.DTOs.request.MessageRequestDTO;
import com.taga.management.DTOs.response.MessageResponse;
import com.taga.management.repository.MessageRepository;
import com.taga.management.services.IMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class ProjectChatController {
    @Autowired
    private IMessageService messageService;

    @MessageMapping("/project/{projectId}/chat")
    @SendTo("/topic/project/{projectId}")
    public MessageResponse sendMessage(@Payload MessageRequestDTO chatMessage, Authentication authentication) {
        return messageService.sendMessage(chatMessage);
    }

    @GetMapping("/project/{projectId}/messages")
    public List<MessageResponse> getProjectMessages(@PathVariable Long projectId, Authentication authentication) {
        return messageService.getMessagesByProjectId(projectId);
    }
}

