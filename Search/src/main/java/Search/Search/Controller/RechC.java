package Search.Search.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Search.Search.Entity.RechE;
import Search.Search.Service.RechS;

@RestController
@RequestMapping
public class RechC {

    private final RechS rechS;

    public RechC(RechS rechS) {
        this.rechS = rechS;
    }
    @GetMapping(path = "/aff")
    public String getString() {
        return "test VTP";
    }


    //creation d'un bien dans la base de donnee
    @ResponseStatus(value=HttpStatus.CREATED)
    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void creer(@RequestBody RechE rechE) {
        this.rechS.creer(rechE);
    }

    //afficher tout les biens
    @GetMapping(path = "/lire", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RechE> lire() {
        return this.rechS.lire();
    }

    //gestion des biens par ville
    @GetMapping(path = "/searchV/{ville}")
    public List<RechE> searchV(@PathVariable String ville){
     return this.rechS.searchV(ville);
    }

    //gestion d'un bien pour des villes
    @GetMapping(path = "/searchT/{typeb}")
    public List<RechE> searchT(@PathVariable String typeb){
     return this.rechS.searchT(typeb);
    }

    //gestion a partir des prix
    @GetMapping(path = "/searchP/{min}/{max}")
    public List<RechE> searchP(@PathVariable double min,@PathVariable double max){
     return this.rechS.searchP(min,max);
    }

    //affichage de bien selon des criteres specifique(ville-typeb)
    @GetMapping(path = "/searchVT/{ville}/{typeb}")
    public List<RechE> rechercher(@PathVariable String ville,@PathVariable String typeb){
        return this.rechS.searchVT(ville,typeb);
    }

    //affichage de bien selon des criteres specifique(ville-prix)
    @GetMapping(path = "/searchVP/{ville}/{min}/{max}")
    public List<RechE> searchVP(@PathVariable String ville,@PathVariable double min,@PathVariable double max){
    return this.rechS.searchVP(ville,min,max);
    }
    
    //affichage de bien selon des criteres specifique(typeb-prix)
    @GetMapping(path = "/searchTP/{typeb}/{min}/{max}")
    public List<RechE> searchTP(@PathVariable String typeb,@PathVariable double min,@PathVariable double max){
    return this.rechS.searchTP(typeb,min,max);
    }

     //affichage de bien selon des criteres specifique(ville-typeb-prix)
     @GetMapping(path = "/searchVTP/{ville}/{typeb}/{min}/{max}")
     public List<RechE> searchVTP(@PathVariable String ville,@PathVariable String typeb,@PathVariable double min,@PathVariable double max){
     return this.rechS.searchVTP(ville,typeb,min,max);
     }
}
