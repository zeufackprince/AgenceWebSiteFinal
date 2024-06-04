package com.agenceWebSite.AgenceWedSite.Service;


import com.agenceWebSite.AgenceWedSite.Repository.BelongingRepository;
import com.agenceWebSite.AgenceWedSite.Repository.UserRepository;
import com.agenceWebSite.AgenceWedSite.model.Belongings;
import com.agenceWebSite.AgenceWedSite.model.Enums.BelongingType;
import com.agenceWebSite.AgenceWedSite.model.Enums.Role;
import com.agenceWebSite.AgenceWedSite.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

        Optional<User> userdb = this.userRepository.findByUsername(request.getUser().getUsername());

        if (userdb.isPresent() && request.getUser().getRole() == Role.ADMIN || request.getUser().getRole() == Role.AGENT) {

            Belongings belongings = new Belongings();
            belongings.setNom(request.getNom());
            switch (request.getType()) {
                case STUDIOS, ROOM, APPARTMENT:
                    belongings.setType(request.getType());
                    break;
                default:
                    throw new IllegalStateException("Type does not exist");
            }
            belongings.setDimension(request.getDimension());
            switch (request.getLocalisation()) {
                case DOUALA, KRIBI, BUEA, LIMBE, MAROUA, DSCHANG, FOUMBAN, YAOUNDE, BAFOUSSAM -> {
                    belongings.setLocalisation(request.getLocalisation());
                    break;
                }
                default -> throw new RuntimeException("vile pas dans la BD !!");
            }

            belongings.setPrix(request.getPrix());
//            belongings.setImages(request.getImages());
            String imageFilePath = storeImage(request.getImages()); // Implement storeImage method
            if (imageFilePath != null) {
                belongings.setImages(imageFilePath); // Store the image path
            } else {
                throw new RuntimeException("Failed to store image");
            }
            belongings.setUser(request.getUser());
            this.belongingRepository.save(belongings);

            return belongings;
        }else {
            throw new EntityNotFoundException("user with username :" + request.getUser().getUsername() + "does not exist or have the required authority");
        }

    }

    private String storeImage(String base64EncodedImage) {
        // Assumptions:
        // - Image data is received as base64 encoded string
        // - Images are stored in a directory named "images" within the project

        if (base64EncodedImage == null || base64EncodedImage.isEmpty()) {
            return null; // No image provided
        }

        try {
            // Extract image data from base64 string
            String[] parts = base64EncodedImage.split(",");
            String encodedData = parts[1]; // Assuming data starts after comma
            byte[] decodedData = Base64.getDecoder().decode(encodedData);

            // Generate a unique filename
            String filename = UUID.randomUUID().toString() + ".jpg"; // Use JPG extension

            // Create the image directory if it doesn't exist
            File imageDir = new File("images");
            if (!imageDir.exists()) {
                imageDir.mkdirs(); // Create directories if needed
            }

            // Save the image file
            Path imagePath = Paths.get(imageDir.getAbsolutePath(), filename);
            Files.write(imagePath, decodedData);

            // Return the image file path
            return imagePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image: " + e.getMessage());
        }
    }


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
