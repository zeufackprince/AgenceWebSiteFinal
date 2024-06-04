//package com.agenceWebSite.AgenceWedSite.Config;
//
//import com.agenceWebSite.AgenceWedSite.Repository.UserRepository;
//import com.agenceWebSite.AgenceWedSite.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.io.Serializable;
//import java.util.Optional;
//
//@Service
//public class MyUserDetailService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = repository.findByUsername(username);
//        if (user.isPresent()) {
//            var userObj = user.get();
//            return org.springframework.security.core.userdetails.User.builder()
//                    .username(userObj.getUsername())
//                    .password(userObj.getPassword())
//                    .roles(String.valueOf(getRoles(userObj)))
//                    .build();
//        } else {
//            throw new UsernameNotFoundException(username);
//        }
//    }
//
//    private Serializable getRoles(User user) {
//        if (user.getRole() == null) {
//            return new String[]{"USER"};
//        }
//        return user.getRole();
//    }
//}