package com.agenceWebSite.AgenceWebSite.Repository;

import com.agenceWebSite.AgenceWebSite.Models.Belongings;
import com.agenceWebSite.AgenceWebSite.Models.Enums.BelongingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("SELECT DISTINCT b.type FROM Belongings b")
    List<Belongings> findDistinctBelongingType();
}
