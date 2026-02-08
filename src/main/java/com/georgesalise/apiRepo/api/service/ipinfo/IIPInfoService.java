package com.georgesalise.apiRepo.api.service.ipinfo;

import com.georgesalise.apiRepo.api.dto.IPInfoDTO;

import java.util.List;
import java.util.Optional;

public interface IIPInfoService {
    Optional<IPInfoDTO> getIpInfo(Long id);
    List<IPInfoDTO> getAllIpInfo();
    IPInfoDTO createIPInfo(IPInfoDTO ipInfoDTO);
    IPInfoDTO updateIpInfo(Long id, IPInfoDTO ipInfoDTO);
    void deleteIPInfo(Long id);


    void setIsCurrentIP(Long userId, Long ipInfoId);
    IPInfoDTO findIPAddress(String username);
    IPInfoDTO findIPAddress(String username, String ipAddress);
}
