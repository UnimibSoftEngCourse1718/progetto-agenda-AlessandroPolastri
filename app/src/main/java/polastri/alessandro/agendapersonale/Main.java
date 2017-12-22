package polastri.alessandro.agendapersonale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void contatti(View v){

        Intent intent = new Intent(this, Contatti.class);
        startActivity(intent);

    }

    public void attivita(View v){

        Intent intent = new Intent(this, AttivitaInCorso.class);
        startActivity(intent);

    }
}
