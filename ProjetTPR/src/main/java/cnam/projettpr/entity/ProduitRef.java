package cnam.projettpr.entity;

import javax.persistence.*;

@Entity
@Table(name = "produitreference")
public class ProduitRef {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idProduitRef;

    @Column(length = 80, nullable = false)
    private String nomProduitRef;

    @Column(length = 255, nullable = true)
    private String descriptionProduitRef;

    @Column(nullable = false)
    private Integer dureeConservation;

    @ManyToOne
    private Categorie categorie;

    public void setId(Integer id){
        this.idProduitRef = id;
    }
    public Integer getId(){
        return this.idProduitRef;
    }

    public void setNomProduitRef(String nom){
        this.nomProduitRef = nom;
    }
    public String getNomProduitRef(){
        return this.nomProduitRef;
    }

    public void setDescriptionProduitRef(String description){
        this.descriptionProduitRef = description;
    }
    public String getDescriptionProduitRef(){
        return this.descriptionProduitRef;
    }

    public void setDureeConservation(Integer duree){
        this.dureeConservation = duree;
    }
    public Integer getDureeConservation(){
        return this.dureeConservation;
    }

    public void setCategorie(Categorie categorie){
        this.categorie = categorie;
    }
    public Categorie getCategorie(){
        return this.categorie;
    }

}
