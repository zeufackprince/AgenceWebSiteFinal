package com.agenceWebSite.AgenceWebSite.Controller;

import com.agenceWebSite.AgenceWebSite.DTO.ResBelonging;
import com.agenceWebSite.AgenceWebSite.Models.Enums.BelongingType;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Cities;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Status;
import com.agenceWebSite.AgenceWebSite.Repository.UserRepository;
import com.agenceWebSite.AgenceWebSite.Service.BelongingsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BelongingsController {

    private final BelongingsService belongingsService;

    private final UserRepository userRepository;

    @PostMapping("/agent/create-belonging")
    @PreAuthorize("hasRole('ADMIN','AGENT')")
    public ResponseEntity<ResBelonging> createBelon(
            @Autowired SecurityContextHolder securityContextHolder,
            @RequestParam(required = false ,name = "images") List<MultipartFile> images ,
            @RequestParam("name") String name,
            @RequestParam("type")BelongingType type,
            @RequestParam("dimension") String dimension,
            @RequestParam("localisation")Cities localisation,
            @RequestParam("prix") Double prix
            // @RequestParam("status") Status status
    ) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName  = authentication.getName();
        Long userid = this.userRepository.findByEmail(userName).get().getId();

        ResBelonging reponse = this.belongingsService.createBelon(images, name, type, dimension, localisation, prix, userid);

        return  ResponseEntity.ok(reponse);
    }

    @GetMapping("/user/get-All-Belongings")
    // @PreAuthorize("hasRole('ADMIN','AGENT')")
    public ResponseEntity<List<ResBelonging>> getAllBelongings() throws SQLException {

        List<ResBelonging> response = this.belongingsService.getAllBelongings();

        return ResponseEntity.ok(response);
    }



    @PutMapping("/agent/update/{belongingId}")
    @PreAuthorize("hasRole('ADMIN','AGENT')")
    public ResponseEntity<ResBelonging> updateBelon(
            @PathVariable Long belongingId,
            @RequestParam(required = false ) List<MultipartFile> images ,
            @RequestParam(required = false ) String name,
            @RequestParam(required = false )BelongingType type,
            @RequestParam(required = false ) String dimension,
            @RequestParam(required = false )Cities localisation,
            @RequestParam(required = false ) Double prix
            // @RequestParam("status") Status status
    ) throws IOException, SQLException {

        ResBelonging response = this.belongingsService.updateBelon(belongingId, images, name, type, dimension, localisation, prix);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/belonging-id/{id}")
    public ResponseEntity<ResBelonging> getId(@PathVariable Long id) {

        ResBelonging reponse = this.belongingsService.getId(id);

        return ResponseEntity.ok(reponse);
    }

    @GetMapping(path = "/user/belongings/{type}")
    public List<ResBelonging> getBelongingByType(@PathVariable BelongingType type)
    {
        return this.belongingsService.getBelongingByType(type);
    }

    // @GetMapping(path = "/user/belonging/{status}")
    // public List<ResBelonging> getBelongingByStatus(@PathVariable Status status){
    //     return this.belongingsService.getBelongingByStatus(status);
    // }

}
