package com.georgesalise.apiRepo.api.controller;

import com.georgesalise.apiRepo.api.dto.IPInfoDTO;
import com.georgesalise.apiRepo.api.dto.UserHistoryDTO;
import com.georgesalise.apiRepo.api.dto.UserHistoryWithIPInfoDTO;
import com.georgesalise.apiRepo.api.service.userhistory.IUserHistoryService;
import org.springframework.web.bind.annotation.*;

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
    public List<UserHistoryWithIPInfoDTO> getUserHistory(Principal principal){
        String username = principal.getName();
        return iUserHistoryService.getUserHistoryAsIPInfo(username);
    }

    @DeleteMapping
    public void deleteUserHistory(Principal principal, @RequestBody List<Long> userHistoryIds){
        iUserHistoryService.deleteUserHistories(userHistoryIds);
    }
}
