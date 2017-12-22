package polastri.alessandro.agendapersonale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AggiungiContatto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_contatto);
    }

    public void conferma(View v){

        String x, y, z, w;
        long n;

        EditText enome = (EditText) findViewById(R.id.enome);
        EditText ecognome = (EditText) findViewById(R.id.ecognome);
        EditText etipo = (EditText) findViewById(R.id.etipo);
        EditText etelefono = (EditText) findViewById(R.id.etelefono);

        x = enome.getText().toString();
        y = ecognome.getText().toString();
        z = etipo.getText().toString();
        w = etelefono.getText().toString();
        n = Long.parseLong(w);

        Contatto c = new Contatto(x, y, z, n);

        Intent intent = new Intent(this, Contatti.class);
        Bundle extras = new Bundle();
        extras.putParcelable("object_key", c);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void annulla(View v){

        Intent intent = new Intent(this, Contatti.class);
        startActivity(intent);

    }
}
