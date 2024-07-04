package com.agenceWebSite.AgenceWebSite.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotifRes {

    private Long notif_id;

    private Long sender_id;

    private Long recipient_id;

    private String not_message;

    private String recipient_email;

    private String publication_name;

    private Date createdAt;

    private Integer statusCode;

    private String message;

}
