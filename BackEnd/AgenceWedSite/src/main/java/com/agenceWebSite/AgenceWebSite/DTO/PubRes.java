package com.agenceWebSite.AgenceWebSite.DTO;

import com.agenceWebSite.AgenceWebSite.Models.Enums.BelongingType;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Cities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubRes {

    private Long id;

    private int statusCode;

    private String error;

    private String message;

    private String titre;

    private String description;

    private String nom;

    private BelongingType type;

    private String dimension;

    private Cities localisation;

    private double prix;

    List<String> images;

    private List<String> posterUrl;

    private List<String> poster;

    private Long belonging_id;
}
