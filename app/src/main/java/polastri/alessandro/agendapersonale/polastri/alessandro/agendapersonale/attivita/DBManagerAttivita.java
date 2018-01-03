package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBManagerAttivita {

    private static DBHelperAttivita dbHelper;

    DBManagerAttivita(Context context){

        dbHelper = new DBHelperAttivita(context);
    }

    void salvaAttivita(String oggetto, String inizio, String fine, String priorita){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Attivita.CAMPO_OGGETTO, oggetto);
        cv.put(Attivita.CAMPO_INIZIO, inizio);
        cv.put(Attivita.CAMPO_FINE, fine);
        cv.put(Attivita.CAMPO_PRIORITA, priorita);

        try{
            db.insert(Attivita.NOME_TABELLA, null, cv);
        } catch(SQLiteException sqle){
            throw(new UnsupportedOperationException());
        }
    }

    boolean cancellaAttivita(long id){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            return db.delete(Attivita.NOME_TABELLA, Attivita.CAMPO_ID + "=?", new String[]{Long.toString(id)}) > 0;
        } catch(SQLiteException sqle){

            return false;
        }
    }

    Cursor query(){

        try{

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return db.query(Attivita.NOME_TABELLA, null, null, null, null, null, null, null);
        } catch(SQLiteException sqle){

            return null;
        }
    }
}
