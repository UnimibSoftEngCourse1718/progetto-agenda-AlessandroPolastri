package polastri.alessandro.agendapersonale;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import polastri.alessandro.agendapersonale.Contatto;

public class DBHelperRubrica extends SQLiteOpenHelper{

    private static final String NOMEDB = "DBCONTATTI";

    public DBHelperRubrica(Context context){
        super(context, NOMEDB, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String a = "CREATE TABLE " + Contatto.NOME_TABELLA +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contatto.CAMPO_NOME + " TEXT," +
                Contatto.CAMPO_COGNOME + " TEXT," +
                Contatto.CAMPO_TELEFONO + " TEXT," +
                Contatto.CAMPO_EMAIL + " TEXT," +
                Contatto.CAMPO_TIPO + " TEXT)";
        db.execSQL(a);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
