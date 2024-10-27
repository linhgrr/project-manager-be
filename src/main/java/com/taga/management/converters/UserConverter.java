package com.taga.management.converters;

import com.taga.management.models.User;
import com.taga.management.models.response.ResponseUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;
    public List<ResponseUser> toResponseUserList(List<User> userList){
        List<ResponseUser> responseUserList = new ArrayList<>();
        for (User user: userList){
            ResponseUser responseUser = modelMapper.map(user, ResponseUser.class);
            responseUserList.add(responseUser);
        }
        return responseUserList;
    }
}
