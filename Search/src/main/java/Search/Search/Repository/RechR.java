package Search.Search.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Search.Search.Entity.RechE;
import java.util.List;

@Repository
public interface RechR extends JpaRepository<RechE, Long> {

    List<RechE> findByVille(String ville);
    List<RechE> findByTypeb(String typeb);
    List<RechE> findByVilleAndTypeb(String ville, String typeb);
    
    @Query("SELECT p FROM RechE p WHERE p.prix BETWEEN :min AND :max")
    List<RechE> findByPrixRange(double min, double max);

    @Query("SELECT p FROM RechE p WHERE p.prix BETWEEN :min AND :max AND p.ville = :ville")
    List<RechE> findByVilleAndPrixRange(String ville,double min, double max);
    
    @Query("SELECT p FROM RechE p WHERE p.prix BETWEEN :min AND :max AND p.typeb = :typeb")
    List<RechE> findByTypebAndPrixRange(String typeb,double min, double max);
    
    @Query("SELECT p FROM RechE p WHERE p.prix BETWEEN :min AND :max AND p.ville = :ville AND p.typeb = :typeb")
    List<RechE> findByVilleAndTypebAndPrixRange(String ville,String typeb,double min, double max);
    
}