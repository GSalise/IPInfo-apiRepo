package com.georgesalise.apiRepo.api.service;

import com.georgesalise.apiRepo.api.dto.IPInfoBaseDTO;
import com.georgesalise.apiRepo.api.dto.IPInfoDetailedDTO;

import java.util.List;
import java.util.Optional;

public interface IIPInfoService {
    Optional<IPInfoDetailedDTO> getIpInfo(Long id);
    List<IPInfoDetailedDTO> getAllIpInfo();
    IPInfoDetailedDTO createIPInfo(IPInfoDetailedDTO ipInfoDetailedDTO);
    IPInfoBaseDTO createIPInfo(IPInfoBaseDTO ipInfoBaseDTO);
    IPInfoDetailedDTO updateIpInfo(Long id, IPInfoDetailedDTO ipInfoDetailedDTO);
    void deleteIPInfo(Long id);

    void setIsCurrentIP(Long userId, Long ipInfoId);
}
