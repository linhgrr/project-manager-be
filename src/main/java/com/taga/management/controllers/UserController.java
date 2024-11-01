package com.taga.management.controllers;

import com.taga.management.DTOs.UserDTO;
import com.taga.management.DTOs.UserLoginDTO;
import com.taga.management.DTOs.request.UserUpdateDTO;
import com.taga.management.DTOs.response.LoginResponse;
import com.taga.management.converters.UserConverter;
import com.taga.management.models.User;
import com.taga.management.DTOs.response.UserResponseDTO;
import com.taga.management.services.IUserService;
import com.taga.management.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserConverter userConverter;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO,
                                      BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            User user = userService.createUser(userDTO);//return ResponseEntity.ok("Register successfully");
            return ResponseEntity.ok("Created user successfully");
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); //rule 5
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO) {
        // Kiểm tra thông tin đăng nhập và sinh token
        try {
            LoginResponse loginResponse = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public List<UserResponseDTO> getUserByName(@RequestParam String name) {
        return userService.findUserByName(name);
    }

    @GetMapping("/profile")
    public UserResponseDTO getUserProfile() {
        return userConverter.toResponseUser(userService.findById(SecurityUtils.getPrincipal().getId()));
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            userService.updateUser(userUpdateDTO);
            return ResponseEntity.ok("Updated avatar successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
