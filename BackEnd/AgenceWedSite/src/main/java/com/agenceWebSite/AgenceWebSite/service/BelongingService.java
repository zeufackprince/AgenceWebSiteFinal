package com.agenceWebSite.AgenceWebSite.service;

import com.agenceWebSite.AgenceWebSite.entity.Belongings;
import com.agenceWebSite.AgenceWebSite.entity.Enums.BelongingType;
import com.agenceWebSite.AgenceWebSite.entity.Enums.Cities;
import com.agenceWebSite.AgenceWebSite.entity.Enums.Role;
import com.agenceWebSite.AgenceWebSite.entity.OurUsers;
import com.agenceWebSite.AgenceWebSite.repository.BelongingRepository;
import com.agenceWebSite.AgenceWebSite.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


/**
 * The type Belonging service.
 */
@Service
public class BelongingService {


    private final BelongingRepository belongingRepository;

    private final UserRepository userRepository;

    /**
     * Instantiates a new Belonging service.
     *
     * @param belongingRepository the belonging repository
     * @param userRepository      the user repository
     */
    public BelongingService(BelongingRepository belongingRepository, UserRepository userRepository) {
        this.belongingRepository = belongingRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create bien immobilier belongings.
     *
     * @param request the request
     * @return the belongings
     */
    public Belongings createBienImmobilier(Belongings request) {

        // Validate user existence and role
        Optional<OurUsers> userOptional = userRepository.findById(request.getUser().getId());
        if (!userOptional.isPresent() ||
            !(userOptional.get().getRole() == Role.ADMIN || userOptional.get().getRole() == Role.AGENT)) {
            throw new IllegalStateException("User does not exist or has insufficient permissions");
        }

        // Validate request properties
        if (request.getNom() == null || request.getNom().isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be empty");
        }

        if (request.getType() == null || !isValidType(request.getType())) {
            throw new IllegalArgumentException("Invalid property type");
        }

        if (request.getLocalisation() == null || !isValidLocalisation(request.getLocalisation())) {
            throw new IllegalArgumentException("Invalid property location");
        }

        // Create and save Belongings object
        Belongings belongings = new Belongings();
        belongings.setNom(request.getNom());
        belongings.setType(request.getType());
        belongings.setDimension(request.getDimension());
        belongings.setLocalisation(request.getLocalisation());
        belongings.setPrix(request.getPrix());
        belongings.setImages(request.getImages());
        belongings.setUser(userOptional.get());

        belongingRepository.save(belongings);

        return belongings;
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


//    private String storeImage(String base64EncodedImage) {
//        // Assumptions:
//        // - Image data is received as base64 encoded string
//        // - Images are stored in a directory named "images" within the project
//
//        if (base64EncodedImage == null || base64EncodedImage.isEmpty()) {
//            return null; // No image provided
//        }
//
//        try {
//            // Extract image data from base64 string
//            String[] parts = base64EncodedImage.split(",");
//            String encodedData = parts[0]; // Assuming data starts after comma
//            byte[] decodedData = Base64.getDecoder().decode(encodedData);
//
//            // Generate a unique filename
//            String filename = UUID.randomUUID().toString() + ".jpg"; // Use JPG extension
//
//            // Create the image directory if it doesn't exist
//            File imageDir = new File("images");
//            if (!imageDir.exists()) {
//                imageDir.mkdirs(); // Create directories if needed
//            }
//
//            // Save the image file
//            Path imagePath = Paths.get(imageDir.getAbsolutePath(), filename);
//            Files.write(imagePath, decodedData);
//
//            // Return the image file path
//            return imagePath.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store image: " + e.getMessage());
//        }
//    }


    /**
     * Gets all.
     *
     * @return the all
     */
    public List<Belongings> getAll()
    {
        return this.belongingRepository.findAll();
    }

    /**
     * Gets abelonging.
     *
     * @param id the id
     * @return the abelonging
     */
    public Optional<Belongings> getAbelonging(Long id) {
        return this.belongingRepository.findById(id);
    }

    /**
     * Gets belonging by type.
     *
     * @param type the type
     * @return the belonging by type
     */
    public Optional<List<Belongings>> getBelongingByType(BelongingType type) {
        Optional<List<Belongings>> belongingsType;
        switch (type) {
            case ROOM:
                belongingsType = belongingRepository.findByType(BelongingType.ROOM);
                break;
            case APPARTMENT:
                belongingsType = belongingRepository.findByType(BelongingType.APPARTMENT);
                break;
            case STUDIOS:
                belongingsType = belongingRepository.findByType(BelongingType.STUDIOS);
                break;
            default:
                throw new IllegalStateException("type not found");
        }
        return belongingsType;
    }

    /**
     * Update belonging response entity.
     *
     * @param id      the id
     * @param request the request
     * @return the response entity
     */
    public ResponseEntity<?> updateBelonging(Long id, Belongings request) {
        Belongings existingBelongingds = belongingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id :" + id + "Not Found"));

        existingBelongingds.setNom(request.getNom());
        existingBelongingds.setType(request.getType());
        existingBelongingds.setDimension(request.getDimension());
        existingBelongingds.setLocalisation(request.getLocalisation());
        existingBelongingds.setPrix(request.getPrix());
        existingBelongingds.setImages(request.getImages());

        belongingRepository.save(existingBelongingds);
        return ResponseEntity.ok("UPDATED");
    }

}
