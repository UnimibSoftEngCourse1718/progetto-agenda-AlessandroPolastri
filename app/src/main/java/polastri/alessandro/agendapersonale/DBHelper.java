package polastri.alessandro.agendapersonale;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String NOMEDB = "DBATTIVITA";

    public DBHelper(Context context){
        super(context, NOMEDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String a = "CREATE TABLE " + Attivita.NOME_TABELLA +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                Attivita.CAMPO_OGGETTO + " TEXT," +
                Attivita.CAMPO_DATA + " TEXT," +
                Attivita.CAMPO_ORARIO + " TEXT," +
                Attivita.CAMPO_NOTE + " TEXT," +
                Attivita.CAMPO_ALLARME + " TEXT)";
        db.execSQL(a);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
