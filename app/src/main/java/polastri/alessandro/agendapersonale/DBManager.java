package polastri.alessandro.agendapersonale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

public class DBManager {

    private DBHelper dbHelper;

    public DBManager(Context context){

        dbHelper = new DBHelper(context);
    }

    public void save(String oggetto, String data, String orario, String note, String allarme){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Attivita.CAMPO_OGGETTO, oggetto);
        cv.put(Attivita.CAMPO_DATA, data);
        cv.put(Attivita.CAMPO_ORARIO, orario);
        cv.put(Attivita.CAMPO_NOTE, note);
        cv.put(Attivita.CAMPO_ALLARME, allarme);

        try{
            db.insert(Attivita.NOME_TABELLA, null, cv);
        } catch(SQLiteException sqle){}
    }

    public boolean delete(long id){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            if(db.delete(Attivita.NOME_TABELLA, Attivita.CAMPO_ID + "=?", new String[]{Long.toString(id)})>0)
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

            crs = db.query(Attivita.NOME_TABELLA, null, null, null, null, null, null, null);
        } catch(SQLiteException sqle){
            return null;
            }
        return crs;
    }
}