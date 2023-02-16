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
    private ProduitRef produitRef;

    @Transient
    private String actionStr;

    public void setId(Integer id){
        this.idHistoProduitStock = id;
    }
    public Integer getId(){
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
    public void setActionStr(String statusStr){
        this.actionStr = statusStr;
        if (statusStr.equals("INSERTION")){
            setAction(0);
        } else if (statusStr.equals("MODIFICATION")){
            setAction(1);
        }
    }

    public HistoProduitStock(){
    }
}
