package cnam.projettpr.entity;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Table(name = "histofrigo")
@NamedQuery(name="HistoFrigo.findByIdFrigo", query="select h from HistoFrigo h where h.frigo.idFrigo = :id and h.dateHisto = :datehisto")
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
    @JoinColumn(name="idFrigo")
    private Frigo frigo;

    @Transient
    private String nomFrigo;

    private Float tempMatin;

    private Float tempMidi;

    @ManyToOne
    private Utilisateur utilisateur;

    @Transient
    private String actionStr;

    public void setIdHistoFrigo(Integer id){
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

    public Float getTempMatin(){
        return this.tempMatin;
    }
    public void setTempMatin(Float tempMatin){
        this.tempMatin = tempMatin;
    }

    public Float getTempMidi(){
        return this.tempMidi;
    }
    public void setTempMidi(Float tempMidi){
        this.tempMidi = tempMidi;
    }

    public Utilisateur getUtilisateur(){
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
    }

    @Transient
    private String nomUtilisateur;
    @Transient
    public String getNomUtilisateur(){
        if (utilisateur != null){
            return utilisateur.getLogin();
        }
        return "";
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
    public String getNomFrigo(){
        if (frigo != null){
            return frigo.getNomFrigo();
        }
        return "";
    }

    public HistoFrigo(){
        nomFrigo = getNomFrigo();
    }

    @Override
    public String toString() {
        return "";
    }

}


