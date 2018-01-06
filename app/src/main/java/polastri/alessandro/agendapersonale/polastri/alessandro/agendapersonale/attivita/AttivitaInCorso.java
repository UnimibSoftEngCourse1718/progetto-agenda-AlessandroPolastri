package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.health.SystemHealthManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import polastri.alessandro.agendapersonale.R;

public class AttivitaInCorso extends AppCompatActivity {

    private DBManagerAttivita db = null;
    private CursorAdapter adapter;
    private ListView listView = null;
    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {

            int position = listView.getPositionForView(view);
            long id = adapter.getItemId(position);
            if(db.cancellaAttivita(id)){

                adapter.changeCursor(db.query());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attivita_in_corso);

        Button aggiungiAttivita = findViewById(R.id.aggiungi_attivita);
        aggiungiAttivita.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AttivitaInCorso.this);
                @SuppressLint("InflateParams")
                View v = getLayoutInflater().inflate(R.layout.layout_dialog_attivitaincorso, null);

                final EditText oggetto = v.findViewById(R.id.eoggetto_attivita_in_corso);
                final EditText fine = v.findViewById(R.id.escadenza_attivita);

                builder.setView(v);
                final AlertDialog dialog = builder.create();
                dialog.show();

                Button salvaAttivita = v.findViewById(R.id.salva_attivita);
                salvaAttivita.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        if(!oggetto.getText().toString().isEmpty() && !fine.getText().toString().isEmpty()){

                            final String dataInizioAutomatica = getDataAutomatica(System.currentTimeMillis());
                            final String prioritaAutomatica = getPrioritaAutomatica(dataInizioAutomatica, fine.getEditableText().toString());

                            if(prioritaAutomatica != null) {

                                db.salvaAttivita(oggetto.getEditableText().toString(), dataInizioAutomatica, fine.getEditableText().toString(), prioritaAutomatica);
                                Toast.makeText(AttivitaInCorso.this, "Attività inserita!", Toast.LENGTH_SHORT).show();
                                adapter.changeCursor(db.query());
                                dialog.dismiss();
                            } else {

                                Toast.makeText(AttivitaInCorso.this, "La data è sbagliata!", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(AttivitaInCorso.this, "Prima inserisci i dati richiesti!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        db = new DBManagerAttivita(this);
        listView = findViewById(R.id.lista_attivita_in_corso);
        Cursor crs = db.query();
        adapter = new CursorAdapter(this, crs, 0) {

            @Override
            @SuppressLint("InflateParams")
            public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

                return getLayoutInflater().inflate(R.layout.riga_layout_attivita, null);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                String oggetto = cursor.getString(cursor.getColumnIndex(Attivita.CAMPO_OGGETTO));
                String scadenza = cursor.getString(cursor.getColumnIndex(Attivita.CAMPO_FINE));
                String provvisorio = cursor.getString(cursor.getColumnIndex(Attivita.CAMPO_PRIORITA));
                TextView txt = view.findViewById(R.id.oggetto_attivita_inserito);
                txt.setText(oggetto);
                txt = view.findViewById(R.id.data_scadenza_attivita);
                txt.setText(scadenza);
                txt = view.findViewById(R.id.txtprovvisorio);
                txt.setText(provvisorio);
                ImageButton cancellaAttivitaInCorso = view.findViewById(R.id.cancella_attivitaincorso);
                cancellaAttivitaInCorso.setOnClickListener(clickListener);
            }

            @Override
            public long getItemId(int position) {

                Cursor cursor = adapter.getCursor();
                cursor.moveToPosition(position);
                return cursor.getLong(cursor.getColumnIndex(Attivita.CAMPO_ID));
            }
        };

        Button suggerisciAttivita = findViewById(R.id.suggerisci_attivita_in_corso);
        suggerisciAttivita.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Cursor cursor = DBManagerAttivita.getMin();

                if (cursor == null) {

                    Toast.makeText(AttivitaInCorso.this, "Errore!", Toast.LENGTH_LONG).show();
                } else {

                    int minimo = cursor.getColumnIndex("MINIMO");
                    String a = cursor.getString(minimo);
                    Toast.makeText(AttivitaInCorso.this, "Prossima attività in scadenza:\n" + a, Toast.LENGTH_LONG).show();
                }
            }
        });

        listView.setAdapter(adapter);
    }

    public String getDataAutomatica(long timeMillisecond) {

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date corrente = calendar.getTime();
        return formato.format(corrente);
    }

    public String getPrioritaAutomatica(String dataIniziale, String dataFinale){

        if(dataFinale.length() != 10 || dataFinale.charAt(2) != '/' || dataFinale.charAt(5) != '/'){

            return null;
        }

        int anno1 = Integer.parseInt(dataIniziale.substring(6, 10));
        int anno2 = Integer.parseInt(dataFinale.substring(6, 10));
        int annoPriorita = 0;

        if(anno2 < anno1){

            return null;
        } else if ((anno1 == anno2 && (Integer.parseInt(dataIniziale.substring(3, 5)) == Integer.parseInt(dataFinale.substring(3, 5))) && Integer.parseInt(dataIniziale.substring(0, 2)) > Integer.parseInt(dataFinale.substring(0, 2)))){

            return null;
        } else if((anno1 == anno2 && (Integer.parseInt(dataIniziale.substring(3, 5)) > Integer.parseInt(dataFinale.substring(3, 5))))){

            return null;
        } else if((Integer.parseInt(dataIniziale.substring(3, 5)) > 12 || (Integer.parseInt(dataFinale.substring(3, 5)) > 12))){

            return null;
        } else if(Integer.parseInt(dataIniziale.substring(0, 2)) > 31 || Integer.parseInt(dataFinale.substring(0, 2)) > 31){

            return null;
        } else if(Integer.parseInt(dataFinale.substring(3, 5)) == 2 && Integer.parseInt(dataFinale.substring(0, 2)) > 29){

            return null;
        } else if((Integer.parseInt(dataFinale.substring(3, 5)) == 4 || Integer.parseInt(dataFinale.substring(3, 5)) == 6 || Integer.parseInt(dataFinale.substring(3, 5)) == 11 || Integer.parseInt(dataFinale.substring(3, 5)) == 9) && Integer.parseInt(dataFinale.substring(0, 2)) == 31){

            return null;
        }

        if(anno2 - anno1 == 0){

            annoPriorita = 0;
        } else if (anno2 > anno1){

            annoPriorita = (anno2 - anno1) * 9999;
        }

        int mese1, mese2, mesePriorita = 0;

        if(dataIniziale.charAt(3) == '0' && dataFinale.charAt(3) == '0' && Character.getNumericValue(dataIniziale.charAt(4)) <= Character.getNumericValue(dataFinale.charAt(4))){

            mese1 = Character.getNumericValue(dataIniziale.charAt(4));
            mese2 = Character.getNumericValue(dataFinale.charAt(4));
            mesePriorita = (mese2 - mese1) * 900;
        } else if(dataIniziale.charAt(3) == '0' && dataFinale.charAt(3) == '0' && Character.getNumericValue(dataIniziale.charAt(4)) > Character.getNumericValue(dataFinale.charAt(4))){

            mese1 = Character.getNumericValue(dataIniziale.charAt(4));
            mese2 = Character.getNumericValue(dataFinale.charAt(4));
            mesePriorita = ((12 - mese1) + mese2) * 900;
        } else if(dataIniziale.charAt(3) != '0' && dataFinale.charAt(3) != '0' && Integer.parseInt(dataIniziale.substring(3, 5)) <= Integer.parseInt(dataFinale.substring(3, 5))){

            mese1 = Integer.parseInt(dataIniziale.substring(3, 5));
            mese2 = Integer.parseInt(dataFinale.substring(3, 5));
            mesePriorita = (mese2 - mese1) * 900;
        } else if(dataIniziale.charAt(3) != '0' && dataFinale.charAt(3) != '0' && Integer.parseInt(dataIniziale.substring(3, 5)) > Integer.parseInt(dataFinale.substring(3, 5))){

            mese1 = Integer.parseInt(dataIniziale.substring(3, 5));
            mese2 = Integer.parseInt(dataFinale.substring(3, 5));
            mesePriorita = ((12 - mese1) + mese2) * 900;
        } else if(dataIniziale.charAt(3) != '0' && dataFinale.charAt(3) == '0'){

            mese1 = Integer.parseInt(dataIniziale.substring(3, 5));
            mese2 = Character.getNumericValue(dataFinale.charAt(4));
            mesePriorita = ((12 - mese1) + mese2) * 900;
        } else if(dataIniziale.charAt(3) == '0' && dataFinale.charAt(3) != '0'){

            mese1 = Character.getNumericValue(dataIniziale.charAt(4));
            mese2 = Integer.parseInt(dataFinale.substring(3, 5));
            mesePriorita = (mese2 - mese1) * 900;
        }

        int giorno1, giorno2, giornoPriorita = 0;

        if(dataIniziale.charAt(0) == '0' && dataFinale.charAt(0) == '0' && dataIniziale.charAt(1) <= dataFinale.charAt(1)) {

            giorno1 = Character.getNumericValue(dataIniziale.charAt(1));
            giorno2 = Character.getNumericValue(dataFinale.charAt(1));
            giornoPriorita = giorno2 - giorno1;
        } else if(dataIniziale.charAt(0) == '0' && dataFinale.charAt(0) == '0' && dataIniziale.charAt(1) > dataFinale.charAt(1)){

            giorno1 = Character.getNumericValue(dataIniziale.charAt(1));
            giorno2 = Character.getNumericValue(dataFinale.charAt(1));
            giornoPriorita = (30 - giorno1) + giorno2;
        } else if(dataIniziale.charAt(0) != '0' && dataFinale.charAt(0) != '0' && Integer.parseInt(dataIniziale.substring(0, 2)) <= Integer.parseInt(dataFinale.substring(0, 2))){

            giorno1 = Integer.parseInt(dataIniziale.substring(0, 2));
            giorno2 = Integer.parseInt(dataFinale.substring(0, 2));
            giornoPriorita = giorno2 - giorno1;
        } else if(dataIniziale.charAt(0) != '0' && dataFinale.charAt(0) != '0' && Integer.parseInt(dataIniziale.substring(0, 2)) > Integer.parseInt(dataFinale.substring(0, 2))){

            giorno1 = Integer.parseInt(dataIniziale.substring(0, 2));
            giorno2 = Integer.parseInt(dataFinale.substring(0, 2));
            giornoPriorita = (30 - giorno1) + giorno2;
        } else if(dataIniziale.charAt(0) == '0' && dataFinale.charAt(0) != '0'){

            giorno1 = Character.getNumericValue(dataIniziale.charAt(1));
            giorno2 = Integer.parseInt(dataFinale.substring(0, 2));
            giornoPriorita = giorno2 - giorno1;
        } else if (dataIniziale.charAt(0) != '0' && dataFinale.charAt(0) == '0'){

            giorno1 = Integer.parseInt(dataIniziale.substring(0, 2));
            giorno2 = Character.getNumericValue(dataFinale.charAt(1));
            giornoPriorita = (30 - giorno1) + giorno2;
        }

        return Integer.toString((giornoPriorita + mesePriorita + annoPriorita));
    }
}