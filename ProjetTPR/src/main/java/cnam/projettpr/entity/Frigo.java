package cnam.projettpr.entity;

import javax.persistence.*;

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

    public Frigo(){}

    public Frigo(String nom){
        this.nomFrigo = nom;
    }

    @Override
    public String toString() {
        return "Frigo [id=" + idFrigo + ", Nom=" + nomFrigo+"]";
    }

}


