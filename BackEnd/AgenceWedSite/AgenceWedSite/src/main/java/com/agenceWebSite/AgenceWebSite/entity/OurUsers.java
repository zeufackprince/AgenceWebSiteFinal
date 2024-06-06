package com.agenceWebSite.AgenceWebSite.entity;


import com.agenceWebSite.AgenceWebSite.entity.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
    private Integer telephone;

    @Column(name = "password")
    private String password;

//    @Lob
//    @Basic(fetch = FetchType.LAZY) // Improve performance for large images
//    private byte[] imageData;

    /**
     * The Images.
     */
    @Column(name = "images")
    public String images;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Belongings> belongings;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
