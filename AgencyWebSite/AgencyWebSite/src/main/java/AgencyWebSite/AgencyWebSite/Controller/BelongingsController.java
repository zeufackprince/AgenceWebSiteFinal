package AgencyWebSite.AgencyWebSite.Controller;

import AgencyWebSite.AgencyWebSite.DTO.ResBelonging;
import AgencyWebSite.AgencyWebSite.Exceptions.FileExistsException;
import AgencyWebSite.AgencyWebSite.Models.Belongings;
import AgencyWebSite.AgencyWebSite.Models.Enums.BelongingType;
import AgencyWebSite.AgencyWebSite.Models.Enums.Cities;
import AgencyWebSite.AgencyWebSite.Models.Enums.Roles;
import AgencyWebSite.AgencyWebSite.Models.OurUsers;
import AgencyWebSite.AgencyWebSite.Repository.BelongingRepository;
import AgencyWebSite.AgencyWebSite.Repository.UserRepository;
import AgencyWebSite.AgencyWebSite.Service.BelongingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BelongingsController {

    private final BelongingsService belongingsService;

    @PostMapping("/create-belonging")
//    @PreAuthorize("hasRole('ADMIN','AGENT')")
    public ResponseEntity<ResBelonging> createBelon(
            @RequestParam(required = false ,name = "images") List<MultipartFile> images ,
            @RequestParam("name") String name,
            @RequestParam("type")BelongingType type,
            @RequestParam("dimension") String dimension,
            @RequestParam("localisation")Cities localisation,
            @RequestParam("prix") Double prix,
            @RequestParam("userId") Long user
    ) throws IOException {

        ResBelonging reponse = this.belongingsService.createBelon(images, name, type, dimension, localisation, prix, user);

        return  ResponseEntity.ok(reponse);
    }

    @GetMapping("/get-All-Belongings")
//    @PreAuthorize("hasRole('ADMIN','AGENT')")
    public ResponseEntity<List<ResBelonging>> getAllBelongings() throws SQLException {

        List<ResBelonging> response = this.belongingsService.getAllBelongings();

        return ResponseEntity.ok(response);
    }



    @PutMapping("/update/{belongingId}")
//    @PreAuthorize("hasRole('ADMIN','AGENT')")
    public ResponseEntity<ResBelonging> updateBelon(
            @PathVariable Long belongingId,
            @RequestParam(required = false ) List<MultipartFile> images ,
            @RequestParam(required = false ) String name,
            @RequestParam(required = false )BelongingType type,
            @RequestParam(required = false ) String dimension,
            @RequestParam(required = false )Cities localisation,
            @RequestParam(required = false ) Double prix
    ) throws IOException, SQLException {

        ResBelonging response = this.belongingsService.updateBelon(belongingId, images, name, type, dimension, localisation, prix);

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/belonging-type")
//    public List<Belongings> getBelongingType() {
//        return this.belongingRepository.findDistinctBelongingType();
//    }

    @GetMapping("/belonging-id/{id}")
    public ResponseEntity<ResBelonging> getId(@PathVariable Long id) {

        ResBelonging reponse = this.belongingsService.getId(id);

        return ResponseEntity.ok(reponse);
    }

}
