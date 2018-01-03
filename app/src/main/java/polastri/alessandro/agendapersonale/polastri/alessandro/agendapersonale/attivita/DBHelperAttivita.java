package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperAttivita extends SQLiteOpenHelper{

    private static final String NOMEDB = "DBATTIVITAINCORSO";

    DBHelperAttivita(Context context){

        super(context, NOMEDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String text = " TEXT,";
        String crea = "CREATE TABLE " + Attivita.NOME +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                Attivita.CAMPO_OGGETTO + text +
                Attivita.CAMPO_INIZIO + text +
                Attivita.CAMPO_FINE + text +
                Attivita.CAMPO_PRIORITA + " TEXT)";
        db.execSQL(crea);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        throw(new UnsupportedOperationException());
    }
}