package com.agenceWebSite.AgenceWedSite.Controllers;

import com.agenceWebSite.AgenceWedSite.DTO.LoginRequest;
import com.agenceWebSite.AgenceWedSite.Repository.UserRepository;
import com.agenceWebSite.AgenceWedSite.model.Enums.Role;
import com.agenceWebSite.AgenceWedSite.model.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The type User controller.
 */
@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserRepository userRepository;


    /**
     * Instantiates a new User controller.
     *
     * @param userRepository the user repository
     */
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


//    @PostMapping("/users/register")
//    public ResponseEntity<User> registerUser(@RequestBody User user) {
//        User registeredUser = userService.register(user);
//        return ResponseEntity.ok(registeredUser);
//    }
//
//    @PostMapping("/users/login")
//    public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) {
//        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
//        // You can return a success message or create a session for the user
//        // based on your security needs. Here's an example returning the user:
//        return ResponseEntity.ok(user);
//    }

    /**
     * Hash password string.
     *
     * @param password the password
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    /**
     * Create user user.
     *
     * @param request the request
     * @return the user
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    @PostMapping("/users/register")
    public User createUser(@RequestBody User request) throws NoSuchAlgorithmException {
        Optional<User> user = this.userRepository.findByEmail(request.getEmail());

        if (user.isEmpty()){
            User user1 = new User();

            user1.setName(request.getName());
            user1.setEmail(request.getEmail());
            user1.setUsername(request.getUsername());
            user1.setTelephone(request.getTelephone());

            String hashedPassword = hashPassword(request.getPassword());
            user1.setPassword(hashedPassword);
            user1.setImageData(request.getImageData());
//            user1.setPassword(request.getPassword());
            Role role = request.getRole();
            switch (role) {
                case USER, AGENT, ADMIN -> user1.setRole(role);
                default -> throw new IllegalStateException("Type does not exist");
            }

            return this.userRepository.save(user1);
        }else {

            throw new EntityExistsException("User already exist with Email : " + request.getEmail());
        }
    }

    User[] connected;

    /**
     * Login response entity.
     *
     * @param loginRequest the login request
     * @return the response entity
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws NoSuchAlgorithmException {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isPresent()) {
            User storedUser = user.get();
             connected = new User[]{storedUser};
            String providedPasswordHash = hashPassword(loginRequest.getPassword());
            if (storedUser.getPassword().equals(providedPasswordHash)) {

                return ResponseEntity.ok("Login successful");
            } else {
                throw new IllegalArgumentException("Incorrect password");
            }
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @GetMapping("/admin/connected")
    public User[] conUsers(){
        return connected;
    }

    /**
     * User list list.
     *
     * @return the list
     */
    @GetMapping("/admin/users/all")
    public List<User> userList()
    {
        return this.userRepository.findAll();
    }

    /**
     * Gets users by role.
     *
     * @param role the role
     * @return the users by role
     */
    @GetMapping("/admin/users/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Role role) {
        List<User> usersByRole;
        switch (role) {
            case USER:
                usersByRole = userRepository.findByRole(Role.USER);
                break;
            case AGENT:
                usersByRole = userRepository.findByRole(Role.AGENT);
                break;
            case ADMIN:
                usersByRole = userRepository.findByRole(Role.ADMIN);
                break;
            default:
                usersByRole = Collections.emptyList();
        }
        return ResponseEntity.ok(usersByRole);
    }

    /**
     * Update user response entity.
     *
     * @param id   the id
     * @param user the user
     * @return the response entity
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id :" + id + "Not Found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setTelephone(user.getTelephone());

        userRepository.save(existingUser);
        return ResponseEntity.ok("UPDATED");
    }

//    @RestController
//    @RequestMapping("/api/v1/users")
//    public class RegistrationController {


    }


