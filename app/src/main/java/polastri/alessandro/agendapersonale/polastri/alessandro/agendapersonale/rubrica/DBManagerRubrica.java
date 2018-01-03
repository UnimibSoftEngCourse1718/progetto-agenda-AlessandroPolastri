package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.rubrica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

class DBManagerRubrica {

    private static DBHelperRubrica dbHelper;

    DBManagerRubrica(Context context){

        dbHelper = new DBHelperRubrica(context);
    }

    void salvaContatto(String nome, String cognome, String telefono, String email, String tipo){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Contatto.CAMPO_NOME, nome);
        cv.put(Contatto.CAMPO_COGNOME, cognome);
        cv.put(Contatto.CAMPO_TELEFONO, telefono);
        cv.put(Contatto.CAMPO_EMAIL, email);
        cv.put(Contatto.CAMPO_TIPO, tipo);

        try{

            db.insert(Contatto.NOME_TABELLA, null, cv);
        } catch(SQLiteException sqle){

            throw(new UnsupportedOperationException());
        }
    }

    boolean cancellaContatto(long id){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{

            return db.delete(Contatto.NOME_TABELLA, Contatto.CAMPO_ID + "=?", new String[]{Long.toString(id)}) > 0;
        } catch(SQLiteException sqle){

            return false;
        }
    }

    Cursor query(){

        try{

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return db.query(Contatto.NOME_TABELLA, null, null, null, null, null, null, null);
        } catch(SQLiteException sqle){

            return null;
        }
    }

    static Cursor cercaTipo(String ricerca) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  rowid as " +
                Contatto.CAMPO_ID + "," +
                Contatto.CAMPO_NOME + "," +
                Contatto.CAMPO_COGNOME + "," +
                Contatto.CAMPO_TELEFONO + "," +
                Contatto.CAMPO_EMAIL + "," +
                Contatto.CAMPO_TIPO +
                " FROM " + Contatto.NOME_TABELLA +
                " WHERE " +  Contatto.CAMPO_TIPO + "  LIKE  '%" + ricerca + "%' "
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
