package com.agenceWebSite.AgenceWebSite.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Publication.
 */
@Entity
@Table
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;


    private String images;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_id")
    private Belongings bienImmobilier;

    @OneToMany
    private List<Notification> notification;
    
}
