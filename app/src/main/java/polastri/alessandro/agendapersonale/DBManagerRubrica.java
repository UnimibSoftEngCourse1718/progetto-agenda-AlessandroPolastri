package polastri.alessandro.agendapersonale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBManagerRubrica {

    private DBHelperRubrica dbHelper;

    public DBManagerRubrica(Context context){

        dbHelper = new DBHelperRubrica(context);
    }

    public void save(String nome, String cognome, String telefono, String email, String tipo){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contatto.CAMPO_NOME, nome);
        cv.put(Contatto.CAMPO_COGNOME, cognome);
        cv.put(Contatto.CAMPO_TELEFONO, telefono);
        cv.put(Contatto.CAMPO_EMAIL, email);
        cv.put(Contatto.CAMPO_TIPO, tipo);

        try{
            db.insert(Contatto.NOME_TABELLA, null, cv);
        } catch(SQLiteException sqle){}
    }

    public boolean delete(long id){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            if(db.delete(Contatto.NOME_TABELLA, Contatto.CAMPO_ID + "=?", new String[]{Long.toString(id)})>0)
                return true;
            return false;
        } catch(SQLiteException sqle){
            return false;
        }
    }

    public Cursor query(){

        Cursor crs;

        try{

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            crs = db.query(Contatto.NOME_TABELLA, null, null, null, null, null, null, null);
        } catch(SQLiteException sqle){
            return null;
        }
        return crs;
    }
}
