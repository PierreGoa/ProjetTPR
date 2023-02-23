package cnam.projettpr.entity;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Stream;

@Entity
@Table(name = "frigo")
public class Frigo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idFrigo;

    @Column(length = 80, nullable = false)
    private String nomFrigo;

    @Column
    private Float tempMin;

    @Column
    private Float tempMax;

    //Non persistent : Alimentées par l'historique frigo du jour.
    @Transient
    public Float tempMatin;
    @Transient
    public Float tempMidi;

    @OneToMany(mappedBy = "frigo",cascade = CascadeType.ALL)
    private Set<HistoFrigo> histosFrigo;

    public void setId(Integer id){
        this.idFrigo = id;
    }
    public Integer getId(){
        return this.idFrigo;
    }

    public void setNomFrigo(String nom){
        this.nomFrigo = nom;
    }
    public String getNomFrigo(){
        return this.nomFrigo;
    }

    public void setTempMin(Float temp){this.tempMin = temp;}
    public Float getTempMin(){return this.tempMin;}

    public void setTempMax(Float temp){this.tempMax = temp;}
    public Float getTempMax(){return this.tempMax;}

    public Set<HistoFrigo> getHistosFrigo(){
        return histosFrigo;
    }

    @Transient
    public Float getTempMatin(){
        HistoFrigo hf = getHistoriqueDuJour();
        if (hf != null){
            return hf.getTempMatin();
        }
        return Float.valueOf(0);
    }
    @Transient
    public void setTempMatin(Float tempMatin){
        this.tempMatin = tempMatin;
    }

    @Transient
    public Float getTempMidi(){
        HistoFrigo hf = getHistoriqueDuJour();
        if (hf != null){
            return hf.getTempMidi();
        }
        return Float.valueOf(0);
    }

    @Transient
    public void setTempMidi(Float tempMidi){
        this.tempMidi = tempMidi;
    }

    public HistoFrigo getHistoriqueDuJour(){
        HistoFrigo result = null;
        if (this.histosFrigo != null){
            Date today = new Date();
            for (HistoFrigo hf : this.histosFrigo){
                if (   hf.getDateHisto() != null
                        && hf.getDateHisto().getDay() == today.getDay()
                        && hf.getDateHisto().getMonth() == today.getMonth()
                        && hf.getDateHisto().getYear() == today.getYear()){
                    result = hf;
                    break;
                }
            }
        }
        return result;
    }

    public void updateTemperatureMinMax(HistoFrigo histoFrigo){
        if (histoFrigo != null){
            if (histoFrigo.getTempMatin() < this.getTempMin()){
                this.setTempMin(histoFrigo.getTempMatin());
            }
            if (histoFrigo.getTempMidi() < this.getTempMin()){
                this.setTempMin(histoFrigo.getTempMidi());
            }
            if (histoFrigo.getTempMatin() > this.getTempMax()){
                this.setTempMax(histoFrigo.getTempMatin());
            }
            if (histoFrigo.getTempMidi() > this.getTempMax()){
                this.setTempMax(histoFrigo.getTempMidi());
            }
        }
    }

    public Frigo(){}

    public Frigo(String nom){
        this.nomFrigo = nom;
    }

    @Override
    public String toString() {
        return "Frigo [id=" + idFrigo + ", Nom=" + nomFrigo+"]";
    }

}


