package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.impegni;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import static polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.impegni.Impegno.NOME_TABELLA;

class DBManagerImpegno {

    private static DBHelperImpegno dbHelperImpegno;

    DBManagerImpegno(Context context){

        dbHelperImpegno = new DBHelperImpegno(context);
    }

    void salvaImpegno(String oggetto, String data, String ora_inizio, String ora_finale, String ripetizione, String allarme, String note, String tipo){

        SQLiteDatabase db = dbHelperImpegno.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Impegno.CAMPO_OGGETTO, oggetto);
        cv.put(Impegno.CAMPO_DATA, data);
        cv.put(Impegno.CAMPO_ORA_INIZIO, ora_inizio);
        cv.put(Impegno.CAMPO_ORA_FINALE, ora_finale);
        cv.put(Impegno.CAMPO_RIPETIZIONE, ripetizione);
        cv.put(Impegno.CAMPO_ALLARME, allarme);
        cv.put(Impegno.CAMPO_NOTE, note);
        cv.put(Impegno.CAMPO_TIPO, tipo);

        try{

            db.insert(NOME_TABELLA, null, cv);
        } catch(SQLiteException sqle){

            throw(new UnsupportedOperationException());
        }
    }

    boolean cancellaImpegno(long id){

        SQLiteDatabase db = dbHelperImpegno.getWritableDatabase();

        try{

            return db.delete(NOME_TABELLA, Impegno.CAMPO_ID + "=?", new String[]{Long.toString(id)}) > 0;
        } catch(SQLiteException sqle){

            return false;
        }
    }

    Cursor query(){

        try{

            SQLiteDatabase db = dbHelperImpegno.getReadableDatabase();
            return db.query(NOME_TABELLA, null, null, null, null, null, null, null);
        } catch(SQLiteException sqle){

            return null;
        }
    }

    static Cursor cercaTipo(String ricerca) {

        SQLiteDatabase db = dbHelperImpegno.getReadableDatabase();

        String selectQuery =  "SELECT  rowid as " +
                Impegno.CAMPO_ID + "," +
                Impegno.CAMPO_OGGETTO + "," +
                Impegno.CAMPO_DATA + "," +
                Impegno.CAMPO_ORA_INIZIO + "," +
                Impegno.CAMPO_ORA_FINALE + "," +
                Impegno.CAMPO_RIPETIZIONE + "," +
                Impegno.CAMPO_ALLARME + "," +
                Impegno.CAMPO_NOTE + "," +
                Impegno.CAMPO_TIPO +
                " FROM " + Impegno.NOME_TABELLA +
                " WHERE " +  Impegno.CAMPO_TIPO + "  LIKE  '%" + ricerca + "%' "
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {

            cursor.close();
            return null;
        }

        return cursor;
    }
}
