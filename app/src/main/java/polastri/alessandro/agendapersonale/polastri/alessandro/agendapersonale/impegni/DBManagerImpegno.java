package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.impegni;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import static polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.impegni.Impegno.NOME_TABELLA;

class DBManagerImpegno {

    private static DBHelperImpegno dbHelperImpegno;
    private static final String WHERE = " WHERE ";
    private static final String SELECT = "SELECT * FROM ";
    private static final String EQUAL_AND = " = ? AND ";
    private static final String GREATER_AND = " > ? AND ";
    private static final String MINOR_AND = " < ? AND ";

    DBManagerImpegno(Context context){

        dbHelperImpegno = new DBHelperImpegno(context);
    }

    void salvaImpegno(String oggetto, String data, String oraInizio, String oraFinale, String ripetizione, String allarme, String note, String tipo){

        SQLiteDatabase db = dbHelperImpegno.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Impegno.CAMPO_OGGETTO, oggetto);
        cv.put(Impegno.CAMPO_DATA, data);
        cv.put(Impegno.CAMPO_ORA_INIZIO, oraInizio);
        cv.put(Impegno.CAMPO_ORA_FINALE, oraFinale);
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

            return db.delete(NOME_TABELLA, Impegno.CAMPO_ID + " = ?", new String[]{Long.toString(id)}) > 0;
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

    static Cursor cerca(String ricerca) {

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
                WHERE +  Impegno.CAMPO_TIPO + "  LIKE  '%" + ricerca + "%' OR " +
                Impegno.CAMPO_DATA + "  LIKE  '%" + ricerca + "%' "
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

    static boolean controllaImpegni(String data, String oraIniziale, String oraFinale) {

        SQLiteDatabase db = dbHelperImpegno.getReadableDatabase();

        String select = SELECT + Impegno.NOME_TABELLA + WHERE + Impegno.CAMPO_DATA + EQUAL_AND + Impegno.CAMPO_ORA_FINALE + " = ? OR " +
                Impegno.CAMPO_DATA + EQUAL_AND + Impegno.CAMPO_ORA_INIZIO + " = ?";

        Cursor cursor = db.rawQuery(select, new String[]{data, oraIniziale, data, oraFinale});

        if (cursor == null) {

            return true;
        } else if (!cursor.moveToFirst()) {

            cursor.close();
            return true;
        }

        return false;
    }

    static boolean controllaImpegni2(String data, String oraIniziale, String oraFinale) {

        SQLiteDatabase db = dbHelperImpegno.getReadableDatabase();

        String select = SELECT + Impegno.NOME_TABELLA + WHERE + Impegno.CAMPO_DATA + EQUAL_AND + Impegno.CAMPO_ORA_FINALE + GREATER_AND +
                Impegno.CAMPO_ORA_INIZIO + " < ? OR " + Impegno.CAMPO_DATA + EQUAL_AND + Impegno.CAMPO_ORA_INIZIO + GREATER_AND +
                Impegno.CAMPO_ORA_FINALE + " < ?";

        Cursor cursor = db.rawQuery(select, new String[]{data, oraFinale, oraIniziale, data, oraIniziale, oraFinale});

        if (cursor == null) {

            return true;
        } else if (!cursor.moveToFirst()) {

            cursor.close();
            return true;
        }

        return false;
    }

    static boolean controllaImpegni3(String data, String oraIniziale, String oraFinale) {

        SQLiteDatabase db = dbHelperImpegno.getReadableDatabase();

        String select = SELECT + Impegno.NOME_TABELLA + WHERE + Impegno.CAMPO_DATA + EQUAL_AND + Impegno.CAMPO_ORA_INIZIO + GREATER_AND +
                Impegno.CAMPO_ORA_INIZIO + MINOR_AND + Impegno.CAMPO_ORA_FINALE + GREATER_AND + Impegno.CAMPO_ORA_FINALE + " > ? OR " +
                Impegno.CAMPO_DATA + EQUAL_AND + Impegno.CAMPO_ORA_INIZIO + MINOR_AND + Impegno.CAMPO_ORA_INIZIO + MINOR_AND +
                Impegno.CAMPO_ORA_FINALE + GREATER_AND + Impegno.CAMPO_ORA_FINALE + " < ?";

        Cursor cursor = db.rawQuery(select, new String[]{data, oraIniziale, oraFinale, oraIniziale, oraFinale, data, oraIniziale, oraFinale, oraIniziale, oraFinale});

        if (cursor == null) {

            return true;
        } else if (!cursor.moveToFirst()) {

            cursor.close();
            return true;
        }

        return false;
    }
}