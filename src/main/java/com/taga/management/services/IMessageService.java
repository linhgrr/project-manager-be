package com.taga.management.services;

import com.taga.management.DTOs.request.MessageRequestDTO;
import com.taga.management.DTOs.response.MessageResponse;
import com.taga.management.models.Message;

import java.util.List;

public interface IMessageService {
    void saveMessage(Message message);
    List<MessageResponse> getMessagesByProjectId(Long projectId);
    MessageResponse sendMessage(MessageRequestDTO messageRequestDTO);
}
