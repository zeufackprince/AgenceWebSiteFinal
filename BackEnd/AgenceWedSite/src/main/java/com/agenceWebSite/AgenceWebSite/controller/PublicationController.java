package com.agenceWebSite.AgenceWebSite.controller;


import com.agenceWebSite.AgenceWebSite.entity.Belongings;
import com.agenceWebSite.AgenceWebSite.entity.Enums.Role;
import com.agenceWebSite.AgenceWebSite.entity.OurUsers;
import com.agenceWebSite.AgenceWebSite.entity.Publication;
import com.agenceWebSite.AgenceWebSite.repository.BelongingRepository;
import com.agenceWebSite.AgenceWebSite.repository.PublicationRepository;
import com.agenceWebSite.AgenceWebSite.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * The type Publication controller.
 */
@RestController
@RequestMapping("/api")
public class PublicationController {

    private final PublicationRepository publicationRepository;

    private final BelongingRepository belongingsRepository;

    private final UserRepository userRepository;

    /**
     * Instantiates a new Publication controller.
     *
     * @param publicationRepository the publication repository
     * @param belongingsRepository  the belongings repository
     * @param userRepository        the user repository
     */
    public PublicationController(PublicationRepository publicationRepository,
                                 BelongingRepository belongingsRepository,
                                 UserRepository userRepository) {
        this.publicationRepository = publicationRepository;
        this.belongingsRepository = belongingsRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create publication response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/agent/publications/create")
    public ResponseEntity<Publication> createPublication(@RequestBody Publication request) {
        // Validate user role (ADMIN or AGENT)

        if (!validateUserRole(request.getBienImmobilier().getUser().getId())) {
            return ResponseEntity.badRequest().build();
        }

        // Check if belongings exists
        Belongings belongings = belongingsRepository.findById(request.getBienImmobilier().getId()).orElse(null);
        if (belongings == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if belongings already has a publication
        if (belongings.getPublication() != null) {
            return ResponseEntity.badRequest().build(); // Error: Belongings already has a publication
        }

        // Create and save publication
        Publication publication = new Publication();
        publication.setTitre(request.getTitre());
        publication.setDescription(request.getDescription());
        publication.setImages(request.getBienImmobilier().getImages());
        publication.setBienImmobilier(belongings);
//        publication.setUser(userRepository.findById(request.getBienImmobilier().getUser().getId()).get());

        Publication savedPublication = publicationRepository.save(publication);

        belongings.setPublication(savedPublication);
        belongingsRepository.save(belongings); // Update belongings with publication reference
        return ResponseEntity.ok(savedPublication);
    }

    // Additional methods for retrieving, updating, and deleting publications

    private boolean validateUserRole(Long userId) {
        OurUsers user = userRepository.findById(userId).get();
        return user.getRole() == Role.ADMIN || user.getRole() == Role.AGENT;
    }

    /**
     * Gets publication.
     *
     * @return the publication
     */
    @GetMapping("/agent/publications/getAll")
    public List<Publication> getPublication()
    {
        return this.publicationRepository.findAll();
    }
}

