package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateDatabase  extends SQLiteOpenHelper {
    private final static  String db="project.db";
    private final static  int db_version=1;
    final String table_name1="user";
    final String table_name2="calendar";
    final String table_name3="map";
    final String table_name4="help";
    final String table_name5="barrierfree";
    public CreateDatabase(@Nullable Context context) {
        super(context, db, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  //創建table

        db.execSQL("CREATE TABLE IF NOT EXISTS user ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userid TEXT, " +
                "username TEXT, " +
                "Email TEXT DEFAULT '', " +
                "password TEXT  " +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS calendar ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userid TEXT, " +
                "messegeid INTEGER, " +
                "year INTEGER, " +
                "day INTEGER, " +
                "thing TEXT, " +
                "month INTEGER " +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS map ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userid TEXT, " +
                "longitude REAL,"+
                "latitude  REAL,"+
                "location TEXT, " +
                "userlocation TEXT " +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS help ( " +
                "event_id TNTEGER PRIMARY KEY AUTOINCREMENT , " +
                "messegeid INTEGER,"+
                "location TEXT ," +
                "month INTEGER, " +
                "day INTEGER, " +
                "location TEXT, " +
                "thing TEXT " +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS barrierfree ( " +
                "adviceid INTEGER, " +
                "longitude REAL,"+
                "latitude REAL, " +
                "thingname TEXT"+
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  //更新資料庫  如果版本更新用啟動並砍掉重建
        final String DROP_TABLE = "DROP TABLE IF EXISTS " + table_name1;
        db.execSQL(DROP_TABLE);
        final String DROP_TABLE2 = "DROP TABLE IF EXISTS " + table_name2;
        db.execSQL(DROP_TABLE);
        final String DROP_TABLE3 = "DROP TABLE IF EXISTS " + table_name3;
        db.execSQL(DROP_TABLE);
        final String DROP_TABLE4 = "DROP TABLE IF EXISTS " + table_name4;
        db.execSQL(DROP_TABLE);
        final String DROP_TABLE5 = "DROP TABLE IF EXISTS " + table_name5;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

}