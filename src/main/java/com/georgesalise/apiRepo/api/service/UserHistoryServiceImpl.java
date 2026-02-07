package com.georgesalise.apiRepo.api.service;

import com.georgesalise.apiRepo.api.dto.UserHistoryDTO;
import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.model.UserHistory;
import com.georgesalise.apiRepo.api.repository.IUserHistory;
import com.georgesalise.apiRepo.api.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserHistoryServiceImpl implements IUserHistoryService{
    private final IUserHistory userHistoryRepository;

    public UserHistoryServiceImpl(IUserHistory userHistoryRepository){
        this.userHistoryRepository = userHistoryRepository;
    }

    @Override
    public List<UserHistoryDTO> getUserHistory(Long userId) {
        if(userId == null){
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return userHistoryRepository.findByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserHistoryDTO> getHistoryByIPAdd(Long ipInfoId) {
        if(ipInfoId == null){
            throw new IllegalArgumentException("UserHistory ID cannot be null");
        }
        return userHistoryRepository.findByIpInfoId(ipInfoId).stream().map(this::convertToDTO).collect(Collectors.toList());
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
    public UserHistoryDTO createUserHistory(UserHistoryDTO userHistoryDTO) {
        if(userHistoryDTO == null){
            throw new IllegalArgumentException("UserHistoryDTO cannot be null");
        }
        UserHistory userHistory = convertTOEntity(userHistoryDTO);
        UserHistory saved_userHistory = userHistoryRepository.save(userHistory);
        return convertToDTO(saved_userHistory);
    }

    @Override
    public UserHistoryDTO updateUserHistory(Long id, UserHistoryDTO userHistoryDTO) {
        return null;
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

    private UserHistory convertTOEntity(UserHistoryDTO userHistoryDTO){
        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(userHistoryDTO.userId());
        userHistory.setIpInfoId(userHistoryDTO.ipInfoId());
        userHistory.setAccessedAt(LocalDateTime.now());
        return  userHistory;
    }

}
