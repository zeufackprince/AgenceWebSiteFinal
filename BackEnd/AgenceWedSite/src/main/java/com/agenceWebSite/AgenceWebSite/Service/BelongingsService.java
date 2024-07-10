package com.agenceWebSite.AgenceWebSite.Service;

import com.agenceWebSite.AgenceWebSite.Controller.FileController;
import com.agenceWebSite.AgenceWebSite.DTO.ResBelonging;
import com.agenceWebSite.AgenceWebSite.Exceptions.FileExistsException;
import com.agenceWebSite.AgenceWebSite.Models.Belongings;
import com.agenceWebSite.AgenceWebSite.Models.Enums.BelongingType;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Cities;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Role;
import com.agenceWebSite.AgenceWebSite.Models.OurUsers;
import com.agenceWebSite.AgenceWebSite.Repository.BelongingRepository;
import com.agenceWebSite.AgenceWebSite.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BelongingsService {

    private final BelongingRepository belongingRepository;

    private final UserRepository userRepository;

    private final FileController fileController;

    private final String path ="/images";

    private final String baseUrl = "http://localhost:1010";

    public ResBelonging createBelon(List<MultipartFile> images, 
                                    String name, 
                                    BelongingType type, 
                                    String dimension, 
                                    Cities localisation, 
                                    Double prix,
                                    // Status status, 
                                    Long user
        ) throws IOException {

        Belongings belongings = new Belongings();
                    belongings.setNom(name);
                    belongings.setType(type);
                    belongings.setDimension(dimension);
                    belongings.setLocalisation(localisation);
                    belongings.setPrix(prix);
                    // belongings.setStatus(status);


        Optional<OurUsers> users = this.userRepository.findById(user);
        if (users.isEmpty() || !(users.get().getRole() == Role.ADMIN || users.get().getRole() == Role.AGENT))
        {
            throw new IllegalStateException("User does not exist or has insufficient permissions");
        }
        if (type == null || !isValidType(type)) {
            throw new IllegalArgumentException("Invalid property type");
        }

        if (localisation == null || !isValidLocalisation(localisation)) {
            throw new IllegalArgumentException("Invalid property location");
        }

        belongings.setUser(users.get());

        List<String> poster = new ArrayList<>();
        List<String> posterUrl = new ArrayList<>();

        for (MultipartFile file : images){
            if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
                throw new FileExistsException("File already exists! Please enter another file name!");
            }
            String uploadedFileName = fileController.uploadFileHandler(file);
            poster.add(uploadedFileName);
            String posterUrls = baseUrl + "/file/" + uploadedFileName;
            posterUrl.add(posterUrls);
        }

        belongings.setImages(poster);

        Belongings belongingsdb = belongingRepository.save(belongings);

        ResBelonging reponse = new ResBelonging();
        reponse.setId(belongingsdb.getId());
        reponse.setNom(belongingsdb.getNom());
        reponse.setType(belongingsdb.getType());
        reponse.setDimension(belongingsdb.getDimension());
        reponse.setLocalisation(belongingsdb.getLocalisation());
        reponse.setPrix(belongingsdb.getPrix());
        // reponse.setStatus(belongingsdb.getStatus());
        reponse.setPoster(poster);
        reponse.setPosterUrl(posterUrl);
        reponse.setUserId(belongingsdb.getUser().getId());


        return  reponse;
    }


    public List<ResBelonging> getAllBelongings() throws SQLException {

        List<Belongings> belongingsList = this.belongingRepository.findAll();
        List<ResBelonging> resBelongingList = new ArrayList<>();
        for (Belongings belongingsdb : belongingsList) {
            List<String> posterUrl = new ArrayList<>();
            ResBelonging reponse = new ResBelonging();

            List<String> img = belongingsdb.getImages();
            for (String image : img) {
                String posterUrls = baseUrl + "/file/" + image;
                posterUrl.add(posterUrls);
            }
            reponse.setId(belongingsdb.getId());
            reponse.setNom(belongingsdb.getNom());
            reponse.setType(belongingsdb.getType());
            reponse.setDimension(belongingsdb.getDimension());
            reponse.setLocalisation(belongingsdb.getLocalisation());
            reponse.setPrix(belongingsdb.getPrix());
            // reponse.setStatus(belongingsdb.getStatus());
            reponse.setPoster(belongingsdb.getImages());
            reponse.setPosterUrl(posterUrl);
            reponse.setUserId(belongingsdb.getUser().getId());

            resBelongingList.add(reponse);
        }

        if (resBelongingList.isEmpty()) {
            throw new RuntimeException("Empty List No Belonging in DB");
        } else {
            return resBelongingList;
        }
    }

    public ResBelonging updateBelon(Long belongingId, 
                                    List<MultipartFile> images, 
                                    String name, 
                                    BelongingType type, 
                                    String dimension, 
                                    Cities localisation, 
                                    Double prix 
                                    // Status status
        ) throws IOException, SQLException {

        Optional<Belongings> belongingsdb = this.belongingRepository.findById(belongingId);
        List<String> poster = new ArrayList<>();
        List<String> posterUrl = new ArrayList<>();

        if (!images.isEmpty()) {

            List<String> fileName = belongingsdb.get().getImages();
            for (String ImgName : fileName) {
                Files.deleteIfExists(Paths.get(path + File.separator + ImgName));
            }

            for (MultipartFile file : images) {
                // if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
                //     throw new FileExistsException("File already exists! Please enter another file name!");
                // }
                String uploadedFileName = fileController.uploadFileHandler(file);
                poster.add(uploadedFileName);
                String posterUrls = baseUrl + "/file/" + uploadedFileName;
                posterUrl.add(posterUrls);
            }
        }


        if(belongingsdb.isPresent()){
            belongingsdb.get().setNom(name);
            belongingsdb.get().setType(type);
            belongingsdb.get().setDimension(dimension);
            belongingsdb.get().setLocalisation(localisation);
            belongingsdb.get().setPrix(prix);
            belongingsdb.get().setImages(poster);
            // belongingsdb.get().setStatus(status);
        }else {
            throw new RuntimeException("No such belonging with the id provided");
        }
        Belongings belongings = belongingsdb.get();
        ResBelonging reponse = new ResBelonging();

        reponse.setId(belongings.getId());
        reponse.setNom(belongings.getNom());
        reponse.setType(belongings.getType());
        reponse.setDimension(belongings.getDimension());
        reponse.setLocalisation(belongings.getLocalisation());
        reponse.setPrix(belongings.getPrix());
        // reponse.setStatus(belongings.getStatus());
        reponse.setPoster(belongings.getImages());
        reponse.setPosterUrl(posterUrl);
        reponse.setUserId(belongings.getUser().getId());

        return reponse;
    }

    public ResBelonging getId(@PathVariable Long id) {

        List<String> img = new ArrayList<>();

        Optional<Belongings> belongingsdb = this.belongingRepository.findById(id);
        ResBelonging reponse = new ResBelonging();

        Belongings belongings =belongingsdb.get();
        List<String> posterUrl =  belongings.getImages();

        reponse.setId(belongings.getId());
        reponse.setNom(belongings.getNom());
        reponse.setType(belongings.getType());
        reponse.setDimension(belongings.getDimension());
        reponse.setLocalisation(belongings.getLocalisation());
        reponse.setPrix(belongings.getPrix());
        // reponse.setStatus(belongings.getStatus());
        reponse.setPoster(belongings.getImages());

        for (String image : posterUrl){

            String posterUrls = baseUrl + "/file/" + image;
            img.add(posterUrls);
        }

        reponse.setPosterUrl(img);
        reponse.setUserId(belongings.getUser().getId());

        return reponse;
    }


    private boolean isValidType(BelongingType type) {
        return type == BelongingType.STUDIOS || type == BelongingType.ROOM || type == BelongingType.APPARTMENT;
    }

    private boolean isValidLocalisation(Cities localisation) {
        return localisation == Cities.DOUALA || localisation == Cities.KRIBI ||
                localisation == Cities.BUEA || localisation == Cities.LIMBE ||
                localisation == Cities.MAROUA || localisation == Cities.DSCHANG ||
                localisation == Cities.FOUMBAN || localisation == Cities.YAOUNDE ||
                localisation == Cities.BAFOUSSAM;
    }

    public List<ResBelonging> getBelongingByType(BelongingType type) {
        List<Belongings> belongings = this.belongingRepository.findAll();
        List<ResBelonging> responsed = new ArrayList<>();
    
        try {
            for (Belongings belongingsdb : belongings) {
                if (belongingsdb.getType() == type) {
                    ResBelonging response = new ResBelonging();
                    List<String> posterUrl = new ArrayList<>();
    
                    List<String> img = belongingsdb.getImages();
                    for (String image : img) {
                        String posterUrls = baseUrl + "/file/" + image;
                        posterUrl.add(posterUrls);
                    }
    
                    response.setId(belongingsdb.getId());
                    response.setNom(belongingsdb.getNom());
                    response.setType(belongingsdb.getType());
                    response.setDimension(belongingsdb.getDimension());
                    response.setLocalisation(belongingsdb.getLocalisation());
                    response.setPrix(belongingsdb.getPrix());
                    // response.setStatus(belongingsdb.getStatus());
                    response.setPoster(belongingsdb.getImages());
                    response.setPosterUrl(posterUrl);
                    response.setUserId(belongingsdb.getUser().getId());
    
                    responsed.add(response);
                }
            }
        } catch (Exception e) {
            ResBelonging response = new ResBelonging();
            response.setMessage("Error while fetching Belonging by Type");
            responsed.add(response);
        }
    
        if (responsed.isEmpty()) {
            ResBelonging response = new ResBelonging();
            response.setMessage("No belongings found for the specified type");
            responsed.add(response);
        }
    
        return responsed;
    }
    

    // public List<ResBelonging> getBelongingByStatus(Status status) {
    //     List<Belongings> belongings = this.belongingRepository.findAll();
    //     List<ResBelonging> responsed = new ArrayList<>();
    
    //     try {
    //         if (status == Status.ACHETER) {
    //             for (Belongings belongingsdb : belongings) {
    //                 if (belongingsdb.getStatus() == Status.ACHETER) {
    //                     ResBelonging response = new ResBelonging();
    //                     List<String> posterUrl = new ArrayList<>();
    //                     List<String> img = belongingsdb.getImages();
    //                     for (String image : img) {
    //                         String posterUrls = baseUrl + "/file/" + image;
    //                         posterUrl.add(posterUrls);
    //                     }
    //                     response.setId(belongingsdb.getId());
    //                     response.setNom(belongingsdb.getNom());
    //                     response.setType(belongingsdb.getType());
    //                     response.setDimension(belongingsdb.getDimension());
    //                     response.setLocalisation(belongingsdb.getLocalisation());
    //                     response.setPrix(belongingsdb.getPrix());
    //                     response.setStatus(belongingsdb.getStatus());
    //                     response.setPoster(belongingsdb.getImages());
    //                     response.setPosterUrl(posterUrl);
    //                     response.setUserId(belongingsdb.getUser().getId());
    
    //                     responsed.add(response);
    //                 }
    //             }
    //         } else if (status == Status.LOUER) {
    //             for (Belongings belongingsdb : belongings) {
    //                 if (belongingsdb.getStatus() == Status.LOUER) {
    //                     ResBelonging response = new ResBelonging();
    //                     List<String> posterUrl = new ArrayList<>();
    //                     List<String> img = belongingsdb.getImages();
    //                     for (String image : img) {
    //                         String posterUrls = baseUrl + "/file/" + image;
    //                         posterUrl.add(posterUrls);
    //                     }
    //                     response.setId(belongingsdb.getId());
    //                     response.setNom(belongingsdb.getNom());
    //                     response.setType(belongingsdb.getType());
    //                     response.setDimension(belongingsdb.getDimension());
    //                     response.setLocalisation(belongingsdb.getLocalisation());
    //                     response.setPrix(belongingsdb.getPrix());
    //                     // response.setStatus(belongingsdb.getStatus());
    //                     response.setPoster(belongingsdb.getImages());
    //                     response.setPosterUrl(posterUrl);
    //                     response.setUserId(belongingsdb.getUser().getId());
    
    //                     responsed.add(response);
    //                 }
    //             }
    //         } else {
    //             ResBelonging response = new ResBelonging();
    //             response.setMessage("Error while fetching Belonging by Status");
    //             responsed.add(response);
    //         }
    //     } catch (Exception e) {
    //         ResBelonging response = new ResBelonging();
    //         response.setMessage("Error while fetching Belonging by Status");
    //         responsed.add(response);
    //     }
    
    //     return responsed;
    // }
    
    
}
