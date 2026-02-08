package com.georgesalise.apiRepo.api.service.user;

import com.georgesalise.apiRepo.api.dto.UserAuthenticationDTO;
import com.georgesalise.apiRepo.api.dto.UserDTO;
import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.repository.IUserRepository;
import com.georgesalise.apiRepo.api.service.misc.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

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
    public String createUser(UserAuthenticationDTO userAuthenticationDTO) {
        if(userAuthenticationDTO == null){
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        User user = convertToEntity(userAuthenticationDTO);
        User saved_user = userRepository.save(user);
        return jwtService.generateToken(userAuthenticationDTO.email());
    }

    public String verifyUser(UserAuthenticationDTO userAuthenticationDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthenticationDTO.email(), userAuthenticationDTO.password())
        );

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(userAuthenticationDTO.email());
        } else {
            return "Authentication failed!";
        }
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

    private User convertToEntity(UserAuthenticationDTO userAuthenticationDTO){
        User user = new User();
        user.setEmail(userAuthenticationDTO.email());
        user.setPasswordHash(encoder.encode(userAuthenticationDTO.password()));
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }


}
