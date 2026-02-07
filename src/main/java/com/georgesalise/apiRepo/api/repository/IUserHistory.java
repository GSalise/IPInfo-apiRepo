package com.georgesalise.apiRepo.api.repository;

import com.georgesalise.apiRepo.api.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserHistory extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findByUserId(Long userId);
    List<UserHistory> findByIpInfoId(Long ipInfoId);
}
