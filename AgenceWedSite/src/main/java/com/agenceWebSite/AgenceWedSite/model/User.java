package com.agenceWebSite.AgenceWedSite.model;

import com.agenceWebSite.AgenceWedSite.model.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type User.
 */
@Entity
@Table(name = "user")
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name") 
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password; 

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Belongings> belongings;


}
