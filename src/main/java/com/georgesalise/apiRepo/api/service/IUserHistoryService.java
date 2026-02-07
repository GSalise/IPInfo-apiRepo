package com.georgesalise.apiRepo.api.service;

import com.georgesalise.apiRepo.api.dto.UserHistoryDTO;

import java.util.List;
import java.util.Optional;

public interface IUserHistoryService {
    List<UserHistoryDTO> getUserHistory(Long userId);
    List<UserHistoryDTO> getHistoryByIPAdd(Long ipInfoId);
    Optional<UserHistoryDTO> getUserHistoryByHistoryId(Long historyId);
    List<UserHistoryDTO> getAllUserHistory();
    UserHistoryDTO createUserHistory(UserHistoryDTO userHistoryDTO);
    UserHistoryDTO updateUserHistory(Long id, UserHistoryDTO userHistoryDTO);
    void deleteUserHistory(Long id);
}
