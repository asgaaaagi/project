package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainFunction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_function);
        ImageButton buttonmap=(ImageButton)findViewById(R.id.imageButtonmap);
        buttonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainFunction.this , MapsActivity.class);
                startActivity(intent);
            }
        });
        ImageButton buttoncal=(ImageButton)findViewById(R.id.imageButtoncalendar);
        buttoncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainFunction.this , calendar.class);
                startActivity(intent);
            }
        });
        ImageButton buttonhelp=(ImageButton)findViewById(R.id.imageButtonhelp);
        buttonhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainFunction.this , help.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        // 取得點選項目的id
        int id = item.getItemId();

        // 依照id判斷點了哪個項目並做相應事件
        if (id == R.id.action_settings) {
                    Intent intent = new Intent();
                    intent.setClass(MainFunction.this , personalEditor.class);
                    startActivity(intent);
        }else if(id==R.id.addfriend){
            Intent intent = new Intent();
            intent.setClass(MainFunction.this , addNewFriend.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
