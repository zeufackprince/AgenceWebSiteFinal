package com.agenceWebSite.AgenceWebSite.DTO;

import com.agenceWebSite.AgenceWebSite.Models.Enums.BelongingType;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Cities;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResBelonging {

    private Long id;

    private String nom;

    private BelongingType type;

    private String dimension;

    private Cities localisation;

    private double prix;

    private List<String> posterUrl;

    private List<String> poster;

    private String message;

    private Status status;

    private Long userId;

    public ResBelonging(Long id,
                        String nom,
                        BelongingType type,
                        String dimension,
                        Cities localisation,
                        double prix,
                        Long user,
                        Status status) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.dimension = dimension;
        this.localisation = localisation;
        this.prix = prix;
        this.userId = user;
    }

    public ResBelonging(Long id, String nom, BelongingType type, String dimension1, Cities localisation, double prix, byte[] photoBytes) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.dimension = dimension1;
        this.localisation = localisation;
        this.prix = prix;
    }
}
