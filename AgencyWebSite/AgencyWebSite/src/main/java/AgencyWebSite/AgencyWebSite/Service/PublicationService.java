package AgencyWebSite.AgencyWebSite.Service;

import AgencyWebSite.AgencyWebSite.DTO.PubRes;
import AgencyWebSite.AgencyWebSite.Exceptions.BelongingExistException;
import AgencyWebSite.AgencyWebSite.Exceptions.RoleNotFoundException;
import AgencyWebSite.AgencyWebSite.Models.Belongings;
import AgencyWebSite.AgencyWebSite.Models.Enums.Roles;
import AgencyWebSite.AgencyWebSite.Models.OurUsers;
import AgencyWebSite.AgencyWebSite.Models.Publication;
import AgencyWebSite.AgencyWebSite.Repository.BelongingRepository;
import AgencyWebSite.AgencyWebSite.Repository.PublicationRepository;
import AgencyWebSite.AgencyWebSite.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationService {

    private final String baseUrl = "http://localhost:8080";

    private final BelongingRepository belongingRepository;

    private final UserRepository userRepository;

    private final PublicationRepository publicationRepository;

    public PubRes createPublication(PubRes request) {

        Publication publication = new Publication();
        PubRes response = new PubRes();
        Belongings belongings = belongingRepository.findById(request.getBelonging_id()).get();
        try{
            //Verification si le bien immobilier exist en Base de Donnee
            if (belongingRepository.existsById(request.getBelonging_id())) {


                //Verification du Role de L'utilisateur
                if (!validateUserRole(belongings.getUser().getId())) {
                    throw  new RoleNotFoundException("No Valid Role");
                }

                //Verifie si le bien na pas deja ete Publier
                if (belongings.getPublication() != null) {
                    throw new RuntimeException("Already have a publication");
                }

                publication.setTitre(request.getTitre());
                publication.setDescription(request.getDescription());
                publication.setImages(belongings.getImages());
                publication.setBienImmobilier(belongings);
            }

            Publication savePub = publicationRepository.save(publication);

            belongings.setPublication(savePub);
            belongingRepository.save(belongings);

            List<String> posterUrl = new ArrayList<>();
            List<String> poster = savePub.getImages();

            for (String image : poster){

                String posterUrls = baseUrl + "/file/" + image;
                posterUrl.add(posterUrls);
            }

            response.setId(savePub.getId());
            response.setTitre(savePub.getTitre());
            response.setDescription(savePub.getDescription());
            response.setPoster(savePub.getImages());
            response.setPosterUrl(posterUrl);
            response.setDimension(belongings.getDimension());
            response.setLocalisation(belongings.getLocalisation());
            response.setNom(belongings.getNom());
            response.setPrix(belongings.getPrix());
            response.setType(belongings.getType());
            response.setBelonging_id(belongings.getId());
            response.setStatusCode(200);
            response.setMessage("Publication Succesfull ");

        }catch (Exception e){

            response.setMessage("Error creating Publication");
            response.setStatusCode(500);
            throw new BelongingExistException("Belonging not Found" + e);

        }

        return response;

    }

    //Verifie si l'utilisateur a le bon Role pour faire une publication
    private boolean validateUserRole(Long userId) {
        OurUsers user = userRepository.findById(userId).get();
        return user.getRole() == Roles.ADMIN || user.getRole() == Roles.AGENT;
    }

}
