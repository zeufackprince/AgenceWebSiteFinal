package AgencyWebSite.AgencyWebSite.DTO;

import AgencyWebSite.AgencyWebSite.Models.Enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {


    private Long id;

    private int statusCode;

    private String error;

    private String message;

    private String name;

    private String email;

    private Integer telephone;

    private String password;

    public String images;

    private String posterUrl;

    private String poster;

    private Roles role;

    public ReqRes(String name, String email, Integer telephone, String password, Roles role) {
        this.name = name;
        this.email = email;
        this.telephone =telephone;
        this.password = password;
        this.role = role;
    }
}
