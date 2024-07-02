package AgencyWebSite.AgencyWebSite.Service;

import AgencyWebSite.AgencyWebSite.Controller.FileController;
import AgencyWebSite.AgencyWebSite.DTO.ReqRes;
import AgencyWebSite.AgencyWebSite.Exceptions.FileExistsException;
import AgencyWebSite.AgencyWebSite.Models.Enums.Roles;
import AgencyWebSite.AgencyWebSite.Models.OurUsers;
import AgencyWebSite.AgencyWebSite.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersManagementService {

    private final UserRepository userRepo;

    private final FileController fileService;

    private final String path ="/images";

    private final String baseUrl = "http://localhost:8080";
    public ReqRes register(ReqRes registrationRequest, MultipartFile file) throws IOException {

        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistsException("File already exists! Please enter another file name!");
        }
        String uploadedFileName = fileService.uploadFileHandler(file);

        ReqRes resp = new ReqRes();

        registrationRequest.setPosterUrl(uploadedFileName);

        try {
            OurUsers ourUser = new OurUsers();
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setTelephone(registrationRequest.getTelephone());
            ourUser.setRole(registrationRequest.getRole());
            ourUser.setName(registrationRequest.getName());
            ourUser.setPassword(registrationRequest.getPassword());
            ourUser.setImages(registrationRequest.getPosterUrl());
//            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            OurUsers ourUsersResult = userRepo.save(ourUser);

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

    public List<ReqRes> getAllUsers() {

        List<OurUsers> usersList = this.userRepo.findAll();

        List<ReqRes> reponseList = new ArrayList<>();

        for (OurUsers users : usersList) {
            String posterUrl = baseUrl + "/file/" + users.getImages();
            ReqRes resp = new ReqRes();
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

        return reponseList;
    }

    public ReqRes getUsersById(Long userId) {

        ReqRes resp = new ReqRes();
        try {

            OurUsers users = userRepo.findById(userId).get();

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

    public ResponseEntity<List<ReqRes>> getUsersByRole(Roles role) throws Exception {
        List<OurUsers> usersByRole;
        List<ReqRes> reponseList = new ArrayList<>();

        try {

            switch (role) {
                case USER -> {
                    usersByRole = userRepo.findByRole(Roles.USER);
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
                    usersByRole = userRepo.findByRole(Roles.AGENT);
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
                    usersByRole = userRepo.findByRole(Roles.ADMIN);
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

    public ReqRes updateUser(Long userId, ReqRes reqres, MultipartFile file) throws  IOException {

        ReqRes resp = new ReqRes();
        try {
            OurUsers users = userRepo.findById(userId).orElseThrow(() -> new IllegalStateException("User not found"));

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

            OurUsers saved = userRepo.save(ourUsers);

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
            resp.setError(e.getMessage());
        }

        return resp;
    }

    public ReqRes deleteUser(Long userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = userRepo.findById(userId);
            if (userOptional.isPresent()) {
                userRepo.deleteById(userId);
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

    public ReqRes getMyInfo(String email){
        ReqRes resp = new ReqRes();
        try {
            Optional<OurUsers> users = userRepo.findByEmail(email);
            if (users.isPresent()) {
                String posterUrl = baseUrl + "/file/" + users.get().getImages();
                resp.setId(users.get().getId());
                resp.setName(users.get().getName());
                resp.setEmail(users.get().getEmail());
                resp.setRole(users.get().getRole());
                resp.setPassword(users.get().getPassword());
                resp.setPosterUrl(posterUrl);
                resp.setPoster(users.get().getImages());
                resp.setStatusCode(200);
                resp.setMessage("successful");
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
