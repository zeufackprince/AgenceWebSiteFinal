package AgencyWebSite.AgencyWebSite.Controller;

import AgencyWebSite.AgencyWebSite.DTO.PubRes;
import AgencyWebSite.AgencyWebSite.Exceptions.BelongingExistException;
import AgencyWebSite.AgencyWebSite.Models.Belongings;
import AgencyWebSite.AgencyWebSite.Models.Enums.Roles;
import AgencyWebSite.AgencyWebSite.Models.OurUsers;
import AgencyWebSite.AgencyWebSite.Models.Publication;
import AgencyWebSite.AgencyWebSite.Repository.BelongingRepository;
import AgencyWebSite.AgencyWebSite.Repository.PublicationRepository;
import AgencyWebSite.AgencyWebSite.Repository.UserRepository;
import AgencyWebSite.AgencyWebSite.Service.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;

    @PostMapping("/new-publication")
    public ResponseEntity<PubRes> createPublication(@RequestBody PubRes request) {

        PubRes response = this.publicationService.createPublication(request);
        return ResponseEntity.ok(response);

    }

}
