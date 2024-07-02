package com.agenceWebSite.AgenceWebSite.Controller;

import com.agenceWebSite.AgenceWebSite.DTO.PubRes;
import com.agenceWebSite.AgenceWebSite.Service.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-all-pub")
    public ResponseEntity<List<PubRes>> getAllPublication(){

        List<PubRes> reponse = this.publicationService.getAllPub();
        return ResponseEntity.ok(reponse);

    }

    @DeleteMapping("/delete-pub")
    public void deletePublication(@PathVariable Long id){
        this.publicationService.deletePub(id);
    }

}