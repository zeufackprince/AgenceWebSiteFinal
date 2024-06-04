package com.agenceWebSite.AgenceWedSite.model;

import com.agenceWebSite.AgenceWedSite.model.Enums.BelongingType;
import com.agenceWebSite.AgenceWedSite.model.Enums.Locations;
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

    private Locations localisation;

    private double prix;
    
//    @Lob
//    private List<byte[]> images;
    private String images;

    @OneToOne(mappedBy = "bienImmobilier")
    private Publication publication;

    @ManyToOne
    private User user;
}
