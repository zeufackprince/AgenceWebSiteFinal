package com.agenceWebSite.AgenceWebSite.Controller;

import com.agenceWebSite.AgenceWebSite.DTO.ReqRes;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Role;
import com.agenceWebSite.AgenceWebSite.Service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The type User management controller.
 */
@RestController
public class UserManagementController {
    @Autowired
    private UsersManagementService usersManagementService;


    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> regeister(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String email,
                                            @RequestParam(required = false) String password,
                                            @RequestParam(required = false) String telephone,
                                            @RequestParam(required = false) Role role,
                                            @RequestParam(required = false) MultipartFile file) throws IOException {
        // Check if the file is empty
        
        ReqRes reg = new ReqRes(name, email, telephone, password, role);
        return new ResponseEntity<>(usersManagementService.register(reg, file), HttpStatus.CREATED);

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
    @GetMapping("/api/admin/get-all-users")
    public ResponseEntity<List<ReqRes>> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());

    }

    /**
     * Get u ser by id response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @GetMapping("/api/user/get-users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReqRes> getUSerByID(@PathVariable Long userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));

    }

    /**
     * Gets users by role.
     *
     * @param role the role
     * @return the users by role
     */
    @GetMapping("/api/admin/users/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReqRes>> getUsersByRole(@PathVariable Role role) throws Exception {
        return ResponseEntity.ok(usersManagementService.getUsersByRole(role).getBody());
    }

    /**
     * Update user response entity.
     *
     * @param userId the user id
//     * @param reqres the reqres
     * @return the response entity
     */
    //modified for it to take the actual user
    @PutMapping("/api/user/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Long userId,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String email,
                                             @RequestParam(required = false) String password,
                                             @RequestParam(required = false) String telephone,
                                             @RequestParam(required = false)  Role role,
                                             @RequestParam(required = false) MultipartFile file
    ) throws IOException {
        ReqRes reqres = new ReqRes(name, email, telephone, password, role);

        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres, file));
    }

    /**
     * Get my profile response entity.
     *
     * @return the response entity
     */
    @GetMapping("/api/adminuser/get-profile")
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
    @DeleteMapping("/api/admin/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Long userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }


}
