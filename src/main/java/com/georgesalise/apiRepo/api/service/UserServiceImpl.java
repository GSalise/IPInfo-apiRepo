package com.georgesalise.apiRepo.api.service;

import com.georgesalise.apiRepo.api.dto.UserCreateDTO;
import com.georgesalise.apiRepo.api.dto.UserDTO;
import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDTO> getUser(Long id) {
        if(id == null){
            return Optional.empty();
        }
        return userRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        if(userCreateDTO == null){
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        User user = convertToEntity(userCreateDTO);
        User saved_user = userRepository.save(user);
        return convertToDTO(saved_user);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null){
            throw  new IllegalArgumentException("User id cannot be null");
        }
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user){
        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    private User convertToEntity(UserCreateDTO userCreateDTO){
        User user = new User();
        user.setEmail(userCreateDTO.email());
        user.setPasswordHash(encoder.encode(userCreateDTO.password()));
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }


}
