package AgencyWebSite.AgencyWebSite.Models;

import AgencyWebSite.AgencyWebSite.Models.Enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ourusers")
@AllArgsConstructor @NoArgsConstructor
@Data
public class OurUsers {

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

    @Column(name = "images")
    public String images;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private Roles role;

}
