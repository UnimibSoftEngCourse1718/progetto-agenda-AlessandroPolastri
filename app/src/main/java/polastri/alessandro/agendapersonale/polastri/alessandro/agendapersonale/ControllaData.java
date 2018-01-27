package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ControllaData {

    public static boolean controlla(String data){

        String dataCorrente = getDataAutomatica();

        if(data.length() != 10 || data.charAt(2) != '/' || data.charAt(5) != '/'){

            return false;
        } else{

            int anno = Integer.parseInt(data.substring(6, 10));
            int annoCorrente = Integer.parseInt(dataCorrente.substring(6, 10));
            int mese = Integer.parseInt(data.substring(3, 5));
            int meseCorrente = Integer.parseInt(dataCorrente.substring(3, 5));
            int giorno = Integer.parseInt(data.substring(0, 2));
            int giornoCorrente = Integer.parseInt(dataCorrente.substring(0, 2));

            if (anno < annoCorrente) {

                return false;
            }

            if ((annoCorrente == anno) && (meseCorrente == mese) && (giornoCorrente > giorno)) {

                return false;
            }

            if ((annoCorrente == anno) && (meseCorrente > mese)) {

                return false;
            }

            if (mese > 12) {

                return false;
            }

            if (giorno > 31) {

                return false;
            }

            if (mese == 2 && giorno > 29) {

                return false;
            }

            if ((mese == 4 || mese == 6 || mese == 11 || mese == 9) && (giorno == 31)) {

                return false;
            }
        }

        return true;
    }

    public static boolean controllaOra(String ora){

        if(ora.length() != 5 || ora.charAt(2) != ':'){

            return false;
        }

        int ore = Integer.parseInt(ora.substring(0, 2));
        int minuti = Integer.parseInt(ora.substring(3, 5));

        return ore >= 0 && ore <= 23 && minuti >= 0 && minuti <= 59;

    }

    public static boolean controllaOrario(String oraIniziale, String oraFinale){

        int hIniziale = Integer.parseInt(oraIniziale.substring(0, 2));
        int mIniziale = Integer.parseInt(oraIniziale.substring(3, 5));
        int hFinale = Integer.parseInt(oraFinale.substring(0, 2));
        int mFinale = Integer.parseInt(oraFinale.substring(3, 5));

        if(hIniziale > hFinale){

            return false;
        } else if(hIniziale == hFinale && mIniziale >= mFinale){

            return false;
        }

        return true;
    }

    public static String getDataAutomatica() {

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date corrente = calendar.getTime();
        return formato.format(corrente);
    }
}
