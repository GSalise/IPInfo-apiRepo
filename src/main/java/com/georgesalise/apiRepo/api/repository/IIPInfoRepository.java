package com.georgesalise.apiRepo.api.repository;

import com.georgesalise.apiRepo.api.model.IPInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IIPInfoRepository extends JpaRepository<IPInfo, Long> {
    Optional<IPInfo> findByIpAddress(String ipAddress);
}
