package cnam.projettpr.entity;

import javax.persistence.*;

@Entity
@Table(name = "categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCategorie;

    @Column(length = 80, nullable = false)
    private String nomCategorie;

    @Column(length = 255, nullable = true)
    private String designationCategorie;

    public void setId(Integer id){
        this.idCategorie = id;
    }
    public Integer getId(){
        return this.idCategorie;
    }

    public void setNomCategorie(String nom){
        this.nomCategorie = nom;
    }

    public String getNomCategorie(){
        return this.nomCategorie;
    }

    public void setDesignationCategorie(String designation){
        this.designationCategorie = designation;
    }

    public String getDesignationCategorie(){
        return this.designationCategorie;
    }


    public Categorie(){}

    public Categorie(String nom, String designation){
        this.nomCategorie = nom;
        this.designationCategorie = designation;
    }

    @Override
    public String toString() {
        return "Categorie [id=" + idCategorie + ", Nom=" + nomCategorie + ", Designation=" + designationCategorie+"]";
    }

}


