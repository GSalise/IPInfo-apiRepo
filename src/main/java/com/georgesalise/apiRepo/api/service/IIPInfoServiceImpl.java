package com.georgesalise.apiRepo.api.service;

import com.georgesalise.apiRepo.api.dto.IPInfoBaseDTO;
import com.georgesalise.apiRepo.api.dto.IPInfoDetailedDTO;
import com.georgesalise.apiRepo.api.model.IPInfo;
import com.georgesalise.apiRepo.api.repository.IIPInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IIPInfoServiceImpl implements IIPInfoService{
    private final IIPInfoRepository iipInfoRepository;
    private final UserHistoryServiceImpl userHistoryService;

    public IIPInfoServiceImpl(IIPInfoRepository iipInfoRepository, UserHistoryServiceImpl userHistoryService){
        this.iipInfoRepository = iipInfoRepository;
        this.userHistoryService = userHistoryService;
    }

    @Override
    public Optional<IPInfoDetailedDTO> getIpInfo(Long id) {
        if(id == null){
            throw new IllegalArgumentException("IPInfo id cannot be null");
        }
        return iipInfoRepository.findById(id).map(this::converToDTODetailed);
    }

    @Override
    public List<IPInfoDetailedDTO> getAllIpInfo() {
        return iipInfoRepository.findAll().stream().map(this::converToDTODetailed).collect(Collectors.toList());
    }

    @Override
    public IPInfoDetailedDTO createIPInfo(IPInfoDetailedDTO ipInfoDetailedDTO) {
        if(ipInfoDetailedDTO == null){
            throw new IllegalArgumentException("IPInfoDTO cannot be null");
        }
        IPInfo ipInfo = converToEntity(ipInfoDetailedDTO);
        IPInfo saved_ipInfo = iipInfoRepository.save(ipInfo);
        return converToDTODetailed(saved_ipInfo);

    }

    @Override
    public IPInfoBaseDTO createIPInfo(IPInfoBaseDTO ipInfoBaseDTO) {
        if(ipInfoBaseDTO == null){
            throw new IllegalArgumentException("IPInfoBase cannot be null");
        }
        IPInfo ipInfo = converToEntity(ipInfoBaseDTO);
        IPInfo saved_ipInfo = iipInfoRepository.save(ipInfo);
        return converToDTOBase(saved_ipInfo);
    }

    @Override
    public IPInfoDetailedDTO updateIpInfo(Long id, IPInfoDetailedDTO ipInfoDetailedDTO) {
        return null;
    }

    @Override
    public void deleteIPInfo(Long id) {
        if (id == null){
            throw  new IllegalArgumentException("IPInfo id cannot be null");
        }
        iipInfoRepository.deleteById(id);
    }

    @Override
    public void setIsCurrentIP(Long userId, Long ipadd_id) {

    }

    private IPInfoDetailedDTO converToDTODetailed(IPInfo ipInfo){
        return new IPInfoDetailedDTO(
                ipInfo.getIpInfoId(),
                ipInfo.getIpAddress(),
                ipInfo.getIsCurrentIp(),
                ipInfo.getCountry(),
                ipInfo.getCity(),
                ipInfo.getZipcode(),
                ipInfo.getLatitude(),
                ipInfo.getLongitude(),
                ipInfo.getCreatedAt()
        );
    }
    private IPInfoBaseDTO converToDTOBase(IPInfo ipInfo){
        return new IPInfoBaseDTO(
                ipInfo.getIpInfoId(),
                ipInfo.getIpAddress(),
                ipInfo.getIsCurrentIp(),
                ipInfo.getCountry()
        );
    }

    private IPInfo converToEntity(IPInfoDetailedDTO ipInfoDetailedDTO){
        IPInfo ipInfo = new IPInfo();
        ipInfo.setIpAddress(ipInfoDetailedDTO.ipAddress());
        ipInfo.setIsCurrentIp(ipInfoDetailedDTO.isCurrentIp());
        ipInfo.setCountry(ipInfoDetailedDTO.country());
        ipInfo.setCity(ipInfoDetailedDTO.city());
        ipInfo.setZipcode(ipInfoDetailedDTO.zipcode());
        ipInfo.setLatitude(ipInfoDetailedDTO.latitude());
        ipInfo.setLongitude(ipInfoDetailedDTO.longitude());
        ipInfo.setCreated_at(LocalDateTime.now());
        return ipInfo;
    }
    private IPInfo converToEntity(IPInfoBaseDTO ipInfoBaseDTO){
        IPInfo ipInfo = new IPInfo();
        ipInfo.setIpAddress(ipInfoBaseDTO.ipAddress());
        ipInfo.setIsCurrentIp(ipInfoBaseDTO.isCurrentIp());
        ipInfo.setCountry(ipInfoBaseDTO.country());
        ipInfo.setCreated_at(LocalDateTime.now());
        return ipInfo;
    }
}
