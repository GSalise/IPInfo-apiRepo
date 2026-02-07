package com.georgesalise.apiRepo.api.service;

import com.georgesalise.apiRepo.api.dto.UserCreateDTO;
import com.georgesalise.apiRepo.api.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<UserDTO> getUser(Long id);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserCreateDTO userCreateDTO);
    void deleteUser(Long id);
}
