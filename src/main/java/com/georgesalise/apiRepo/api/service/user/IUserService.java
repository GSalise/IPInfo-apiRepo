package com.georgesalise.apiRepo.api.service.user;

import com.georgesalise.apiRepo.api.dto.UserAuthenticationDTO;
import com.georgesalise.apiRepo.api.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    // Actively used by the controllers
    String registerUser(UserAuthenticationDTO userAuthenticationDTO);
    String verifyUser(UserAuthenticationDTO userAuthenticationDTO);
    Boolean verifyToken(String token);

    // Being used internally

    // Inactive
    Optional<UserDTO> getUser(Long id);
    List<UserDTO> getAllUsers();
    void deleteUser(Long id);
}
