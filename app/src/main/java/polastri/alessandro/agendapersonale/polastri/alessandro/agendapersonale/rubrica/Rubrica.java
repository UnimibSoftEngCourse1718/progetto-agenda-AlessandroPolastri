package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.rubrica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import polastri.alessandro.agendapersonale.R;

public class Rubrica extends AppCompatActivity {

    private final static String TAG = Rubrica.class.getName();
    private String selezioneSpinner;
    private DBManagerRubrica db = null;
    private CursorAdapter adapter;
    private ListView listView = null;
    private Cursor cursor;

    private View.OnClickListener clickListener = new View.OnClickListener() {

        public void onClick(View v) {

            int position = listView.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (db.cancella(id))
                adapter.changeCursor(db.query());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubrica);

        Button aggiungi = findViewById(R.id.aggiungi);
        aggiungi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Rubrica.this);
                @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.layout_dialog_rubrica, null);

                final EditText nome = view.findViewById(R.id.enome);
                final EditText cognome = view.findViewById(R.id.ecognome);
                final EditText telefono = view.findViewById(R.id.etelefono);
                final EditText email = view.findViewById(R.id.eemail);
                final Spinner tipo = view.findViewById(R.id.stipo);
                final EditText tipoPersonalizzato = view.findViewById(R.id.eaggiungiTipo);

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(Rubrica.this, android.R.layout.simple_spinner_item, new String[]{"Nessuno", "Amico", "Collega", "Parente"});
                tipo.setAdapter(spinnerAdapter);
                tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {

                        selezioneSpinner = (String) adapter.getItemAtPosition(pos);
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        throw(new UnsupportedOperationException());
                    }
                });

                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                Button salva = view.findViewById(R.id.salva);
                salva.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        if(!nome.getText().toString().isEmpty()){

                            if(!tipoPersonalizzato.getText().toString().isEmpty()){
                            db.salva(nome.getEditableText().toString(), cognome.getEditableText().toString(), telefono.getEditableText().toString(), email.getEditableText().toString(), tipoPersonalizzato.getEditableText().toString());
                            Toast.makeText(getApplicationContext(), "Contatto inserito!", Toast.LENGTH_SHORT).show();
                            adapter.changeCursor(db.query());
                            dialog.dismiss();
                            } else{
                                db.salva(nome.getEditableText().toString(), cognome.getEditableText().toString(), telefono.getEditableText().toString(), email.getEditableText().toString(), selezioneSpinner);
                                Toast.makeText(getApplicationContext(), "Contatto inserito!", Toast.LENGTH_SHORT).show();
                                adapter.changeCursor(db.query());
                                dialog.dismiss();
                            }

                        } else{
                            Toast.makeText(Rubrica.this, "Il nome è obbligatorio!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        db = new DBManagerRubrica(this);
        listView = findViewById(R.id.lista);
        Cursor crs = db.query();
        adapter = new CursorAdapter(this, crs, 0){

            @SuppressLint("InflateParams")
            public View newView(Context ctx, Cursor arg1, ViewGroup arg2) {

                return getLayoutInflater().inflate(R.layout.riga_layout_rubrica, null);
            }

            public void bindView(View v, Context arg1, Cursor crs) {

                String nome = crs.getString(crs.getColumnIndex(Contatto.CAMPO_NOME));
                String cognome = crs.getString(crs.getColumnIndex(Contatto.CAMPO_COGNOME));
                String telefono = crs.getString(crs.getColumnIndex(Contatto.CAMPO_TELEFONO));
                String email = crs.getString(crs.getColumnIndex(Contatto.CAMPO_EMAIL));
                String tipo = crs.getString(crs.getColumnIndex(Contatto.CAMPO_TIPO));
                TextView txt = v.findViewById(R.id.nome);
                txt.setText(nome);
                txt = v.findViewById(R.id.cognome);
                txt.setText(cognome);
                txt = v.findViewById(R.id.telefono);
                txt.setText(telefono);
                txt = v.findViewById(R.id.email);
                txt.setText(email);
                txt = v.findViewById(R.id.tipo);
                txt.setText(tipo);
                ImageButton imgbtn = v.findViewById(R.id.cancella);
                imgbtn.setOnClickListener(clickListener);
            }

            @Override
            public long getItemId(int position){

                Cursor cursor = adapter.getCursor();
                cursor.moveToPosition(position);
                return cursor.getLong(cursor.getColumnIndex(Contatto.CAMPO_ID));
            }
        };

        SearchView ricerca = findViewById(R.id.ricerca);
        ricerca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                Log.d(TAG, "onQueryTextSubmit ");
                cursor = DBManagerRubrica.cercaTipo(s);

                if (cursor == null) {
                    Toast.makeText(Rubrica.this, "Nessun contatto con il tipo cercato!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Rubrica.this, " Trovati!", Toast.LENGTH_SHORT).show();
                }

                adapter.swapCursor(cursor);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Log.d(TAG, "onQueryTextChange ");
                cursor = DBManagerRubrica.cercaTipo(s);

                if (cursor != null) {
                    adapter.swapCursor(cursor);
                }

                return false;
            }
        });

        listView.setAdapter(adapter);
    }
}