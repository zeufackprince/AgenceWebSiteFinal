package com.agenceWebSite.AgenceWebSite.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import com.agenceWebSite.AgenceWebSite.Models.Enums.Status;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String titre;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    List<String> images;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_id")
    private Belongings bienImmobilier;

    @OneToMany
    private List<Notification> notification;

    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new Date();
    }

}
