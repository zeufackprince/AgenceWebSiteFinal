package com.agenceWebSite.AgenceWebSite.Models;

import com.agenceWebSite.AgenceWebSite.Models.Enums.BelongingType;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Cities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Belongings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nom")
    private String nom;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private BelongingType type;

    @Column(name = "dimension")
    private String dimension;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "localisation")
    private Cities localisation;

    @Column(name = "prix")
    private double prix;

    @Column(name = "images")
    private List<String> images;

    @OneToOne(mappedBy = "bienImmobilier")
    private Publication publication;

    @ManyToOne
    private OurUsers user;

    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new Date();
    }
}