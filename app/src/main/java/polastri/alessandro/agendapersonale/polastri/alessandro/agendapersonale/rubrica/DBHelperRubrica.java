package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.rubrica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperRubrica extends SQLiteOpenHelper{

    private static final String NOMEDB = "DBCONTATTI";

    DBHelperRubrica(Context context){

        super(context, NOMEDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String text = " TEXT,";
        String crea = "CREATE TABLE " + Contatto.NOME_TABELLA +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contatto.CAMPO_NOME + text +
                Contatto.CAMPO_COGNOME + text +
                Contatto.CAMPO_TELEFONO + text +
                Contatto.CAMPO_EMAIL + text +
                Contatto.CAMPO_TIPO + " TEXT)";
        db.execSQL(crea);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        throw(new UnsupportedOperationException());
    }
}