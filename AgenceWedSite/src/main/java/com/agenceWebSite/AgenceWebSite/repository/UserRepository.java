package com.agenceWebSite.AgenceWebSite.repository;

import com.agenceWebSite.AgenceWebSite.entity.Enums.Role;
import com.agenceWebSite.AgenceWebSite.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<OurUsers, Long> {

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<OurUsers> findByEmail(String email);

    /**
     * Find by role list.
     *
     * @param role the role
     * @return the list
     */
    List<OurUsers> findByRole(Role role);
}
