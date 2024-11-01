package com.taga.management.services.impl;

import com.taga.management.DTOs.UserDTO;
import com.taga.management.DTOs.request.UserUpdateDTO;
import com.taga.management.DTOs.response.LoginResponse;
import com.taga.management.components.JwtTokenUtil;
import com.taga.management.converters.UserConverter;
import com.taga.management.models.Role;
import com.taga.management.models.User;
import com.taga.management.DTOs.response.UserResponseDTO;
import com.taga.management.repository.RoleRepository;
import com.taga.management.repository.UserRepository;
import com.taga.management.services.IUserService;
import com.taga.management.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserConverter userConverter;


    @Override
    public User createUser(UserDTO userDTO) {
        String username = userDTO.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        Optional<Role> role = roleRepository.findById(2L);

        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .pictureUrl(userDTO.getPictureUrl())
                .active(true)
                .role(role.get())
                .build();

        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }

    @Override
    public LoginResponse login(String username, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        LoginResponse loginResponse = new LoginResponse();
        if(optionalUser.isEmpty()){
            throw new Exception("Username not found");
        }
        User existingUser = optionalUser.get();
        if(!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Wrong phone number or password");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password,
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        loginResponse.setToken(jwtTokenUtil.generateToken(existingUser));
        loginResponse.setUserId(existingUser.getId());
        return loginResponse;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<UserResponseDTO> findUserByName(String name) {
        List<User> userList = userRepository.findAllByFullNameContains(name);
        return userConverter.toResponseUserList(userList);
    }

    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(userUpdateDTO.getId()).get();
        user.setPictureUrl(userUpdateDTO.getPictureUrl());
        user.setFullName(userUpdateDTO.getFullName());
        user.setAddress(userUpdateDTO.getAddress());
        user.setDateOfBirth(userUpdateDTO.getDateOfBirth());
        userRepository.save(user);
    }
}
