package com.georgesalise.apiRepo.api.service.user;

import com.georgesalise.apiRepo.api.dto.UserAuthenticationDTO;
import com.georgesalise.apiRepo.api.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<UserDTO> getUser(Long id);
    List<UserDTO> getAllUsers();
    String registerUser(UserAuthenticationDTO userAuthenticationDTO);
    String verifyUser(UserAuthenticationDTO userAuthenticationDTO);
    void deleteUser(Long id);
}
