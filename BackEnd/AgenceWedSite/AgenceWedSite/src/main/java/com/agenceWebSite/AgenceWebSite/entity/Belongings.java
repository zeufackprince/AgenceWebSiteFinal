package com.agenceWebSite.AgenceWebSite.entity;

import com.agenceWebSite.AgenceWebSite.entity.Enums.BelongingType;
import com.agenceWebSite.AgenceWebSite.entity.Enums.Cities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Belongings.
 */
@Entity
@Table
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class Belongings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private BelongingType type;

    private String dimension;

    private Cities localisation;

    private double prix;

    private String images;

    @OneToOne(mappedBy = "bienImmobilier")
    private Publication publication;

    @ManyToOne
    private OurUsers user;
}
