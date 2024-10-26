package com.taga.management.services;

import com.taga.management.DTOs.UserDTO;
import com.taga.management.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO);
    String login(String username, String password) throws Exception;
}
