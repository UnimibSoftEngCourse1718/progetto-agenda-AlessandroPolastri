package polastri.alessandro.agendapersonale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Contatti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatti);

    }

    public void aggiungi(View v){

        Intent intent = new Intent(this, AggiungiContatto.class);
        startActivity(intent);

    }

    @Override
    public void onResume(){

        super.onResume();

        Contatto c = new Contatto();
        TextView prova = (TextView) findViewById(R.id.prova);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            c = (Contatto) extras.getParcelable("object_key");
            prova.setText(c.toString());
        }
    }
}
