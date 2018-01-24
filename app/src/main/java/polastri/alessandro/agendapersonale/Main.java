package polastri.alessandro.agendapersonale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita.AttivitaInCorso;
import polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.impegni.ImpegniInCorso;
import polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.rubrica.Rubrica;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void apriRubrica(View v){

        Intent intent;
        intent = new Intent(this, Rubrica.class);
        startActivity(intent);
    }

    public void apriAttivita(View v){

        Intent intent;
        intent = new Intent(this, AttivitaInCorso.class);
        startActivity(intent);
    }

    public void apriImpegni(View v){

        Intent intent;
        intent = new Intent(this, ImpegniInCorso.class);
        startActivity(intent);
    }
}