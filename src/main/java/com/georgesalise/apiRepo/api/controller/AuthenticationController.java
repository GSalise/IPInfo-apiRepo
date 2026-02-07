package com.georgesalise.apiRepo.api.controller;

import com.georgesalise.apiRepo.api.dto.UserCreateDTO;
import com.georgesalise.apiRepo.api.dto.UserDTO;
import com.georgesalise.apiRepo.api.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private final IUserService userService;

    public AuthenticationController(IUserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO createNewUser(@RequestBody UserCreateDTO userCreateDTO){
        return userService.createUser(userCreateDTO);
    }

}
