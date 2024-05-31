package com.agenceWebSite.AgenceWedSite.Controllers;



import com.agenceWebSite.AgenceWedSite.Repository.BelongingRepository;
import com.agenceWebSite.AgenceWedSite.Repository.PublicationRepository;
import com.agenceWebSite.AgenceWedSite.Repository.UserRepository;
import com.agenceWebSite.AgenceWedSite.model.Belongings;
import com.agenceWebSite.AgenceWedSite.model.Enums.Role;
import com.agenceWebSite.AgenceWedSite.model.Publication;
import com.agenceWebSite.AgenceWedSite.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicationController {

    private final PublicationRepository publicationRepository;

    private final BelongingRepository belongingsRepository;

    private final UserRepository userRepository;

    public PublicationController(PublicationRepository publicationRepository,
                                 BelongingRepository belongingsRepository,
                                 UserRepository userRepository) {
        this.publicationRepository = publicationRepository;
        this.belongingsRepository = belongingsRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/publications/create")
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
        User user = userRepository.findById(userId).get();
        return user.getRole() == Role.ADMIN || user.getRole() == Role.AGENT;
    }

    @GetMapping("/publications/getAll")
    public List<Publication> getPublication()
    {
        return this.publicationRepository.findAll();
    }
}

