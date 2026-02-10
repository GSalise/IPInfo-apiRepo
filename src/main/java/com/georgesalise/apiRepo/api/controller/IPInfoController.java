package com.georgesalise.apiRepo.api.controller;

import com.georgesalise.apiRepo.api.dto.IPInfoDTO;
import com.georgesalise.apiRepo.api.service.ipinfo.IIPInfoService;
import com.georgesalise.apiRepo.api.util.CheckIP;
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

    @GetMapping
    public IPInfoDTO findIPInformation(Principal principal, HttpServletRequest request) {
        if(principal == null || request == null){
            throw new IllegalArgumentException("Principal or HTTPServletRequest cannot be null");
        }

        String username = principal.getName();
        String userIP = extractClientIP(request);
        System.out.println("Extracted IP: " + userIP);  // purposefully left for logging reasons

        if(CheckIP.isIPValid(userIP)){
            return iipInfoService.findIPAddress(username, userIP);
        } else{
            throw new IllegalArgumentException("IP address is invalid");
        }
    }

    @GetMapping("/search/{ip_address}")
    public IPInfoDTO findIPInformation(Principal principal, @PathVariable String ip_address) {
        if(principal == null || ip_address == null){
            throw new IllegalArgumentException("Principal or IP Address cannot be null");
        }

        String username = principal.getName();
        if(CheckIP.isIPValid(ip_address)){
            return iipInfoService.findIPAddress(username, ip_address);
        } else{
            throw new IllegalArgumentException("IP address is invalid");
        }

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