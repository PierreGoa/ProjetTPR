package cnam.projettpr.entity;

import cnam.projettpr.repository.HistoFrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
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

    @OneToMany(mappedBy = "produitStock",cascade = CascadeType.ALL)
    private Set<HistoProduitStock> histosProduitStock;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    @Transient Date dateExpiration;

    @Transient
    public Date getDateExpiration(){
        LocalDateTime dateExp = dateOuverture.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(this.produitRef.getDureeConservation());

        ZonedDateTime zonedDateTime = dateExp.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    @Transient
    public void setDateExpiration(Date dateExp){

    }

    @Transient
    public String getTempsRestant(){
        String result = "";

        //Calcul de la date d'expiration
        LocalDateTime dateExpiration = dateOuverture.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        dateExpiration = dateExpiration.plusDays(this.produitRef.getDureeConservation());

        LocalDateTime dateNow = LocalDateTime.now();

        long nbJours = dateNow.until(dateExpiration, ChronoUnit.DAYS);
        long nbMois = dateNow.until(dateExpiration, ChronoUnit.MONTHS);

        Period period = getPeriod(dateNow, dateExpiration);
        long time[] = getTime(dateNow, dateExpiration);

        if (period.getYears() > 0)
        {
            result += String.format("%d année(s) ",period.getYears());
        }
        if (period.getMonths() > 0)
        {
            result += String.format("%d mois(s) ",period.getMonths());
        }
        if (period.getDays() > 0)
        {
            result += String.format("%d jour(s) ",period.getDays());
        }

        if (result == ""){
            result = "Produit expiré";
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
        } else if (statusStr.equals("ERREUR SAISIE")){
            setStatus(2);
        }
    }

    @PrePersist
    public void beforeCreate(){
        dateOuverture = new Date();
    }


    /*
    JPA
    before persist is called for a new entity – @PrePersist
    after persist is called for a new entity – @PostPersist
    before an entity is removed – @PreRemove
    after an entity has been deleted – @PostRemove
    before the update operation – @PreUpdate
    after an entity is updated – @PostUpdate
    after an entity has been loaded – @PostLoad
     */


    //Private
    private Period getPeriod(LocalDateTime fromDate, LocalDateTime toDate) {
        return Period.between(fromDate.toLocalDate(), toDate.toLocalDate());
    }

    private long[] getTime(LocalDateTime fromDate, LocalDateTime toDate) {
        LocalDateTime today = LocalDateTime.of(
                toDate.getYear(),
                toDate.getMonthValue(),
                toDate.getDayOfMonth(),
                fromDate.getHour(),
                fromDate.getMinute(),
                fromDate.getSecond());

        Duration duration = Duration.between(today, toDate);

        long seconds = duration.getSeconds();

        long hours = seconds / 3600;
        long minutes = ((seconds % 3600) / 60);
        long secs = (seconds % 60);

        return new long[]{hours, minutes, secs};
    }


    public ProduitStock(){
    }
}
