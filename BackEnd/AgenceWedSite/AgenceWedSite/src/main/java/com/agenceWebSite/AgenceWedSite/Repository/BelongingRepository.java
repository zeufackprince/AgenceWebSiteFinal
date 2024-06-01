package com.agenceWebSite.AgenceWedSite.Repository;

import com.agenceWebSite.AgenceWedSite.model.Belongings;
import com.agenceWebSite.AgenceWedSite.model.Enums.BelongingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Belonging repository.
 */
public interface BelongingRepository extends JpaRepository<Belongings, Long> {


    /**
     * Find by type optional.
     *
     * @param type the type
     * @return the optional
     */
    Optional<List<Belongings>> findByType(BelongingType type);

}
