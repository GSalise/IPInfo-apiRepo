package com.georgesalise.apiRepo.api.service.userhistory;

import com.georgesalise.apiRepo.api.dto.IPInfoDTO;
import com.georgesalise.apiRepo.api.dto.UserHistoryDTO;
import com.georgesalise.apiRepo.api.dto.UserHistoryWithIPInfoDTO;

import java.util.List;
import java.util.Optional;

public interface IUserHistoryService {
    // Actively used by the controller
    List<UserHistoryWithIPInfoDTO> getUserHistoryAsIPInfo(String email);
    void deleteUserHistories(List<Long> historyIds);

    // Being used internally
    void createUserHistory(Long userId, Long ipInfoId);
    List<UserHistoryDTO> getUserHistory(String email);

    // Inactive
    List<UserHistoryDTO> getUserHistory(Long userId);
    List<UserHistoryDTO> getHistoryByIPAdd(Long ipInfoId);
    Optional<UserHistoryDTO> getUserHistoryByHistoryId(Long historyId);
    List<UserHistoryDTO> getAllUserHistory();
    void deleteUserHistory(Long id);
}
