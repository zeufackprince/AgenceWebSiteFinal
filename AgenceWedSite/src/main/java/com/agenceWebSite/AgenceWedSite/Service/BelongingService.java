package com.agenceWebSite.AgenceWedSite.Service;


import com.agenceWebSite.AgenceWedSite.Repository.BelongingRepository;
import com.agenceWebSite.AgenceWedSite.Repository.UserRepository;
import com.agenceWebSite.AgenceWedSite.model.Belongings;
import com.agenceWebSite.AgenceWedSite.model.Enums.BelongingType;
import com.agenceWebSite.AgenceWedSite.model.Enums.Role;
import com.agenceWebSite.AgenceWedSite.model.Enums.Locations;
import com.agenceWebSite.AgenceWedSite.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;




@Service
public class BelongingService {


    private final BelongingRepository belongingRepository;

    private final UserRepository userRepository;

    public BelongingService(BelongingRepository belongingRepository, UserRepository userRepository) {
        this.belongingRepository = belongingRepository;
        this.userRepository = userRepository;
    }

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
            belongings.setImages(request.getImages());
            belongings.setUser(request.getUser());
            this.belongingRepository.save(belongings);

            return belongings;
        }else {
            throw new EntityNotFoundException("user with username :" + request.getUser().getUsername() + "does not exist or have the required authority");
        }

    }


    public List<Belongings> getAll()
    {
        return this.belongingRepository.findAll();
    }

    public Optional<Belongings> getAbelonging(Long id) {
        return this.belongingRepository.findById(id);
    }

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
