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

@RestController
@RequestMapping(path = "/api")
public class BelongingController {

    private final BelongingService belongingService;

    private final UserRepository userRepository;

    public BelongingController(BelongingService belongingService, UserRepository userRepository)
    {
        this.belongingService = belongingService;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/belongings/create")
    public Belongings createBienImmobilier(@RequestBody Belongings request) throws RelationNotFoundException {

        return belongingService.createBienImmobilier(request);
    }

    @GetMapping(path = "/belongings/all")
    public List<Belongings> getBelongings()
    {
        return this.belongingService.getAll();
    }


    @GetMapping(path = "/belongings/{type}")
    public Optional<List<Belongings>> getBelongingByType(@PathVariable BelongingType type)
    {
        return this.belongingService.getBelongingByType(type);
    }

    @GetMapping(path = "/belongings/{id}")
    public Optional<Belongings> getABelongings(@PathVariable Long id)
    {
        return this.belongingService.getAbelonging(id);
    }



    @PutMapping("/belongings/{id}")
    public ResponseEntity<?> updateBelonging(@PathVariable Long id, @RequestBody Belongings request) {
        return this.belongingService.updateBelonging(id, request);
    }
}
