package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
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

                            final String dataInizioAutomatica = getDataAutomatica();
                            final String prioritaAutomatica = getPrioritaAutomatica(dataInizioAutomatica, fine.getEditableText().toString());

                            if(prioritaAutomatica != null) {

                                db.salvaAttivita(oggetto.getEditableText().toString(), dataInizioAutomatica, fine.getEditableText().toString(), prioritaAutomatica);
                                Toast.makeText(AttivitaInCorso.this, "Attività inserita!", Toast.LENGTH_SHORT).show();
                                adapter.changeCursor(db.query());
                                dialog.dismiss();
                            } else {

                                Toast.makeText(AttivitaInCorso.this, "La data è sbagliata!\n Correggila!", Toast.LENGTH_SHORT).show();
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
                TextView txt = view.findViewById(R.id.oggetto_attivita_inserito);
                txt.setText(oggetto);
                txt = view.findViewById(R.id.data_scadenza_attivita);
                txt.setText(scadenza);
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

                String suggeriscimeloGetMin = DBManagerAttivita.getMin();
                String suggeriscimelo = DBManagerAttivita.ottieniMinimo(suggeriscimeloGetMin);
                Toast.makeText(AttivitaInCorso.this, "Prossima attività in scadenza:\n" + suggeriscimelo, Toast.LENGTH_LONG).show();
            }
        });

        listView.setAdapter(adapter);
    }

    public String getDataAutomatica() {

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date corrente = calendar.getTime();
        return formato.format(corrente);
    }

    public String getPrioritaAutomatica(String dataIniziale, String dataFinale) {

        if (dataFinale.length() != 10 || dataFinale.charAt(2) != '/' || dataFinale.charAt(5) != '/') {

            return null;
        } else {

            int annoIniziale = Integer.parseInt(dataIniziale.substring(6, 10));
            int annoFinale = Integer.parseInt(dataFinale.substring(6, 10));
            int annoPriorita;
            int meseIniziale = Integer.parseInt(dataIniziale.substring(3, 5));
            int meseFinale = Integer.parseInt(dataFinale.substring(3, 5));
            int mesePriorita;
            int giornoIniziale = Integer.parseInt(dataIniziale.substring(0, 2));
            int giornoFinale = Integer.parseInt(dataFinale.substring(0, 2));
            int giornoPriorita;

            if (annoFinale < annoIniziale) {

                return null;
            }

            if ((annoIniziale == annoFinale) && (meseIniziale == meseFinale) && (giornoIniziale > giornoFinale)) {

                return null;
            }

            if ((annoIniziale == annoFinale) && (meseIniziale > meseFinale)) {

                return null;
            }

            if (meseFinale > 12) {

                return null;
            }

            if (giornoFinale > 31) {

                return null;
            }

            if (meseFinale == 2 && giornoFinale > 29) {

                return null;
            }

            if ((meseFinale == 4 || meseFinale == 6 || meseFinale == 11 || meseFinale == 9) && (giornoFinale == 31)) {

                return null;
            }


            if ((annoFinale - annoIniziale) == 0) {

                annoPriorita = 0;
            } else {

                annoPriorita = (annoFinale - annoIniziale) * 1200;
            }


            if (dataFinale.charAt(3) == 0) {

                mesePriorita = Character.getNumericValue(dataFinale.charAt(4)) * 100;
            } else {

                mesePriorita = meseFinale * 100;
            }


            if (dataFinale.charAt(0) == 0) {

                giornoPriorita = Character.getNumericValue(dataFinale.charAt(1));
            } else {

                giornoPriorita = giornoFinale;
            }

            return Integer.toString((giornoPriorita + mesePriorita + annoPriorita));
        }
    }
}