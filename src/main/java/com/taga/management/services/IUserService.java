package com.taga.management.services;

import com.taga.management.DTOs.UserDTO;
import com.taga.management.DTOs.request.UserUpdateDTO;
import com.taga.management.DTOs.response.LoginResponse;
import com.taga.management.models.User;
import com.taga.management.DTOs.response.UserResponseDTO;

import java.util.List;

public interface IUserService {
    User createUser(UserDTO userDTO);
    LoginResponse login(String username, String password) throws Exception;
    User findById(Long id);
    User findByUsername(String username);
    List<UserResponseDTO> findUserByName(String name);
    void updateUser(UserUpdateDTO user);
}
