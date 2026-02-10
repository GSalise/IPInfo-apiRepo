package com.georgesalise.apiRepo.api.service.ipinfo;

import com.georgesalise.apiRepo.api.dto.IPGeoAPIDTO;
import com.georgesalise.apiRepo.api.dto.IPInfoDTO;
import com.georgesalise.apiRepo.api.model.IPInfo;

import java.util.List;
import java.util.Optional;

public interface IIPInfoService {
    // Actively used by the controller
    IPInfoDTO findIPAddress(String username, String ipAddress);
    void updateIpInfo(IPInfo ipInfo, IPGeoAPIDTO ipGeoAPIDTO);

    // Being used internally
    IPInfoDTO createIPInfo(IPInfoDTO ipInfoDTO);

    // Inactive
    Optional<IPInfoDTO> getIpInfo(Long id);
    List<IPInfoDTO> getAllIpInfo();
    void deleteIPInfo(Long id);
}
