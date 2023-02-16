package cnam.projettpr.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Table(name = "histofrigo")
public class HistoFrigo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idHistoFrigo;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    private java.util.Date dateHisto;

    @Column(nullable = false)
    private int action = 0;

    @ManyToOne
    private Frigo frigo;

    @Transient
    private String nomFrigo;

    private Float tempMatin;

    private Float tempMidi;

    @Transient
    private String actionStr;

    public void setIdFrigo(Integer id){
        this.idHistoFrigo = id;
    }
    public Integer getIdHistoFrigo(){
        return this.idHistoFrigo;
    }

    public void setAction(int action){this.action = action;}
    public int getAction(){return this.action;}

    public void setDateHisto(java.util.Date date){this.dateHisto = date;}
    public java.util.Date getDateHisto(){return this.dateHisto;}

    public void setFrigo(Frigo frigo){this.frigo = frigo;}
    public Frigo getFrigo(){return this.frigo;}

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


    @Transient
    public String getNomFrigo(){
        if (frigo != null){
            return frigo.getNomFrigo();
        }
        return "";
    }

    @Transient
    public Float getTempMatin(){
        return this.tempMatin;
    }

    @Transient
    public Float getTempMidi(){
        return this.tempMidi;
    }

    public HistoFrigo(){}

    @Override
    public String toString() {
        return "";
    }

}


