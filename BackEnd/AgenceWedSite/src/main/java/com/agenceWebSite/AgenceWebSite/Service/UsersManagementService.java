package com.agenceWebSite.AgenceWebSite.Service;

import com.agenceWebSite.AgenceWebSite.Controller.FileController;
import com.agenceWebSite.AgenceWebSite.DTO.ReqRes;
import com.agenceWebSite.AgenceWebSite.Exceptions.FileExistsException;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Role;
import com.agenceWebSite.AgenceWebSite.Models.OurUsers;
import com.agenceWebSite.AgenceWebSite.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The type Users management service.
 */
@Service
@AllArgsConstructor
public class UsersManagementService {

    private final FileController fileService;

    private final String path ="/images";

    private final String baseUrl = "http://localhost:1010";
    @Autowired
    private UserRepository usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register req res.
     *
     * @param registrationRequest the registration request
     * @return the req res
     */
    public ReqRes register(ReqRes registrationRequest, MultipartFile file) throws IOException {
        ReqRes resp = new ReqRes();

        //Verifier si un utilisateur avec l'email exist deja en BD
        Optional<OurUsers> users = this.usersRepo.findByEmail(registrationRequest.getEmail());

        if (users.isPresent()){
            throw new RuntimeException("User  with Email :" + users.get().getEmail() + "Already exist !!");
        }


        //Verifie si le role est null ou pas
        //si elle est null on lui donne le role par defaut qui est USER
        if (registrationRequest.getRole() == null){
            registrationRequest.setRole(Role.USER);
        }

        //on verifie si on n'a deja une photo ayant le meme nom en BD
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistsException("File already exists! Please enter another file name!");
        }
        String uploadedFileName = fileService.uploadFileHandler(file);

        registrationRequest.setPosterUrl(uploadedFileName);
        try {

            OurUsers ourUser = new OurUsers();
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setTelephone(registrationRequest.getTelephone());
            ourUser.setRole(registrationRequest.getRole());
            ourUser.setName(registrationRequest.getName());
            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUser.setImages(registrationRequest.getPosterUrl());

            OurUsers ourUsersResult = usersRepo.save(ourUser);

            String posterUrl = baseUrl + "/file/" + uploadedFileName;

            if (ourUsersResult.getId()>0) {
                //a modifier pour renvoyer juste les im=nformation important mais pas cririque
                resp.setId(ourUsersResult.getId());
                resp.setName(ourUsersResult.getName());
                resp.setEmail(ourUsersResult.getEmail());
                resp.setRole(ourUsersResult.getRole());
                resp.setPassword(ourUsersResult.getPassword());
                resp.setPosterUrl(posterUrl);
                resp.setPoster(ourUsersResult.getImages());
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    /**
     * Login req res.
     *
     * @param loginRequest the login request
     * @return the req res
     */
    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    /**
     * Refresh token req res.
     *
     * @param refreshTokenReqiest the refresh token reqiest
     * @return the req res
     */
    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    /**
     * Gets users by role.
     *
     * @param role the role
     * @return the users by role
     */
    public ResponseEntity<List<ReqRes>> getUsersByRole(Role role) throws Exception {
        List<OurUsers> usersByRole;
        List<ReqRes> reponseList = new ArrayList<>();

        try {

            switch (role) {
                case USER -> {
                    usersByRole = usersRepo.findByRole(Role.USER);
                    for (OurUsers users : usersByRole) {
                        String posterUrl = baseUrl + "/file/" + users.getImages();
                        ReqRes resp = new ReqRes();
                        resp.setId(users.getId());
                        resp.setName(users.getName());
                        resp.setEmail(users.getEmail());
                        resp.setRole(users.getRole());
                        resp.setPassword(users.getPassword());
                        resp.setPosterUrl(posterUrl);
                        resp.setPoster(users.getImages());
                        resp.setMessage("User List of users with role User Successfully");
                        resp.setStatusCode(200);
                        reponseList.add(resp);
                    }
                }
                case AGENT -> {
                    usersByRole = usersRepo.findByRole(Role.AGENT);
                    for (OurUsers users : usersByRole) {
                        String posterUrl = baseUrl + "/file/" + users.getImages();
                        ReqRes resp = new ReqRes();
                        resp.setId(users.getId());
                        resp.setName(users.getName());
                        resp.setEmail(users.getEmail());
                        resp.setRole(users.getRole());
                        resp.setPassword(users.getPassword());
                        resp.setPosterUrl(posterUrl);
                        resp.setPoster(users.getImages());
                        resp.setMessage("User List of users with role AGent Successfully");
                        resp.setStatusCode(200);
                        reponseList.add(resp);
                    }
                }
                case ADMIN -> {
                    usersByRole = usersRepo.findByRole(Role.ADMIN);
                    for (OurUsers users : usersByRole) {
                        String posterUrl = baseUrl + "/file/" + users.getImages();
                        ReqRes resp = new ReqRes();
                        resp.setId(users.getId());
                        resp.setName(users.getName());
                        resp.setEmail(users.getEmail());
                        resp.setRole(users.getRole());
                        resp.setPassword(users.getPassword());
                        resp.setPosterUrl(posterUrl);
                        resp.setPoster(users.getImages());
                        resp.setMessage("User List of users with role ADMIN Successfully");
                        resp.setStatusCode(200);
                        reponseList.add(resp);
                    }
                }
                default -> reponseList = Collections.emptyList();
            }
        }catch (Exception e) {

            throw new Exception(e);
        }
        return ResponseEntity.ok(reponseList);
    }


    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List<ReqRes> getAllUsers() {
        ReqRes resp = new ReqRes();

        List<OurUsers> usersList = this.usersRepo.findAll();

        List<ReqRes> reponseList = new ArrayList<>();

        try {
            List<OurUsers> result = usersRepo.findAll();
//           byte[] imagedb =  filesController.downloadImageFromFileSystem(result.get(0).getProfilePicture().getName()).getBody();
            if (!result.isEmpty()) {

                for (OurUsers users : usersList) {
                    String posterUrl = baseUrl + "/file/" + users.getImages();
                    resp.setId(users.getId());
                    resp.setName(users.getName());
                    resp.setEmail(users.getEmail());
                    resp.setRole(users.getRole());
                    resp.setPassword(users.getPassword());
                    resp.setPosterUrl(posterUrl);
                    resp.setPoster(users.getImages());
                    resp.setMessage("User Listed Successfully");
                    resp.setStatusCode(200);
                    reponseList.add(resp);
                }
            } else {
                resp.setStatusCode(404);
                resp.setMessage("No users found");
                reponseList.add(resp);
            }
            return reponseList;
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
            reponseList.add(resp);
            return reponseList;
        }
    }


    /**
     * Gets users by id.
     *
     * @param userId the id
     * @return the users by id
     */
    public ReqRes getUsersById(Long userId) {
        ReqRes resp = new ReqRes();
        try {

            OurUsers users = usersRepo.findById(userId).get();

            String posterUrl = baseUrl + "/file/" + users.getImages();

            resp.setId(users.getId());
            resp.setName(users.getName());
            resp.setEmail(users.getEmail());
            resp.setRole(users.getRole());
            resp.setPassword(users.getPassword());
            resp.setPosterUrl(posterUrl);
            resp.setPoster(users.getImages());
            resp.setMessage("User Listed Successfully");
            resp.setStatusCode(200);
        }catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }

        return resp;
    }


    /**
     * Delete user req res.
     *
     * @param userId the user id
     * @return the req res
     */
    public ReqRes deleteUser(Long userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes updateUser(Long userId, ReqRes reqres, MultipartFile file) throws  IOException {

        ReqRes resp = new ReqRes();
        OurUsers users = usersRepo.findById(userId).orElseThrow(() -> new IllegalStateException("User not found"));

        try {

            String fileName = users.getImages();

            if (!file.isEmpty()){
                Files.deleteIfExists(Paths.get(path + File.separator + fileName));
                fileName = fileService.uploadFileHandler(file);
            }

            reqres.setPoster(fileName);

            OurUsers ourUsers = new OurUsers(
                    users.getId(),
                    reqres.getName(),
                    reqres.getEmail(),
                    reqres.getTelephone(),
                    reqres.getPassword(),
                    reqres.getImages(),
                    reqres.getRole()
            );

            OurUsers saved = usersRepo.save(ourUsers);

            String posterUrl = baseUrl + "/file/" + fileName;

            resp.setId(saved.getId());
            resp.setName(saved.getName());
            resp.setEmail(saved.getEmail());
            resp.setRole(saved.getRole());
            resp.setPassword(saved.getPassword());
            resp.setPosterUrl(posterUrl);
            resp.setPoster(saved.getImages());
            resp.setMessage("User Updated Successfully");
            resp.setStatusCode(200);
        }catch (Exception e){

            resp.setStatusCode(500);
            resp.setError("Error occurred while updating user: " + e.getMessage());
        }

        return resp;
    }


    /**
     * Get my info req res.
     *
     * @param email the email
     * @return the req res
     */
    public ReqRes getMyInfo(String email){
        ReqRes resp = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);

            if (userOptional.isPresent()) {

                OurUsers saved = userOptional.get();

                String fileName = saved.getImages();

                String posterUrl = baseUrl + "/file/" + fileName;

                resp.setId(saved.getId());
                resp.setName(saved.getName());
                resp.setEmail(saved.getEmail());
                resp.setRole(saved.getRole());
                resp.setPassword(saved.getPassword());
                resp.setTelephone(saved.getTelephone());
                resp.setPosterUrl(posterUrl);
                resp.setPoster(saved.getImages());
                resp.setMessage("User Updated Successfully");
                resp.setStatusCode(200);
            } else {
                resp.setStatusCode(404);
                resp.setMessage("User not found for update");
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return resp;

    }
}
