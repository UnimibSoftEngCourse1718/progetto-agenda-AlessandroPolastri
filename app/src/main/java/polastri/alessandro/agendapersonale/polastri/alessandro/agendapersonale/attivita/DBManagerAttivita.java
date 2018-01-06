package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import static polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita.Attivita.CAMPO_PRIORITA;
import static polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita.Attivita.NOME;

class DBManagerAttivita {

    private static DBHelperAttivita dbHelperAttivita;

    DBManagerAttivita(Context context){

        dbHelperAttivita = new DBHelperAttivita(context);
    }

    void salvaAttivita(String oggetto, String inizio, String fine, String priorita){

        SQLiteDatabase db = dbHelperAttivita.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Attivita.CAMPO_OGGETTO, oggetto);
        cv.put(Attivita.CAMPO_INIZIO, inizio);
        cv.put(Attivita.CAMPO_FINE, fine);
        cv.put(Attivita.CAMPO_PRIORITA, priorita);

        try{

            db.insert(NOME, null, cv);
        } catch(SQLiteException sqle){

            throw(new UnsupportedOperationException());
        }
    }

    boolean cancellaAttivita(long id){

        SQLiteDatabase db = dbHelperAttivita.getWritableDatabase();

        try{

            return db.delete(NOME, Attivita.CAMPO_ID + "=?", new String[]{Long.toString(id)}) > 0;
        } catch(SQLiteException sqle){

            return false;
        }
    }

    Cursor query(){

        try{

            SQLiteDatabase db = dbHelperAttivita.getReadableDatabase();
            return db.query(NOME, null, null, null, null, null, null, null);
        } catch(SQLiteException sqle){

            return null;
        }
    }

    static Cursor getMin() {

        SQLiteDatabase db = dbHelperAttivita.getReadableDatabase();

        String selectQuery = "SELECT MIN(" +
                Attivita.CAMPO_PRIORITA + ")" +
                " AS MINIMO" +
                " FROM " + Attivita.NOME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        return cursor;
    }
}