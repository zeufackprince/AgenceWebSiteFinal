package com.agenceWebSite.AgenceWedSite.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Login request.
 */
@Setter
@Getter
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;

    // Getters and Setters omitted for brevity
}

