package com.agenceWebSite.AgenceWedSite.Controllers;

import com.agenceWebSite.AgenceWedSite.Repository.UserRepository;
import com.agenceWebSite.AgenceWedSite.model.Enums.Role;
import com.agenceWebSite.AgenceWedSite.model.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    /**
     * Create user user.
     *
     * @param request the request
     * @return the user
     */
    @PostMapping("/users/register")
    public User createUser(@RequestBody User request)
    {
        Optional<User> user = this.userRepository.findByEmail(request.getEmail());

        if (user.isEmpty()){
            User user1 = new User();

            user1.setName(request.getName());
            user1.setEmail(request.getEmail());
            user1.setUsername(request.getUsername());
            user1.setPassword(request.getPassword());

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

    /**
     * User list list.
     *
     * @return the list
     */
    @GetMapping("/users/all")
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
    @GetMapping("/users/{role}")
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

        userRepository.save(existingUser);
        return ResponseEntity.ok("UPDATED");
    }
}
