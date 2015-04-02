package com.example.mobileapp.fresh;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haoxiang on 2015/4/1.
 */
public class SQLiteDB extends SQLiteOpenHelper {
    final static int DB_VERSION = 1;
    final static String DB_NAME = "QualityPeriod";
    Context context;

    public SQLiteDB(Context context) {
        super(context,DB_NAME, null,DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        try {
            db.query("QualityPeriod", new String[]{"name","day","times"},"name=?",
                    new String[]{"apple"}, null, null, null);
        }

        catch(Exception e){
            String CREATE_TABLE=" CREATE TABLE QualityPeriod (_id integer primary key autoincrement , name text , day text, times integer)";
            db.execSQL(CREATE_TABLE);
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('apple', '10', '-5')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('beans', '8', '-2')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('bellpepper', '10', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('bread', '10', '-6')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('broccoli', '10', '0')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('cabbage', '7', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('carrot', '10', '-2')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('cauliflower', '8', '-3')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('celery', '6', '-2')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('chillies', '8', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('cookies', '5', '-3')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('corn', '10', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('cucumber', '8', '1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('cupcake', '7', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('egg', '8', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('kiwi', '7', '0')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('lettuce', '8', '1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('milk', '5', '-6')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('mushroom', '7', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('onion', '9', '-4')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('pear', '10', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('potatoes', '8', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('pumpkin', '11', '-1')");
            db.execSQL("INSERT INTO QualityPeriod (name, day, times) values ('tomato', '8', '-3')");
        }

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
