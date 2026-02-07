package com.georgesalise.apiRepo.api.repository;

import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository  extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
