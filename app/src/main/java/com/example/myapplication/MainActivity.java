package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public  SQLiteDatabase db;
    public final String db_name = "project.db";
    public final String tb_name ="user";
    EditText accountcheck;
    EditText passwordcheck;
    public static String accountinformation;
    String passwordinformation;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
//        //刪除資料表
//        String deletetable = "DROP TABLE IF EXISTS todo";
//        db.execSQL(deletetable);

        Button nextPageBtn = (Button) findViewById(R.id.buttonlogin);
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accountcheck = (EditText) findViewById(R.id.editTextaccount);
                passwordcheck = (EditText) findViewById(R.id.editTextpassword);
                accountinformation = accountcheck.getText().toString();
                passwordinformation = passwordcheck.getText().toString();
                if(checkUserExist(accountinformation,passwordinformation)) {
                    //login=true;
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MainFunction.class);
                    startActivity(intent);
                }
            }
        });


        Button registerbu = (Button) findViewById(R.id.buttonregisiter);
        registerbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, register.class);
                startActivity(intent);
            }
        });


        Button forgotbu = (Button) findViewById(R.id.buttonforgotpassword);
        forgotbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, forgotpassword.class);
                startActivity(intent);


            }
        });
    }

    public boolean checkUserExist(String userid, String password) {
        String[] column = {"userid", "password"};
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        //db.execSQL(tb_name);
        String selection = "userid=? and password = ?";
        String[] selectionArgs = {userid, password};
        Cursor cursor = db.query(tb_name, column, selection, selectionArgs, null, null, null);
        //query(表名,欄位名稱,查詢條件,查詢條件的值);
        //利用條件抓出來的值來看有沒有數量有的話代表有這個user
        int count = cursor.getCount();

        cursor.close();
        close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }
    public void close(){
        if(db != null){
            db.close();
        }
    }
}