package com.agenceWebSite.AgenceWebSite.controller;

import com.agenceWebSite.AgenceWebSite.dto.ReqRes;
import com.agenceWebSite.AgenceWebSite.entity.Enums.Role;
import com.agenceWebSite.AgenceWebSite.entity.OurUsers;
import com.agenceWebSite.AgenceWebSite.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type User management controller.
 */
@RestController
public class UserManagementController {
    @Autowired
    private UsersManagementService usersManagementService;

    /**
     * Regeister response entity.
     *
     * @param reg the reg
     * @return the response entity
     */
    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> regeister(@RequestBody ReqRes reg){
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    /**
     * Login response entity.
     *
     * @param req the req
     * @return the response entity
     */
    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    /**
     * Refresh token response entity.
     *
     * @param req the req
     * @return the response entity
     */
    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    /**
     * Get all users response entity.
     *
     * @return the response entity
     */
    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());

    }

    /**
     * Get u ser by id response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<ReqRes> getUSerByID(@PathVariable Long userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));

    }

    /**
     * Gets users by role.
     *
     * @param role the role
     * @return the users by role
     */
    @GetMapping("/admin/users/{role}")
    public ResponseEntity<List<OurUsers>> getUsersByRole(@PathVariable Role role) {
        return ResponseEntity.ok(usersManagementService.getUsersByRole(role).getBody());
    }

    /**
     * Update user response entity.
     *
     * @param userId the user id
     * @param reqres the reqres
     * @return the response entity
     */
    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Long userId, @RequestBody OurUsers reqres){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    /**
     * Get my profile response entity.
     *
     * @return the response entity
     */
    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Delete u ser response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Long userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }


}
