package polastri.alessandro.agendapersonale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AggiungiAttivita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi__attivita);
    }

    public void submit(View v){

        String x, y, z;
        Button bottone = (Button) findViewById(R.id.conferma);
        EditText eattivita = (EditText) findViewById(R.id.eattivita);
        EditText edata = (EditText) findViewById(R.id.edata);
        EditText enota = (EditText) findViewById(R.id.enota);

        x = eattivita.getText().toString();
        y = edata.getText().toString();
        z = enota.getText().toString();

        Attivita a = new Attivita(x, y, z);

        Intent intent = new Intent(this, Main.class);
        Bundle extras = new Bundle();
        extras.putParcelable("object_key", a);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }

}
