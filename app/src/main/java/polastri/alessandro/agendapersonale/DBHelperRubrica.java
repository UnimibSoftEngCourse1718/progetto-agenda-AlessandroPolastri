package polastri.alessandro.agendapersonale;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperRubrica extends SQLiteOpenHelper{

    private static final String NOMEDB = "DBCONTATTI";

    DBHelperRubrica(Context context){
        super(context, NOMEDB, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {

        String b = " TEXT,";
        String a = "CREATE TABLE " + Contatto.NOME_TABELLA +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contatto.CAMPO_NOME + b +
                Contatto.CAMPO_COGNOME + b +
                Contatto.CAMPO_TELEFONO + b +
                Contatto.CAMPO_EMAIL + b +
                Contatto.CAMPO_TIPO + " TEXT)";
        db.execSQL(a);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw(new UnsupportedOperationException());
    }
}
