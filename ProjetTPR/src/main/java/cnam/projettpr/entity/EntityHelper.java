package cnam.projettpr.entity;

import java.util.Date;
import java.util.Set;

public class EntityHelper {

    public static HistoFrigo getHistoriqueDuJour(Frigo frigo, Date jour){
        HistoFrigo result = null;
        Date today = new Date();

        Set<HistoFrigo> histosFrigo = frigo.getHistosFrigo();

        if (histosFrigo != null){
            for (HistoFrigo hf : histosFrigo){
                if (   hf.getDateHisto().getDay() == today.getDay()
                        && hf.getDateHisto().getMonth() == today.getMonth()
                        && hf.getDateHisto().getYear() == today.getYear()){
                    result = hf;
                    break;
                }
            }
        }

        return result;
    }

}
