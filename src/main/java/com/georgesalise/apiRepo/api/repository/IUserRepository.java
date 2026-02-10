package com.georgesalise.apiRepo.api.repository;

import com.georgesalise.apiRepo.api.model.IPInfo;
import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
