package com.agenceWebSite.AgenceWebSite.Service;

import com.agenceWebSite.AgenceWebSite.DTO.PubRes;
import com.agenceWebSite.AgenceWebSite.Exceptions.BelongingExistException;
import com.agenceWebSite.AgenceWebSite.Exceptions.RoleNotFoundException;
import com.agenceWebSite.AgenceWebSite.Models.Belongings;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Role;
import com.agenceWebSite.AgenceWebSite.Models.Enums.Status;
import com.agenceWebSite.AgenceWebSite.Models.OurUsers;
import com.agenceWebSite.AgenceWebSite.Models.Publication;
import com.agenceWebSite.AgenceWebSite.Repository.BelongingRepository;
import com.agenceWebSite.AgenceWebSite.Repository.PublicationRepository;
import com.agenceWebSite.AgenceWebSite.Repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationService {

    private final String baseUrl = "http://localhost:1010";

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
                publication.setStatus(request.getStatus());
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
            response.setStatus(savePub.getStatus());
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
        return user.getRole() == Role.ADMIN || user.getRole() == Role.AGENT;
    }

    public List<PubRes> getAllPub() {

        List<Publication> pub = this.publicationRepository.findAll();
        List<PubRes> resp = new ArrayList<>();

        try {
            for (Publication publication : pub) {
                PubRes response = new PubRes();
                Belongings belongings = this.belongingRepository.findById(publication.getBienImmobilier().getId()).get();
                List<String> posterUrl = new ArrayList<>();
                List<String> poster = publication.getImages();

                for (String image : poster){

                    String posterUrls = baseUrl + "/file/" + image;
                    posterUrl.add(posterUrls);
                }

                response.setId(publication.getId());
                response.setTitre(publication.getTitre());
                response.setDescription(publication.getDescription());
                response.setPoster(publication.getImages());
                response.setPosterUrl(posterUrl);
                response.setDimension(belongings.getDimension());
                response.setLocalisation(belongings.getLocalisation());
                response.setNom(belongings.getNom());
                response.setStatus(publication.getStatus());
                response.setPrix(belongings.getPrix());
                response.setType(belongings.getType());
                response.setBelonging_id(belongings.getId());

                resp.add(response);
            }
        }catch (Exception e){

            throw new RuntimeException(e);
        }

        return resp;
    }

    public void deletePub(Long id) {

        this.publicationRepository.deleteById(id);
    }

    public PubRes getPubById(Long pubid) {
        
        Publication publication = this.publicationRepository.findById(pubid).get();
        PubRes response = new PubRes();
        Belongings belongings = belongingRepository.findById(publication.getBienImmobilier().getId()).get();

        List<String> posterUrl = new ArrayList<>();
        List<String> poster = publication.getImages();

        for (String image : poster){

            String posterUrls = baseUrl + "/file/" + image;
            posterUrl.add(posterUrls);
        }

        response.setId(publication.getId());
        response.setTitre(publication.getTitre());
        response.setDescription(publication.getDescription());
        response.setPoster(publication.getImages());
        response.setPosterUrl(posterUrl);
        response.setDimension(belongings.getDimension());
        response.setLocalisation(belongings.getLocalisation());
        response.setNom(belongings.getNom());
        response.setPrix(belongings.getPrix());
        response.setStatus(publication.getStatus());
        response.setType(belongings.getType());
        response.setBelonging_id(belongings.getId());


        return response;
    }

    public List<PubRes> getPublicationByStatus(Status status){

        List<Publication> pubList = this.publicationRepository.findAll();
        List<PubRes> res = new ArrayList<>();

        try {
            for(Publication publication : pubList){

                if (publication.getStatus() == status.LOUER) {

                    Belongings belongings = belongingRepository.findById(publication.getBienImmobilier().getId()).get();

                    List<String> posterUrl = new ArrayList<>();
                    List<String> poster = publication.getImages();

                    for (String image : poster){

                        String posterUrls = baseUrl + "/file/" + image;
                        posterUrl.add(posterUrls);
                    }

                    PubRes pub = new PubRes();
                    pub.setId(publication.getId());
                    pub.setTitre(publication.getTitre());
                    pub.setDescription(publication.getDescription());
                    pub.setPoster(publication.getImages());
                    pub.setPosterUrl(posterUrl);
                    pub.setDimension(belongings.getDimension());
                    pub.setLocalisation(belongings.getLocalisation());
                    pub.setNom(belongings.getNom());
                    pub.setPrix(belongings.getPrix());
                    pub.setStatus(publication.getStatus());
                    pub.setType(belongings.getType());
                    pub.setBelonging_id(belongings.getId());

                    res.add(pub);
                    
                }else if(publication.getStatus() == status.ACHETER ){

                    Belongings belongings = belongingRepository.findById(publication.getBienImmobilier().getId()).get();

                    List<String> posterUrl = new ArrayList<>();
                    List<String> poster = publication.getImages();

                    for (String image : poster){

                        String posterUrls = baseUrl + "/file/" + image;
                        posterUrl.add(posterUrls);
                    }

                    PubRes pub = new PubRes();
                    pub.setId(publication.getId());
                    pub.setTitre(publication.getTitre());
                    pub.setDescription(publication.getDescription());
                    pub.setPoster(publication.getImages());
                    pub.setPosterUrl(posterUrl);
                    pub.setDimension(belongings.getDimension());
                    pub.setLocalisation(belongings.getLocalisation());
                    pub.setNom(belongings.getNom());
                    pub.setPrix(belongings.getPrix());
                    pub.setStatus(publication.getStatus());
                    pub.setType(belongings.getType());
                    pub.setBelonging_id(belongings.getId());

                    res.add(pub);
                }

            }
        } catch (Exception e) {
            PubRes pub = new PubRes();
            pub.setMessage("Error fetching Publications of State " + status + e);
            res.add(pub);
            
        }

        return res;

    }

   
}
