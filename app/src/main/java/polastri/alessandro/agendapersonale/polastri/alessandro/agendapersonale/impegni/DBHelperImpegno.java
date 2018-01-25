package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.impegni;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperImpegno extends SQLiteOpenHelper{

    private static final String NOMEDB = "DBIMPEGNI";

    DBHelperImpegno(Context context) {

        super(context, NOMEDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String text = " TEXT,";
        String crea = "CREATE TABLE " + Impegno.NOME_TABELLA +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                Impegno.CAMPO_OGGETTO + text +
                Impegno.CAMPO_DATA + text +
                Impegno.CAMPO_ORA_INIZIO + text +
                Impegno.CAMPO_ORA_FINALE + text +
                Impegno.CAMPO_RIPETIZIONE + text +
                Impegno.CAMPO_ALLARME + text +
                Impegno.CAMPO_NOTE + text +
                Impegno.CAMPO_TIPO + " TEXT)";
        db.execSQL(crea);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        throw(new UnsupportedOperationException());
    }
}