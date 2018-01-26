package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.impegni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import polastri.alessandro.agendapersonale.R;
import polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.ControllaData;

public class ImpegniInCorso extends AppCompatActivity {

    private final static String TAG = ImpegniInCorso.class.getName();
    private Cursor cursor;
    private DBManagerImpegno db = null;
    private CursorAdapter adapter;
    private ListView listView = null;
    private String selezioneSpinnerTipo;
    private String selezioneSpinnerRipetizione;
    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view){

            int position = listView.getPositionForView(view);
            long id = adapter.getItemId(position);
            if(db.cancellaImpegno(id)){

                adapter.changeCursor(db.query());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impegni_in_corso);

        Button aggiungiImpegno = findViewById(R.id.aggiungi_impegno);
        aggiungiImpegno.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                AlertDialog.Builder builder = new AlertDialog.Builder(ImpegniInCorso.this);
                @SuppressLint("InflateParams")
                View v = getLayoutInflater().inflate(R.layout.layout_dialog_impegniincorso, null);

                final EditText oggetto = v.findViewById(R.id.eimpegno);
                final EditText data = v.findViewById(R.id.edata);
                final EditText oraInizio = v.findViewById(R.id.eorainizio);
                final EditText oraFinale = v.findViewById(R.id.eorafine);
                final EditText allarme = v.findViewById(R.id.eallarme);
                final EditText note = v.findViewById(R.id.enote);
                final EditText tipoPersonalizzato = v.findViewById(R.id.eaggiungitipo);

                final Spinner ripetizione = v.findViewById(R.id.sripetizione);
                ArrayAdapter<String> spinnerAdapterRipetizione = new ArrayAdapter<>(ImpegniInCorso.this, android.R.layout.simple_spinner_item, new String[]{"Mai", "Ogni giorno", "Ogni settimana", "Ogni mese", "Ogni anno"});
                ripetizione.setAdapter(spinnerAdapterRipetizione);
                ripetizione.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {

                        selezioneSpinnerRipetizione = (String) adapter.getItemAtPosition(pos);
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        throw(new UnsupportedOperationException());
                    }
                });

                final Spinner tipo = v.findViewById(R.id.sstipo);
                ArrayAdapter<String> spinnerAdapterTipo = new ArrayAdapter<>(ImpegniInCorso.this, android.R.layout.simple_spinner_item, new String[]{"Nessuno", "Appuntamento", "Lavorativo", "Importante"});
                tipo.setAdapter(spinnerAdapterTipo);
                tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {

                        selezioneSpinnerTipo = (String) adapter.getItemAtPosition(pos);
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        throw(new UnsupportedOperationException());
                    }
                });

                builder.setView(v);
                final AlertDialog dialog = builder.create();
                dialog.show();

                Button salva = v.findViewById(R.id.salva_impegno);
                salva.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v){

                        if(!oggetto.getText().toString().isEmpty() && !data.getText().toString().isEmpty() && !oraInizio.getText().toString().isEmpty() && !oraFinale.getText().toString().isEmpty()){

                            if(!tipoPersonalizzato.getText().toString().isEmpty() && ControllaData.controlla(data.getEditableText().toString())){
                                db.salvaImpegno(oggetto.getEditableText().toString(), data.getEditableText().toString(), oraInizio.getEditableText().toString(), oraFinale.getEditableText().toString(), selezioneSpinnerRipetizione, allarme.getEditableText().toString(), note.getEditableText().toString(), tipoPersonalizzato.getEditableText().toString());
                                Toast.makeText(ImpegniInCorso.this, "Impegno inserito!", Toast.LENGTH_SHORT).show();
                                adapter.changeCursor(db.query());
                                dialog.dismiss();
                            } else if(ControllaData.controlla(data.getEditableText().toString())){

                                db.salvaImpegno(oggetto.getEditableText().toString(), data.getEditableText().toString(), oraInizio.getEditableText().toString(), oraFinale.getEditableText().toString(), selezioneSpinnerRipetizione, allarme.getEditableText().toString(), note.getEditableText().toString(), selezioneSpinnerTipo);
                                Toast.makeText(ImpegniInCorso.this, "Impegno inserito!", Toast.LENGTH_SHORT).show();
                                adapter.changeCursor(db.query());
                                dialog.dismiss();
                            } else{

                                Toast.makeText(ImpegniInCorso.this, "La data è sbagliata! Correggila!", Toast.LENGTH_SHORT).show();
                            }
                        } else{

                            Toast.makeText(ImpegniInCorso.this, "I campi con * sono obbligatori!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        db = new DBManagerImpegno(ImpegniInCorso.this);
        listView = findViewById(R.id.lista_impegni);
        Cursor crs = db.query();
        adapter = new CursorAdapter(ImpegniInCorso.this, crs, 0){

            @Override
            @SuppressLint("InflateParams")
            public View newView(Context context, Cursor cursor, ViewGroup viewGroup){

                return getLayoutInflater().inflate(R.layout.riga_layout_impegno, null);
            }

            @Override
            public void bindView(View v, Context arg1, Cursor crs) {
                String oggetto = crs.getString(crs.getColumnIndex(Impegno.CAMPO_OGGETTO));
                String data = crs.getString(crs.getColumnIndex(Impegno.CAMPO_DATA));
                String oraIniziale = crs.getString(crs.getColumnIndex(Impegno.CAMPO_ORA_INIZIO));
                String oraFinale = crs.getString(crs.getColumnIndex(Impegno.CAMPO_ORA_FINALE));
                final String ripetizione = crs.getString(crs.getColumnIndex(Impegno.CAMPO_RIPETIZIONE));
                final String tipo = crs.getString(crs.getColumnIndex(Impegno.CAMPO_TIPO));
                final String allarme = crs.getString(crs.getColumnIndex(Impegno.CAMPO_ALLARME));
                final String nota = crs.getString(crs.getColumnIndex(Impegno.CAMPO_NOTE));
                TextView txt = v.findViewById(R.id.woggetto);
                txt.setText(oggetto);
                txt = v.findViewById(R.id.wdata);
                txt.setText(data);
                txt = v.findViewById(R.id.worainizio);
                txt.setText(oraIniziale);
                txt = v.findViewById(R.id.worafine);
                txt.setText(oraFinale);
                ImageButton cancellaImpegno = v.findViewById(R.id.cancella_impegno);
                cancellaImpegno.setOnClickListener(clickListener);
                ImageButton mostraNote = v.findViewById(R.id.mostra_note);
                mostraNote.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view){

                        AlertDialog.Builder builder = new AlertDialog.Builder(ImpegniInCorso.this);
                        @SuppressLint("InflateParams")
                        View v = getLayoutInflater().inflate(R.layout.layout_dialog_note_impegni, null);

                        TextView text = v.findViewById(R.id.tdialogAllarme);
                        text.setText(allarme);
                        text = v.findViewById(R.id.mnota);
                        text.setText(nota);
                        text = v.findViewById(R.id.tripetere);
                        text.setText(ripetizione);
                        text = v.findViewById(R.id.mtipo);
                        text.setText(tipo);

                        builder.setView(v);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
            @Override
            public long getItemId(int position){

                Cursor cursore = adapter.getCursor();
                cursore.moveToPosition(position);
                return cursore.getLong(cursore.getColumnIndex(Impegno.CAMPO_ID));
            }
        };

        SearchView ricerca = findViewById(R.id.ricerca_tipo_impegno);
        ricerca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                Log.d(TAG, "onQueryTextSubmit ");
                cursor = DBManagerImpegno.cercaTipo(s);

                if (cursor == null) {

                    Toast.makeText(ImpegniInCorso.this, "Nessun impegno con il tipo cercato!", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(ImpegniInCorso.this, " Trovati!", Toast.LENGTH_SHORT).show();
                }

                adapter.swapCursor(cursor);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Log.d(TAG, "onQueryTextChange ");
                cursor = DBManagerImpegno.cercaTipo(s);

                if (cursor != null) {

                    adapter.swapCursor(cursor);
                }

                return false;
            }
        });

        listView.setAdapter(adapter);
    }
}
