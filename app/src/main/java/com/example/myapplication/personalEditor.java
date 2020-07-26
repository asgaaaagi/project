package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.myapplication.R.id.button3;
import static com.example.myapplication.R.id.editTextemail2;
import static com.example.myapplication.R.id.editname;
import static com.example.myapplication.R.id.textView3;
import static java.lang.String.valueOf;

public class personalEditor extends AppCompatActivity {

    static final String db_name = "project.db";
    static final String table_name = "user";
    SQLiteDatabase db;
    Cursor cur;
    TextView id;
    EditText name,email;
    String useraccount = MainActivity.accountinformation;
    Button change;
    static String changedname;
    static String changedid;
    static String changedemail;
    int u_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_editor);

        name = (EditText)findViewById(editname);
        id = (TextView)findViewById(textView3);
        email = (EditText)findViewById(editTextemail2);
        change = (Button)findViewById(button3);

        name.setText(namequery(useraccount));
        id.setText(useraccount);
        email.setText(emailquery(useraccount));
        u_id = _idquery(useraccount);

        change.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedname = name.getText().toString();
                changedid = id.getText().toString();
                changedemail = email.getText().toString();
                updateData(u_id,changedname,changedemail);

                Intent intent = new Intent();
                intent.setClass(personalEditor.this , MainFunction.class);
                startActivity(intent);
            }
        });
    }

    //查詢E-mail
    private  String emailquery(String account){
        //開啟或新增資料庫
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        cur = db.rawQuery("SELECT Email FROM user where userid='"+account+"'", null);
        int rows_num = cur.getCount();//取得資料表列數
        if(rows_num != 0){
            cur.moveToFirst();   //將指標移至第一筆資料
            String thing =cur.getString(0);
            return thing;
        }
        else
            return "err";
    }

    //查詢名子
    private  String namequery(String account){
        //開啟或新增資料庫
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        cur = db.rawQuery("SELECT username FROM user where userid='"+account+"'", null);
        int rows_num = cur.getCount();//取得資料表列數
        if(rows_num != 0){
            cur.moveToFirst();   //將指標移至第一筆資料
            String thing = cur.getString(0);
            return thing;
        }
        else
            return "err";
    }

    //查詢_id
    private  int _idquery(String account){
        //開啟或新增資料庫
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        cur = db.rawQuery("SELECT _id FROM user where userid='"+account+"'", null);
        int rows_num = cur.getCount();//取得資料表列數
        if(rows_num != 0){
            cur.moveToFirst();   //將指標移至第一筆資料
            int thing = cur.getInt(0);
            return thing;
        }
        else
            return 0;
    }

    //更新資料的方法
    private void updateData(int _id,String name,String email){
        //開啟或新增資料庫
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        String update_text="UPDATE user SET username = '"+name+"',Email = '"+email+"' WHERE _id = " + _id;
        db.execSQL(update_text);
    }
}
