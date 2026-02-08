package com.georgesalise.apiRepo.api.controller;

import com.georgesalise.apiRepo.api.dto.UserHistoryDTO;
import com.georgesalise.apiRepo.api.service.userhistory.IUserHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/history")
public class UserHistoryController {
    private final IUserHistoryService iUserHistoryService;


    public UserHistoryController(IUserHistoryService iUserHistoryService) {
        this.iUserHistoryService = iUserHistoryService;
    }

    @GetMapping
    public List<UserHistoryDTO> getUserHistory(Principal principal){
        String username = principal.getName();
        return iUserHistoryService.getUserHistory(username);
    }
}
