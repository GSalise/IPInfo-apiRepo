package com.georgesalise.apiRepo.api.controller;

import com.georgesalise.apiRepo.api.dto.UserAuthenticationDTO;
import com.georgesalise.apiRepo.api.service.user.IUserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PostMapping("/verify")
    public Boolean verifyToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid");
        }

        String token = authHeader.substring(7);
        boolean valid = userService.verifyToken(token);
        if (!valid) {
            throw new IllegalArgumentException("Token is invalid or expired");
        }
        return true;
    }

}
