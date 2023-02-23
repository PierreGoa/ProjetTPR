package cnam.projettpr.entity;

import java.util.Date;

public class EntityHelper {

    public static HistoFrigo getHistoriqueDuJour(Frigo frigo, Date jour){
        HistoFrigo result = null;
        Date today = new Date();
        for (HistoFrigo hf : frigo.getHistosFrigo()){
            if (   hf.getDateHisto().getDay() == today.getDay()
                    && hf.getDateHisto().getMonth() == today.getMonth()
                    && hf.getDateHisto().getYear() == today.getYear()){
                result = hf;
                break;
            }
        }
        return result;
    }

}
