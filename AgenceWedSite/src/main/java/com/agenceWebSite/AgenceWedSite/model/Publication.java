package com.agenceWebSite.AgenceWedSite.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
//    @Lob
//    private List<byte[]> images;

    private String images;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_id")
    private Belongings bienImmobilier;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
    
}
