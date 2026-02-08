package com.georgesalise.apiRepo.api.controller;

import com.georgesalise.apiRepo.api.dto.IPInfoDTO;
import com.georgesalise.apiRepo.api.service.ipinfo.IIPInfoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/ipinfo")
public class IPInfoController {
    private final IIPInfoService iipInfoService;

    public IPInfoController(IIPInfoService iipInfoService) {
        this.iipInfoService = iipInfoService;
    }

    @PostMapping
    public IPInfoDTO findIPInformation(Principal principal) {
        String username = principal.getName();
        return iipInfoService.findIPAddress(username);
    }

    @PostMapping("/search/{ip_address}")
    public IPInfoDTO findIPInformation(Principal principal, @PathVariable String ip_address) {
        String username = principal.getName();
        return iipInfoService.findIPAddress(username, ip_address);
    }


}
