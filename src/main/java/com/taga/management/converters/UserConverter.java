package com.taga.management.converters;

import com.taga.management.DTOs.request.UserUpdateDTO;
import com.taga.management.models.User;
import com.taga.management.DTOs.response.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;
    public List<UserResponseDTO> toResponseUserList(List<User> userList){
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        for (User user: userList){
            UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
            userResponseDTOList.add(userResponseDTO);
        }
        return userResponseDTOList;
    }

    public UserResponseDTO toResponseUser(User user){
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public User toUser(UserUpdateDTO userUpdateDTO){
        return modelMapper.map(userUpdateDTO, User.class);
    }
}
