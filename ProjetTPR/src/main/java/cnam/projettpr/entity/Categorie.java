package cnam.projettpr.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Categorie {


    private Long idCategorie;

    @Id
    public Long getIdCategorie()
    {
        return this.idCategorie;
    }
    public void setIdCategorie(Long id){
        this.idCategorie = id;
    }

    private String nomCategorie;
    public String getNomCategorie()
    {
        return this.nomCategorie;
    }
    public void setNomCategorie(String nom){
        this.nomCategorie = nom;
    }

    private String designationCategorie;
    public String getDesignationCategorie()
    {
        return this.designationCategorie;
    }
    public void setDesignationCategorie(String designation){
        this.designationCategorie = designation;
    }

}


