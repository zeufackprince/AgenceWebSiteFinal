//package com.agenceWebSite.AgenceWedSite.Service;
//
//import com.agenceWebSite.AgenceWedSite.Repository.UserRepository;
//import com.agenceWebSite.AgenceWedSite.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UsersService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder; // Assuming BCrypt for password encoding
//
//
//    public User register(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
//
//    public User login(String email, String password) {
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                return user;
//            } else {
//                throw new IllegalArgumentException("Invalid password");
//            }
//        } else {
//            throw new IllegalArgumentException("User not found");
//        }
//    }
//}
//
