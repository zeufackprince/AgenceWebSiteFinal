package Search.Search.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import Search.Search.Entity.RechE;
import Search.Search.Repository.RechR;

@Service
public class RechS {

    private final RechR rechR;

    public RechS(RechR rechR) {
        this.rechR = rechR;
    }

    // Enregistrement dans la base de données
    public void creer(RechE rechE) {
        rechR.save(rechE);
    }

    // Récupération de tous les biens
    public List<RechE> lire() {
        return rechR.findAll();
    }

    //Affichage des biens pour une ville precisse
    public List<RechE> searchV(String ville){
        List<RechE> response = this.rechR.findByVille(ville);
        return response;
       }

    //affichage d'un bien pour differente ville
   public List<RechE> searchT(String typeb){
    List<RechE> response = this.rechR.findByTypeb(typeb);
    return response;
   }

   //affichage d'un bien pour une tranche budgetaire
   public List<RechE> searchP(double min, double max){
    List<RechE> response = this.rechR.findByPrixRange(min,max);
    return response;
   }

    // test ville-typeb
    public List<RechE> searchVT(String ville , String typeb) {
        List<RechE> optionalRechE = this.rechR.findByVilleAndTypeb(ville,typeb);
        return optionalRechE;
    }

    // test ville-prix
    public List<RechE> searchVP(String ville , double min, double max) {
        List<RechE> optionalRechE = this.rechR.findByVilleAndPrixRange(ville,min,max);
        return optionalRechE;
    }

    // test typeb-prix
    public List<RechE> searchTP(String typeb , double min, double max) {
        List<RechE> optionalRechE = this.rechR.findByTypebAndPrixRange(typeb,min,max);
        return optionalRechE;
    }

    // test ville-typeb-prix
    public List<RechE> searchVTP(String ville,String typeb , double min, double max) {
        List<RechE> optionalRechE = this.rechR.findByVilleAndTypebAndPrixRange(ville,typeb,min,max);
        return optionalRechE;
    }

    

   

   
}
