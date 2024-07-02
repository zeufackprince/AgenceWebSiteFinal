package AgencyWebSite.AgencyWebSite.Repository;

import AgencyWebSite.AgencyWebSite.Models.Enums.Roles;
import AgencyWebSite.AgencyWebSite.Models.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

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
    List<OurUsers> findByRole(Roles role);
}
