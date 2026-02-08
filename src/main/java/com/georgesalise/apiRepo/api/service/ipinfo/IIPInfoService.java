package com.georgesalise.apiRepo.api.service.ipinfo;

import com.georgesalise.apiRepo.api.dto.IPGeoAPIDTO;
import com.georgesalise.apiRepo.api.dto.IPInfoDTO;
import com.georgesalise.apiRepo.api.model.IPInfo;

import java.util.List;
import java.util.Optional;

public interface IIPInfoService {
    Optional<IPInfoDTO> getIpInfo(Long id);
    List<IPInfoDTO> getAllIpInfo();
    IPInfoDTO createIPInfo(IPInfoDTO ipInfoDTO);
    void deleteIPInfo(Long id);


    IPInfoDTO findIPAddress(String username);
    IPInfoDTO findIPAddress(String username, String ipAddress);
    void updateIpInfo(IPInfo ipInfo, IPGeoAPIDTO ipGeoAPIDTO);
}
