package com.georgesalise.apiRepo.api.service.user;

import com.georgesalise.apiRepo.api.dto.UserAuthenticationDTO;
import com.georgesalise.apiRepo.api.dto.UserDTO;
import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.model.UserHistory;
import com.georgesalise.apiRepo.api.repository.IUserRepository;
import com.georgesalise.apiRepo.api.service.misc.CustomUserDetailsService;
import com.georgesalise.apiRepo.api.service.misc.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
    CustomUserDetailsService userDetailsService;

    @Autowired
    private JWTService jwtService;

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDTO> getUser(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        try {
            return userRepository.findById(id).map(this::convertToDTO);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user with id " + id, e);
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            return userRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

    @Override
    public String registerUser(UserAuthenticationDTO userAuthenticationDTO) {
        if (userAuthenticationDTO == null) {
            throw new IllegalArgumentException("User email and password cannot be null");
        }

        if (userRepository.existsByEmail(userAuthenticationDTO.email())) {
            throw new IllegalArgumentException("A user with this email already exists");
        }

        try {
            User user = convertToEntity(userAuthenticationDTO);
            User savedUser = userRepository.save(user);
            return jwtService.generateToken(userAuthenticationDTO.email());

        } catch (Exception e) {
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
    }



    public String verifyUser(UserAuthenticationDTO userAuthenticationDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuthenticationDTO.email(), userAuthenticationDTO.password())
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(userAuthenticationDTO.email());
            } else {
                throw new IllegalStateException("Authentication failed for unknown reason");
            }

        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    @Override
    public Boolean verifyToken(String token) {
        try {
            String email = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            return jwtService.validateToken(token, userDetails);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            if (user.isActive()) {
                user.setActive(false);
                userRepository.save(user);
            }

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }


    private UserDTO convertToDTO(User user){
        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.isActive(),
                user.getCreatedAt()
        );
    }

    private User convertToEntity(UserAuthenticationDTO userAuthenticationDTO){
        User user = new User();
        user.setEmail(userAuthenticationDTO.email());
        user.setPasswordHash(encoder.encode(userAuthenticationDTO.password()));
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }


}
