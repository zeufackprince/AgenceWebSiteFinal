package com.agenceWebSite.AgenceWebSite.Service;


import com.agenceWebSite.AgenceWebSite.Models.OurUsers;
import com.agenceWebSite.AgenceWebSite.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type Our user details service.
 */
@Service
public class OurUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OurUsers> user = repository.findByEmail(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                .username(userObj.getUsername())
                .password(userObj.getPassword())
                .roles(user.get().getRole().name())
                .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
    
}

