package com.agenceWebSite.AgenceWebSite.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.agenceWebSite.AgenceWebSite.entity.Enums.Role;
import com.agenceWebSite.AgenceWebSite.entity.OurUsers;
import lombok.Data;

import java.util.List;

/**
 * The type Req res.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private Integer telephone;
    private String images;
    private Role role;
    private String email;
    private String password;
    private OurUsers ourUsers;
    private List<OurUsers> ourUsersList;

}
