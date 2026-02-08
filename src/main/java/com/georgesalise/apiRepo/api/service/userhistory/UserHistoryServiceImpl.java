package com.georgesalise.apiRepo.api.service.userhistory;

import com.georgesalise.apiRepo.api.dto.UserHistoryDTO;
import com.georgesalise.apiRepo.api.model.IPInfo;
import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.model.UserHistory;
import com.georgesalise.apiRepo.api.repository.IIPInfoRepository;
import com.georgesalise.apiRepo.api.repository.IUserHistoryRepository;
import com.georgesalise.apiRepo.api.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    @Override
    public void deleteUserHistory(Long id) {
        if (id == null){
            throw new IllegalArgumentException("UserHistory ID cannot be null");
        }
        userHistoryRepository.deleteById(id);
    }

    private UserHistoryDTO convertToDTO(UserHistory userHistory){
        return new UserHistoryDTO( userHistory.getHistoryId(),
        userHistory.getUserId(),
        userHistory.getIpInfoId(),
        userHistory.getAccessedAt()
        );
    }

    public List<UserHistoryDTO> convertToDTO(List<UserHistory> userHistoryList) {
        return userHistoryList.stream()
                .map(history -> new UserHistoryDTO(
                        history.getHistoryId(),
                        history.getUserId(),
                        history.getIpInfoId(),
                        history.getAccessedAt()
                ))
                .toList();
    }
}
