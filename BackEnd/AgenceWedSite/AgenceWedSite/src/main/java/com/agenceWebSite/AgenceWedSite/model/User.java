package com.agenceWebSite.AgenceWedSite.model;

import com.agenceWebSite.AgenceWedSite.model.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Collection;
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

    @Column(name = "telephone_num")
    private Integer telephone;

    @Column(name = "password")
    private String password;

    @Lob
    @Basic(fetch = FetchType.LAZY) // Improve performance for large images
    private byte[] imageData;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Belongings> belongings;

    /**
     * Gets telephone.
     *
     * @return the telephone
     */
    public Integer getTelephone() {
        return telephone;
    }

    /**
     * Sets telephone.
     *
     * @param telephone the telephone
     */
    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }


}
