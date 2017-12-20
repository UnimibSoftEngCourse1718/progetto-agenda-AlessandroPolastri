package polastri.alessandro.agendapersonale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class In_Corso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in__corso);
    }

    public void cambia(View v){
        Button b = (Button) findViewById(R.id.add);

        Intent successiva = new Intent(In_Corso.this,Crea_Attivita.class);
        startActivity(successiva);
    }

    @Override
    public void onResume(){
        super.onResume();

        Attivita a = new Attivita("casa", "cosa", "male");
        TextView titolo = (TextView) findViewById(R.id.testo);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            a = (Attivita) extras.getParcelable("object_key");
        }

        titolo.setText(a.toString());

    }
}
