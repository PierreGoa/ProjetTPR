package cnam.projettpr.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "produitstock")
public class ProduitStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idProduitStock;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    private java.util.Date dateOuverture;

    @Column(nullable = false)
    private int status = 0;

    @ManyToOne
    private ProduitRef produitRef;

    @Transient
    private String tempsRestant;

    @Transient
    private String statusStr;

    public void setId(Integer id){
        this.idProduitStock = id;
    }
    public Integer getId(){
        return this.idProduitStock;
    }

    public void setDateOuverture(java.util.Date dateOuverture){
        this.dateOuverture = dateOuverture;
    }
    public java.util.Date getDateOuverture(){
        return this.dateOuverture;
    }

    public void setProduitRef(ProduitRef produitRef){this.produitRef = produitRef;}
    public ProduitRef getProduitRef(){return this.produitRef;}

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }

    @Transient
    public String getTempsRestant(){
        String result = "";

        Date dateNow = new Date(System.currentTimeMillis());
        long elapsedms = dateNow.getTime() - dateOuverture.getTime();
        long diff = TimeUnit.HOURS.convert(elapsedms, TimeUnit.MILLISECONDS);

        if (diff > 0){
            long nbJours = diff / 24;
            if (nbJours > 0){
                result = String.format("%d jour(s) ",nbJours);
            }

            if (nbJours > this.produitRef.getDureeConservation()){
                result = "Produit expiré";
            } else {
                long nbHeures = diff - (nbJours * 24);
                if (nbHeures > 0){
                    result += String.format("%d heure(s)",nbHeures);
                }
            }
        }
        else {
            result = "Moins d'une heure";
        }

        return result;
    }

    @Transient
    public void setTempsRestant(String temps){
        this.tempsRestant = temps;
    }

    @Transient
    public String getStatusStr(){
        String result = "";

        switch (getStatus()) {
            case 0 :
                result = "ENCOURS";
                break;
            case 1 :
                result = "CONSOMME";
                break;
            case 2 :
                result = "ERREUR SAISIE";
                break;
            default:
                break;
        }
        return result;
    }

    @Transient
    public void setStatusStr(String statusStr){
        this.statusStr = statusStr;
        if (statusStr.equals("ENCOURS")){
            setStatus(0);
        } else if (statusStr.equals("CONSOMME")){
            setStatus(1);
        } else if (statusStr.equals("ERREURSAISIE")){
            setStatus(2);
        }
    }

    @PrePersist
    public void beforeCreate()
    {
        dateOuverture = new Date();
    }

    public ProduitStock(){
    }
}
