package com.georgesalise.apiRepo.api.service.userhistory;

import com.georgesalise.apiRepo.api.dto.IPInfoDTO;
import com.georgesalise.apiRepo.api.dto.UserHistoryDTO;
import com.georgesalise.apiRepo.api.dto.UserHistoryWithIPInfoDTO;
import com.georgesalise.apiRepo.api.model.IPInfo;
import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.model.UserHistory;
import com.georgesalise.apiRepo.api.repository.IIPInfoRepository;
import com.georgesalise.apiRepo.api.repository.IUserHistoryRepository;
import com.georgesalise.apiRepo.api.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserHistoryServiceImpl implements IUserHistoryService{
    private final IUserHistoryRepository userHistoryRepository;
    private final IUserRepository userRepository;
    private final IIPInfoRepository iipInfoRepository;

    public UserHistoryServiceImpl(IUserHistoryRepository userHistoryRepository, IUserRepository userRepository, IIPInfoRepository iipInfoRepository) {
        this.userHistoryRepository = userHistoryRepository;
        this.userRepository = userRepository;
        this.iipInfoRepository = iipInfoRepository;
    }

    @Override
    public List<UserHistoryDTO> getUserHistory(Long userId) {
        if(userId == null){
            throw new IllegalArgumentException("User ID cannot be null");
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new IllegalArgumentException("User does not exist");
        }

        User user = optionalUser.get();
        return userHistoryRepository.findByUser(user).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserHistoryDTO> getUserHistory(String username) {
        if(username == null){
            throw new IllegalArgumentException("Username cannot be null");
        }

        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty()){
            throw new IllegalArgumentException("User does not exist");
        }

        User user = optionalUser.get();



        return userHistoryRepository.findByUser(user).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserHistoryDTO> getHistoryByIPAdd(Long ipInfoId) {
        if(ipInfoId == null){
            throw new IllegalArgumentException("UserHistory ID cannot be null");
        }

        Optional<IPInfo> optionalIPInfo = iipInfoRepository.findById(ipInfoId);
        IPInfo result;

        if(optionalIPInfo.isPresent()){
            result = optionalIPInfo.get();
        }else{
            throw new IllegalStateException("IP Information matching the id: " + ipInfoId + "could not be found");
        }

        return userHistoryRepository.findByIpInfo(result).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<UserHistoryDTO> getUserHistoryByHistoryId(Long historyId){
        if(historyId == null){
            throw new IllegalArgumentException("UserHistory ID cannot be null");
        }
        return userHistoryRepository.findById(historyId).map(this::convertToDTO);
    }

    @Override
    public List<UserHistoryWithIPInfoDTO> getUserHistoryAsIPInfo(String email) {
        List<UserHistoryDTO> userHistories = getUserHistory(email);

        List<UserHistoryDTO> activeHistories = userHistories.stream()
                .filter(UserHistoryDTO::isActive)
                .toList();

        List<Long> ipInfoIds = new ArrayList<>();
        for (UserHistoryDTO history : activeHistories) {
            ipInfoIds.add(history.ipInfoId());
        }

        List<IPInfo> ipInfos = iipInfoRepository.findAllById(ipInfoIds);

        List<IPInfo> activeIpInfos = ipInfos.stream()
                .filter(IPInfo::isActive)
                .toList();

        Map<Long, IPInfo> ipInfoMap = new HashMap<>();
        for (IPInfo ipInfo : activeIpInfos) {
            ipInfoMap.put(ipInfo.getIpInfoId(), ipInfo);
        }

        List<UserHistoryWithIPInfoDTO> result = new ArrayList<>();
        for (UserHistoryDTO history : activeHistories) {
            IPInfo matchingIpInfo = ipInfoMap.get(history.ipInfoId());
            if (matchingIpInfo != null) {
                UserHistoryWithIPInfoDTO dto = convertToIPInfoDTO(matchingIpInfo, history.historyId());
                result.add(dto);
            }
        }

        return result;
    }

    @Override
    public List<UserHistoryDTO> getAllUserHistory() {
        return userHistoryRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void createUserHistory(Long userId, Long ipInfoId) {
        if(userId == null || ipInfoId == null){
            throw new IllegalArgumentException("User Id and IpInfo Id must not be null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        IPInfo ipInfo = iipInfoRepository.findById(ipInfoId)
                .orElseThrow(() -> new IllegalArgumentException("IPInfo not found"));

        boolean alreadyExists = userHistoryRepository.existsByUserAndIpInfo(user, ipInfo);

        if(!alreadyExists){
            UserHistory userHistory = new UserHistory();
            userHistory.setUser(user);
            userHistory.setIpInfo(ipInfo);
            userHistory.setAccessedAt(LocalDateTime.now());
            userHistoryRepository.save(userHistory);
        }
    }

    public void deleteUserHistory(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("UserHistory ID cannot be null");
        }

        UserHistory userHistory = userHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("UserHistory record not found for ID: " + id));

        if (!userHistory.isActive()) {
            System.out.println("UserHistory ID " + id + " is already inactive");
            return;
        }

        userHistory.setActive(false);
        userHistoryRepository.save(userHistory);
    }


    @Override
    @Transactional
    public void deleteUserHistories(List<Long> historyIds) {
        if (historyIds == null) {
            throw new IllegalArgumentException("UserHistory IDs cannot be null");
        }

        if (historyIds.isEmpty()) {
            return;
        }

        List<Long> validIds = historyIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (validIds.isEmpty()) {
            throw new IllegalArgumentException("UserHistory IDs list contains only nulls");
        }

        List<UserHistory> histories = userHistoryRepository.findAllById(validIds);

        if (histories.isEmpty()) {
            throw new IllegalStateException("No UserHistory records found for IDs: " + validIds);
        }

        Set<Long> foundIds = new HashSet<>();
        histories.forEach(history -> {
            foundIds.add(history.getHistoryId());
            history.setActive(false);
        });

        List<Long> missingIds = validIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            System.out.println("Warning: UserHistory records not found for IDs: " + missingIds);
        }

        userHistoryRepository.saveAll(histories);
    }


    private UserHistoryDTO convertToDTO(UserHistory userHistory){
        return new UserHistoryDTO(
                userHistory.getHistoryId(),
                userHistory.getUserId(),
                userHistory.getIpInfoId(),
                userHistory.isActive(),
                userHistory.getAccessedAt()
        );
    }

    public List<UserHistoryDTO> convertToDTO(List<UserHistory> userHistoryList) {
        return userHistoryList.stream()
                .map(history -> new UserHistoryDTO(
                        history.getHistoryId(),
                        history.getUserId(),
                        history.getIpInfoId(),
                        history.isActive(),
                        history.getAccessedAt()
                ))
                .toList();
    }

    private UserHistoryWithIPInfoDTO convertToIPInfoDTO(IPInfo ipInfo, Long historyId) {
        return new UserHistoryWithIPInfoDTO(
                historyId,
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

}
