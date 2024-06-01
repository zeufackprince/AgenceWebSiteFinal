package com.agenceWebSite.AgenceWedSite.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Demo controller.
 */
@RestController
public class DemoController {

    /**
     * Home response entity.
     *
     * @return the response entity
     */
    @GetMapping("/price")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Welcome home page prince");
    }

    /**
     * User response entity.
     *
     * @return the response entity
     */
    @GetMapping("/user")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("Welcome user");
    }

    /**
     * Admin response entity.
     *
     * @return the response entity
     */
    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("Welcome admin!!");
    }
}
