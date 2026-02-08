package com.georgesalise.apiRepo.api.controller;

import com.georgesalise.apiRepo.api.dto.UserAuthenticationDTO;
import com.georgesalise.apiRepo.api.service.user.IUserService;
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
    public String registerUser(@RequestBody UserAuthenticationDTO userAuthenticationDTO){
        return userService.registerUser(userAuthenticationDTO);
    }


    @PostMapping("/login")
    public String loginUser(@RequestBody UserAuthenticationDTO userAuthenticationDTO){
        return userService.verifyUser(userAuthenticationDTO);
    }
}
