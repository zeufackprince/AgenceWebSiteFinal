package com.agenceWebSite.AgenceWedSite.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/price")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Welcome home page prince");
    }

    @GetMapping("/user")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("Welcome user");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("Welcome admin!!");
    }
}
