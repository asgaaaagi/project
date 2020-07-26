package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class register extends AppCompatActivity {
    static final String db_name = "project.db";
    static final String table_name="user";
    EditText account;
    EditText password;
    EditText email;
    String name=null;
    String accountinformation;
    String passwordinformation;
    String emailinformation;
    String[] column={"username","password"};
   ImageButton register;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=(ImageButton)findViewById(R.id.imageButtonregister);
       db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
       db.execSQL("CREATE TABLE IF NOT EXISTS user ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userid TEXT, " +
                "username TEXT, " +
                "Email TEXT DEFAULT '', " +
                "password TEXT  " +
                ");");  //創建表，如果創建了就不在執行*/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account=(EditText)findViewById(R.id.todolist2);
                password=(EditText)findViewById(R.id.todolist3);
                email=(EditText)findViewById(R.id.todolist4);
                accountinformation=account.getText().toString();
                passwordinformation=account.getText().toString();

                emailinformation=email.getText().toString();  //取得帳號密碼信箱的值並轉成字串
                Cursor c = db.query(table_name,column,null,null,null,
                        null,null);
            if(c.getCount()!=0) { //如果資料表裡面沒有資料則執行else來創建第一筆資料
                back: if (c.moveToFirst()) { // 移到第 1 筆資料 (若有資料才繼續)
                    do {
                        String accountdata = c.getString(0);
                        String passworddata = c.getString(1);
                        if (accountinformation.equals(accountdata) ||
                                passwordinformation.equals(passworddata)) {
                            //如果帳號密碼相同則印出訊息並重新輸入
                            Toast.makeText(register.this, "帳號或密碼已有註冊請更改", Toast.LENGTH_LONG).show();
                        } else {
                            addData(accountinformation, name, passwordinformation, emailinformation);
                            break back;
                        }
                    } while (c.moveToNext());
                }
            }else{
                addData(accountinformation,name,passwordinformation,emailinformation);
            }
                c.close();
                Intent intent = new Intent();
                intent.setClass(register.this , MainActivity.class);
                startActivity(intent);

            }
        });


        }
    private void addData(String account, String name,String password, String email){
        ContentValues cv = new ContentValues();
        cv.put("userid",account);
        cv.put("username",name);
        cv.put("Email",email);
        cv.put("password",password);
        db.insert(table_name,null,cv);
    }
    public void onResume() {

        super.onResume();
    }
}
