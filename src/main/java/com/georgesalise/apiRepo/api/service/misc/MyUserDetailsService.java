package com.georgesalise.apiRepo.api.service.misc;

import com.georgesalise.apiRepo.api.model.User;
import com.georgesalise.apiRepo.api.model.UserPrincipal;
import com.georgesalise.apiRepo.api.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private IUserRepository userRepository;

    public MyUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        User user;

        if(optionalUser.isPresent()){
            user = optionalUser.get();
        } else{
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }
}
