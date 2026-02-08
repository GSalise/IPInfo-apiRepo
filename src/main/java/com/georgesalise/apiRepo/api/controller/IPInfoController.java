package com.georgesalise.apiRepo.api.controller;

import com.georgesalise.apiRepo.api.dto.IPInfoDTO;
import com.georgesalise.apiRepo.api.service.ipinfo.IIPInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/ipinfo")
public class IPInfoController {
    private final IIPInfoService iipInfoService;

    public IPInfoController(IIPInfoService iipInfoService) {
        this.iipInfoService = iipInfoService;
    }

    @PostMapping
    public IPInfoDTO findIPInformation(Principal principal, HttpServletRequest request) {
        String username = principal.getName();
        String userIP = extractClientIP(request);
        System.out.println("Extracted IP: " + userIP);
        return iipInfoService.findIPAddress(username, userIP);
    }

    @PostMapping("/search/{ip_address}")
    public IPInfoDTO findIPInformation(Principal principal, @PathVariable String ip_address) {
        String username = principal.getName();
        return iipInfoService.findIPAddress(username, ip_address);
    }

    private String extractClientIP(HttpServletRequest request) {
        String[] headers = {
            "CF-Connecting-IP",
            "X-Forwarded-For",
            "X-Real-IP",
            "True-Client-IP",
            "X-Client-IP"
        };

        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {

                return ip.split(",")[0].trim();
            }
        }

        return request.getRemoteAddr();
    }
}