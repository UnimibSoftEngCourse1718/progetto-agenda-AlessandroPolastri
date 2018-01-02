package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita;

import android.content.Context;
import android.database.Cursor;
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

import polastri.alessandro.agendapersonale.R;

public class Impegni extends AppCompatActivity {

    private DBManager db = null;
    private CursorAdapter adapter;
    private ListView listView = null;
    private View.OnClickListener clickListener = new View.OnClickListener() {

        public void onClick(View v) {

            int position = listView.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (db.delete(id))
                adapter.changeCursor(db.query());
        }
    };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attivita_in_corso);

        Button bottoneSalva = findViewById(R.id.salva);
        bottoneSalva.setOnClickListener(new View.OnClickListener(){

            public void onClick(View arg1) {

                salva(arg1);
            }
        });

        db = new DBManager(this);
        listView = findViewById(R.id.lista);
        Cursor crs = db.query();
        adapter = new CursorAdapter(this, crs, 0){

            public View newView(Context ctx, Cursor arg1, ViewGroup arg2) {

                View v = getLayoutInflater().inflate(R.layout.riga_layout_attivita, null);
                return v;
            }

            public void bindView(View v, Context arg1, Cursor crs) {

                String oggetto = crs.getString(crs.getColumnIndex(Attivita.CAMPO_OGGETTO));
                String data = crs.getString(crs.getColumnIndex(Attivita.CAMPO_DATA));
                TextView txt = v.findViewById(R.id.testo_oggetto);
                txt.setText(oggetto);
                txt = v.findViewById(R.id.testo_data);
                txt.setText(data);
                ImageButton imgbtn = v.findViewById(R.id.bottone_cancella);
                imgbtn.setOnClickListener(clickListener);
            }

            public long getItemId(int position){

                Cursor cursor = adapter.getCursor();
                cursor.moveToPosition(position);
                return cursor.getLong(cursor.getColumnIndex(Attivita.CAMPO_ID));
            }
        };

        listView.setAdapter(adapter);
    }

    public void salva(View v){

        EditText eattivita = findViewById(R.id.eattivita);
        EditText edata = findViewById(R.id.edata);
        EditText eorario = findViewById(R.id.eorario);
        EditText enote = findViewById(R.id.enote);
        EditText eallarme = findViewById(R.id.eallarme);

        if(eattivita != null && edata != null){

            db.save(eattivita.getEditableText().toString(), edata.getEditableText().toString(), eorario.getEditableText().toString(), enote.getEditableText().toString(), eallarme.getEditableText().toString());
            Toast.makeText(getApplicationContext(), "Inserito!", Toast.LENGTH_LONG).show();
            adapter.changeCursor(db.query());
        }else{
            Toast.makeText(getApplicationContext(), "L'oggetto e la data devono essere inseriti!", Toast.LENGTH_LONG).show();
        }
    }
}