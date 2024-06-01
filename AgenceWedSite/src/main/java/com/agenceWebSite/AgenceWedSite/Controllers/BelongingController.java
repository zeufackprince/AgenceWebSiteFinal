package com.agenceWebSite.AgenceWedSite.Controllers;


import com.agenceWebSite.AgenceWedSite.Repository.UserRepository;
import com.agenceWebSite.AgenceWedSite.Service.BelongingService;
import com.agenceWebSite.AgenceWedSite.model.Belongings;
import com.agenceWebSite.AgenceWedSite.model.Enums.BelongingType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * The type Belonging controller.
 */
@RestController
@RequestMapping(path = "/api")
public class BelongingController {

    private final BelongingService belongingService;

    private final UserRepository userRepository;

    /**
     * Instantiates a new Belonging controller.
     *
     * @param belongingService the belonging service
     * @param userRepository   the user repository
     */
    public BelongingController(BelongingService belongingService, UserRepository userRepository)
    {
        this.belongingService = belongingService;
        this.userRepository = userRepository;
    }

    /**
     * Create bien immobilier belongings.
     *
     * @param request the request
     * @return the belongings
     * @throws RelationNotFoundException the relation not found exception
     */
    @PostMapping(path = "/belongings/create")
    public Belongings createBienImmobilier(@RequestBody Belongings request) throws RelationNotFoundException {

        return belongingService.createBienImmobilier(request);
    }

    /**
     * Gets belongings.
     *
     * @return the belongings
     */
    @GetMapping(path = "/belongings/all")
    public List<Belongings> getBelongings()
    {
        return this.belongingService.getAll();
    }


    /**
     * Gets belonging by type.
     *
     * @param type the type
     * @return the belonging by type
     */
    @GetMapping(path = "/belongings/{type}")
    public Optional<List<Belongings>> getBelongingByType(@PathVariable BelongingType type)
    {
        return this.belongingService.getBelongingByType(type);
    }

    /**
     * Gets a belongings.
     *
     * @param id the id
     * @return the a belongings
     */
    @GetMapping(path = "/belongings/{id}")
    public Optional<Belongings> getABelongings(@PathVariable Long id)
    {
        return this.belongingService.getAbelonging(id);
    }


    /**
     * Update belonging response entity.
     *
     * @param id      the id
     * @param request the request
     * @return the response entity
     */
    @PutMapping("/belongings/{id}")
    public ResponseEntity<?> updateBelonging(@PathVariable Long id, @RequestBody Belongings request) {
        return this.belongingService.updateBelonging(id, request);
    }
}
