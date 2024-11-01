package com.taga.management.services.impl;

import com.taga.management.DTOs.request.MessageRequestDTO;
import com.taga.management.DTOs.response.MessageResponse;
import com.taga.management.converters.MessageConverter;
import com.taga.management.models.Message;
import com.taga.management.repository.MessageRepository;
import com.taga.management.services.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageConverter messageConverter;
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public List<MessageResponse> getMessagesByProjectId(Long projectId) {
        List<Message> messageList = messageRepository.findByProject_Id(projectId);
        return messageList.stream().map(message -> messageConverter.toMessageResponse(message)).collect(Collectors.toList());
    }

    @Override
    public MessageResponse sendMessage(MessageRequestDTO chatMessage) {
        Message message = messageConverter.toMessage(chatMessage);
        messageRepository.save(message);

        return messageConverter.toMessageResponse(message);
    }
}
