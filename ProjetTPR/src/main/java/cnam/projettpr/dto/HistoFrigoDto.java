package cnam.projettpr.dto;

import cnam.projettpr.entity.HistoFrigo;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class HistoFrigoDto {

    private Integer idHistoFrigo;
    private Integer idFrigo;
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    private Date dateHisto;
    private String actionStr;
    private String nomFrigo;
    private Float tempMatin;
    private Float tempMidi;

    public Integer getIdHistoFrigo(){
        return this.idHistoFrigo;
    }
    public void setIdHistoFrigo(Integer idHistoFrigo) {
        this.idHistoFrigo = idHistoFrigo;
    }
    public Integer getIdFrigo(){
        return this.idFrigo;
    }
    public void setIdFrigo(Integer idFrigo){
        this.idFrigo = idFrigo;
    }
    public Date getDateHisto(){
        return this.dateHisto;
    }
    public void setDateHisto(Date dateHisto){
        this.dateHisto = dateHisto;
    }
    public String getActionStr(){
        return this.actionStr;
    }
    public void setActionStr(String actionStr){
        this.actionStr = actionStr;
    }
    public String getNomFrigo(){
        return this.nomFrigo;
    }
    public void setNomFrigo(String nomFrigo){
        this.nomFrigo = nomFrigo;
    }
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

    public String toString(){return "";}

    public HistoFrigoDto(){}

    public HistoFrigoDto(HistoFrigo histoFrigo){
        this.idFrigo = histoFrigo.getFrigo().getId();
        this.idFrigo = this.idFrigo == null ? 0 :this.idFrigo;
        this.idHistoFrigo = histoFrigo.getIdHistoFrigo();
        this.idHistoFrigo = this.idHistoFrigo == null ? 0 :this.idHistoFrigo;
        this.dateHisto = histoFrigo.getDateHisto();
        this.actionStr = histoFrigo.getActionStr();
        this.nomFrigo = histoFrigo.getNomFrigo();
        this.tempMatin = histoFrigo.getTempMatin();
        this.tempMatin = this.tempMatin == null ? 0 :this.tempMatin;
        this.tempMidi = histoFrigo.getTempMidi();
        this.tempMidi = this.tempMidi == null ? 0 :this.tempMidi;
    }
}
