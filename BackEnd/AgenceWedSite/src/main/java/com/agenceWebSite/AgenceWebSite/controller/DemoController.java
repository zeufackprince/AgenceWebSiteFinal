package com.agenceWebSite.AgenceWebSite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Demo controller.
 */
@RestController
@RequestMapping("/api")
public class DemoController {

    /**
     * Home response entity.
     *
     * @return the response entity
     */
    @GetMapping("/users/home")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Welcome home page prince");
    }

    /**
     * User response entity.
     *
     * @return the response entity
     */
    @GetMapping("/users")
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

    /**
     * Agent response entity.
     *
     * @return the response entity
     */
    @GetMapping("/agent")
    public ResponseEntity<String> agent(){
        return ResponseEntity.ok("Welcome agent!!");
    }
}
