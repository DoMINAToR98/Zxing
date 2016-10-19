package in.hackveda.zxing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kartiksharma on 20/10/16.
 */
public class MyDatabase {
    public static final String DB_name = "Scan.db";
    public static final int DB_ver = 2;
    public static final String DB_table = "DB";
    public static final String Codes = "Codes";
    public static final String Q_create =
            "CREATE TABLE " + DB_table + "(" + Codes + " TEXT)";
    Context context;
    SQLiteDatabase database;

    public MyDatabase(Context c) {
        context = c;
    }

    public MyDatabase open() {
        DBHelper dbh = new DBHelper(context);
        database = dbh.getWritableDatabase();
        return this;


    }
    public void write(String task) {
        ContentValues cv=new ContentValues();
        cv.put(Codes,task);
        database.insert(DB_table,null,cv);

    }
    public void close() {
        database.close();

    }
    public String read(){
        String result="";
        String[] colms={Codes};
        Cursor cur=database.query(DB_table,colms,null,null,null,null,null);
        int iCode=cur.getColumnIndex(Codes);
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
            result = result+ cur.getString(iCode)+"\n";

        }

        return result;
    }


    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_name, null, DB_ver);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(Q_create);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
