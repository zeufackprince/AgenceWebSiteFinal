package com.agenceWebSite.AgenceWedSite.Repository;


import com.agenceWebSite.AgenceWedSite.model.Enums.Role;
import com.agenceWebSite.AgenceWedSite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername(String username);

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<User> findByEmail(String email);

    /**
     * Find by role list.
     *
     * @param role the role
     * @return the list
     */
    List<User> findByRole(Role role);
}
