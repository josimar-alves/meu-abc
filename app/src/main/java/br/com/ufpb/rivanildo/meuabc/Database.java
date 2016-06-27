package br.com.ufpb.rivanildo.meuabc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rivanildo on 26/04/16.
 */
public class Database extends SQLiteOpenHelper {

    private static String sql = "Create table paths (id integer primary key autoincrement, titulo text, path text)";
    private static String sql2 = "Create table uris (id integer primary key autoincrement, titulo text, uri text)";

    public Database(Context context) {
        super(context,"meuabc",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql); db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
