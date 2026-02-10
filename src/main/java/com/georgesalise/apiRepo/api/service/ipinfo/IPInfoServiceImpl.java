package com.georgesalise.apiRepo.api.service.ipinfo;

import com.georgesalise.apiRepo.api.dto.IPGeoAPIDTO;
import com.georgesalise.apiRepo.api.dto.IPInfoDTO;
import com.georgesalise.apiRepo.api.model.IPInfo;
import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.repository.IIPInfoRepository;
import com.georgesalise.apiRepo.api.repository.IUserRepository;
import com.georgesalise.apiRepo.api.service.userhistory.IUserHistoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IPInfoServiceImpl implements IIPInfoService{
    private final IIPInfoRepository ipInfoRepository;
    private final IUserRepository userRepository;
    private final IUserHistoryService userHistoryService;
    private final WebClient webClient;

    public IPInfoServiceImpl(IIPInfoRepository ipInfoRepository, IUserRepository userRepository, IUserHistoryService userHistoryService, WebClient webClient) {
        this.ipInfoRepository = ipInfoRepository;
        this.userRepository = userRepository;
        this.userHistoryService = userHistoryService;
        this.webClient = webClient;
    }

    @Override
    public Optional<IPInfoDTO> getIpInfo(Long id) {
        if(id == null){
            throw new IllegalArgumentException("IPInfo id cannot be null");
        }
        return ipInfoRepository.findById(id).map(this::converToDTO);
    }

    @Override
    public List<IPInfoDTO> getAllIpInfo() {
        return ipInfoRepository.findAll().stream().map(this::converToDTO).collect(Collectors.toList());
    }

    @Override
    public IPInfoDTO createIPInfo(IPInfoDTO ipInfoDTO) {
        if(ipInfoDTO == null){
            throw new IllegalArgumentException("IPInfoDTO cannot be null");
        }
        IPInfo ipInfo = converToEntity(ipInfoDTO);
        IPInfo saved_ipInfo = ipInfoRepository.save(ipInfo);
        return converToDTO(saved_ipInfo);
    }

    public IPInfoDTO createIPInfo(IPGeoAPIDTO ipGeoAPIDTO) {
        if(ipGeoAPIDTO == null){
            throw new IllegalArgumentException("IPGeoAPIDTO cannot be null");
        }
        IPInfo ipInfo = converToEntity(ipGeoAPIDTO);
        IPInfo saved_ipInfo = ipInfoRepository.save(ipInfo);
        return converToDTO(saved_ipInfo);
    }

    @Override
    public void deleteIPInfo(Long id) {
        if (id == null){
            throw  new IllegalArgumentException("IPInfo id cannot be null");
        }
        ipInfoRepository.deleteById(id);
        IPInfo ipInfo = ipInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("IPInfo not found"));

        if (ipInfo.isActive()) {
            ipInfo.setActive(false);
            ipInfoRepository.save(ipInfo);
        }
    }

    @Override
    public IPInfoDTO findIPAddress(String email, String ipAddress) {
        IPGeoAPIDTO apiResponse;
        try {
            apiResponse = webClient
                    .get()
                    .uri("/{ipAddress}/json", ipAddress)
                    .retrieve()
                    .bodyToMono(IPGeoAPIDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Failed to call ipinfo.io" + e);
        }

        if (apiResponse == null) {
            throw new IllegalStateException("Failed to retrieve IP information from API since it returned null");
        }

        Optional<IPInfo> existingIPInfo = ipInfoRepository.findByIpAddress(apiResponse.ip());
        IPInfoDTO result;

        // Check if the IP already exists in the db
        // If there are any changes to the old ipinfo, update it
        if(existingIPInfo.isPresent()){
            updateIpInfo(existingIPInfo.get(), apiResponse);
            result = converToDTO(existingIPInfo.get());
        }else{
            result = createIPInfo(apiResponse);
        }

        // Check if user exists, although it will always be present since the email is found in the token itself
        // If not, then something weird is going on
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException(
                        "User from JWT token not found: " + email + " - This should never happen!"
                ));

        // Create a history record
        userHistoryService.createUserHistory(user.getUserId(), result.ipInfoId());
        return result;
    }


    @Override
    @Transactional
    public void updateIpInfo(IPInfo oldIPInfo, IPGeoAPIDTO ipInfoFromGeoAPI) {
        IPInfo newIPInfo = converToEntity(ipInfoFromGeoAPI);

        if(!Objects.equals(oldIPInfo.getCity(), newIPInfo.getCity())){
            oldIPInfo.setCity(newIPInfo.getCity());
        }
        if(!Objects.equals(oldIPInfo.getRegion(), newIPInfo.getRegion())){
            oldIPInfo.setRegion(newIPInfo.getRegion());
        }

        if(!Objects.equals(oldIPInfo.getCountry(), newIPInfo.getCountry())){
            oldIPInfo.setCountry(newIPInfo.getCountry());
        }

        if(!Objects.equals(oldIPInfo.getPostal(), newIPInfo.getPostal())){
            oldIPInfo.setPostal(newIPInfo.getPostal());
        }

        if(!Objects.equals(oldIPInfo.getLatitude(), newIPInfo.getLatitude())){
            oldIPInfo.setLatitude(newIPInfo.getLatitude());
        }

        if(!Objects.equals(oldIPInfo.getLongitude(), newIPInfo.getLongitude())){
            oldIPInfo.setLongitude(newIPInfo.getLongitude());
        }

    }

    private IPInfoDTO converToDTO(IPInfo ipInfo){
        return new IPInfoDTO(
                ipInfo.getIpInfoId(),
                ipInfo.getIpAddress(),
                ipInfo.getCity(),
                ipInfo.getRegion(),
                ipInfo.getCountry(),
                ipInfo.getPostal(),
                ipInfo.getLatitude(),
                ipInfo.getLongitude(),
                ipInfo.isActive(),
                ipInfo.getCreatedAt()
        );
    }


    private IPInfo converToEntity(IPInfoDTO ipInfoDTO){
        IPInfo ipInfo = new IPInfo();
        ipInfo.setIpAddress(ipInfoDTO.ipAddress());
        ipInfo.setCity(ipInfoDTO.city());
        ipInfo.setRegion(ipInfoDTO.region());
        ipInfo.setCountry(ipInfoDTO.country());
        ipInfo.setPostal(ipInfoDTO.postal());
        ipInfo.setLatitude(ipInfoDTO.latitude());
        ipInfo.setLongitude(ipInfoDTO.longitude());
        ipInfo.setActive(true);
        ipInfo.setCreatedAt(LocalDateTime.now());
        return ipInfo;
    }

    private IPInfo converToEntity(IPGeoAPIDTO ipGeoAPIDTO){
        IPInfo ipInfo = new IPInfo();
        ipInfo.setIpAddress(ipGeoAPIDTO.ip());
        ipInfo.setCity(ipGeoAPIDTO.city());
        ipInfo.setRegion(ipGeoAPIDTO.region());
        ipInfo.setCountry(ipGeoAPIDTO.country());
        ipInfo.setPostal(ipGeoAPIDTO.postal());
        ipInfo.setLatitude(ipGeoAPIDTO.getLatitude());
        ipInfo.setLongitude(ipGeoAPIDTO.getLongitude());
        ipInfo.setActive(true);
        ipInfo.setCreatedAt(LocalDateTime.now());
        return ipInfo;
    }
}
