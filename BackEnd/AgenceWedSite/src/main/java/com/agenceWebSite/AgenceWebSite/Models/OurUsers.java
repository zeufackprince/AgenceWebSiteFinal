package com.agenceWebSite.AgenceWebSite.Models;


import com.agenceWebSite.AgenceWebSite.Models.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * The type Our users.
 */
@Entity
@Table(name = "ourusers")
@Data @AllArgsConstructor @NoArgsConstructor
public class OurUsers implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone_num")
    private String telephone;

    @Column(name = "password")
    private String password;

    /**
     * The Images.
     */
    @Column(name = "images")
    public String images;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Belongings> belongings;

    public OurUsers(Long id,
                    String name,
                    String email,
                    String telephone,
                    String password, String images, Role role) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.images = images;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new Date();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
