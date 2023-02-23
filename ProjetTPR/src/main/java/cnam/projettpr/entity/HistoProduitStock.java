package cnam.projettpr.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "histoproduitstock")
public class HistoProduitStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idHistoProduitStock;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    private java.util.Date dateHisto;
    @Column(nullable = false)
    private int action = 0;
    @ManyToOne
    private ProduitStock produitStock;
    @ManyToOne
    private Utilisateur utilisateur;
    @Transient
    private String actionStr;
    public void setIdHistoProduitStock(Integer id){
        this.idHistoProduitStock = id;
    }
    public Integer getIdHistoProduitStock(){
        return this.idHistoProduitStock;
    }
    public void setDateHisto(java.util.Date dateHisto){
        this.dateHisto = dateHisto;
    }
    public java.util.Date getDateHisto(){
        return this.dateHisto;
    }
    public void setAction(int action){
        this.action = action;
    }
    public int getAction(){
        return this.action;
    }

    public ProduitStock getProduitStock(){
        return this.produitStock;
    }

    public void setProduitStock(ProduitStock produitStock){
        this.produitStock = produitStock;
    }

    public Utilisateur getUtilisateur(){
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
    }

    @Transient
    public String getActionStr(){
        String result = "";

        switch (getAction()) {
            case 0 :
                result = "INSERTION";
                break;
            case 1 :
                result = "MODIFICATION";
                break;
            default:
                break;
        }
        return result;
    }

    @Transient
    public void setActionStr(String actionStr){
        this.actionStr = actionStr;
        if (actionStr.equals("INSERTION")){
            setAction(0);
        } else if (actionStr.equals("MODIFICATION")){
            setAction(1);
        }
    }

    @Transient
    private String nomProduitStock;
    @Transient
    public String getNomProduitStock(){
        if (produitStock != null){
            return produitStock.getProduitRef().getNomProduitRef();
        }
        return "";
    }

    @Transient
    private String statutStr;
    @Transient
    public String getStatutStr(){
        if (produitStock != null){
            return produitStock.getStatusStr();
        }
        return "";
    }

    @Transient
    private String nomUtilisateur;
    @Transient
    public String getNomUtilisateur(){
        if (utilisateur != null){
            return utilisateur.getNom();
        }
        return "";
    }

    public HistoProduitStock(){
        nomProduitStock = getNomProduitStock();
    }

    public HistoProduitStock(ProduitStock produitStock, int action, Utilisateur utilisateur){
        this.produitStock = produitStock;
        this.utilisateur = utilisateur;
        this.action = action;
        this.dateHisto = new Date();
        nomProduitStock = getNomProduitStock();
    }

}
