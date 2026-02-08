package com.georgesalise.apiRepo.api.service.userhistory;

import com.georgesalise.apiRepo.api.dto.UserHistoryDTO;

import java.util.List;
import java.util.Optional;

public interface IUserHistoryService {
    List<UserHistoryDTO> getUserHistory(Long userId);
    List<UserHistoryDTO> getUserHistory(String username);
    List<UserHistoryDTO> getHistoryByIPAdd(Long ipInfoId);
    Optional<UserHistoryDTO> getUserHistoryByHistoryId(Long historyId);
    List<UserHistoryDTO> getAllUserHistory();
    void createUserHistory(Long userId, Long ipInfoId);
    void deleteUserHistory(Long id);
}
