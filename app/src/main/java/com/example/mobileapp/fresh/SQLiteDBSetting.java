package com.example.mobileapp.fresh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kaijiehuang on 15-4-8.
 */
public class SQLiteDBSetting extends SQLiteOpenHelper {
    final static int DB_VERSION = 1;
    final static String DB_NAME = "Setting";
    Context context;

    public SQLiteDBSetting(Context context) {
        super(context,DB_NAME, null,DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.query("Setting", new String[]{"function","state"},"function=?",
                    new String[]{"alertTime"}, null, null, null);
        }

        catch(Exception e){
            String CREATE_TABLE=" CREATE TABLE Setting (_id integer primary key autoincrement , function text , state text)";
            db.execSQL(CREATE_TABLE);
            db.execSQL("INSERT INTO Setting (function, state) values ('alertTime', '18:30')");

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
