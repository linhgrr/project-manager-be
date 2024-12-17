package com.taga.management.converters;

import com.taga.management.DTOs.request.MessageRequestDTO;
import com.taga.management.DTOs.response.MessageResponse;
import com.taga.management.components.JwtTokenUtil;
import com.taga.management.models.Message;
import com.taga.management.models.Project;
import com.taga.management.models.User;
import com.taga.management.repository.ProjectRepository;
import com.taga.management.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConverter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private IUserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProjectRepository projectRepository;
    public Message toMessage (MessageRequestDTO chatMessage){
        String token = chatMessage.getToken();
        String username = jwtTokenUtil.extractUsername(token);
        User user = userService.findByUsername(username);
        Message message = modelMapper.map(chatMessage, Message.class);
        Project project = projectRepository.findById(chatMessage.getProjectId()).get();
        message.setProject(project);
        message.setSenderId(user.getId());

        return message;
    }

    public MessageResponse toMessageResponse(Message message){
        MessageResponse messageResponse = modelMapper.map(message, MessageResponse.class);
        User user = userService.findById(messageResponse.getSenderId());
        messageResponse.setFullName(user.getFullName());
        messageResponse.setPictureUrl(user.getPictureUrl());
        messageResponse.setSubscribed(user.isSubscribed());
        return messageResponse;
    }
}
