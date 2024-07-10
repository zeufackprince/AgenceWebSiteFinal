package Search.Search.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bien")
public class RechE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ville;
    private String typeb;
    private double dimension;
    private String localisation;
    private double prix;
    private String imageb;

    public RechE() {
       
    }

    public RechE(int id, String ville, String typeb, double dimension, String localisation, double prix, String imageb) {
        this.id = id;
        this.ville = ville;
        this.typeb = typeb;
        this.dimension = dimension;
        this.localisation = localisation;
        this.prix = prix;
        this.imageb = imageb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setNom(String ville) {
        this.ville = ville;
    }

    public String getTypeb() {
        return typeb;
    }

    public void setTypep(String typeb) {
        this.typeb = typeb;
    }

    public double getDimension() {
        return dimension;
    }

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImageb() {
        return imageb;
    }

    public void setImageb(String imageb) {
        this.imageb = imageb;
    }

}
