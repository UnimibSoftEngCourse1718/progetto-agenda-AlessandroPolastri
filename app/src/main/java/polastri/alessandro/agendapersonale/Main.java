package polastri.alessandro.agendapersonale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void attivita(View v){

        Intent intent = new Intent(this, Impegni.class);
        startActivity(intent);
    }

    public void rubrica(View v){

        Intent intent = new Intent(this, Rubrica.class);
        startActivity(intent);
    }
}