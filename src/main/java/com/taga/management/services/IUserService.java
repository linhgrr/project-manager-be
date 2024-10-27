package com.taga.management.services;

import com.taga.management.DTOs.UserDTO;
import com.taga.management.models.User;
import com.taga.management.models.response.ResponseUser;

import java.util.List;

public interface IUserService {
    User createUser(UserDTO userDTO);
    String login(String username, String password) throws Exception;
    User findById(Long id);
    List<ResponseUser> findUserByName(String name);
}
