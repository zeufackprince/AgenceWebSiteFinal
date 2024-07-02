package com.agenceWebSite.AgenceWebSite.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Role;
import com.agenceWebSite.AgenceWebSite.Models.OurUsers;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.List;

/**
 * The type Req res.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;

    private Long id;

    private String error;

    private String message;

    private String token;

    private String refreshToken;

    private String expirationTime;

    private String name;

    private Integer telephone;

    private String images;

    private String posterUrl;

    private String poster;

    private Role role;

    private String email;

    private String password;

    private OurUsers ourUsers;

    private List<OurUsers> ourUsersList;

    public ReqRes(Long id, String name, String email, Integer telephone, Role role, byte[] photoBytes) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.role = role;
    }

    public ReqRes(String name, String email, Integer telephone, String password, Role role) {
        this.name = name;
        this.email = email;
        this.telephone =telephone;
        this.password = password;
        this.role = role;
    }
}
