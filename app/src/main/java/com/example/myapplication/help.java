package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class help extends AppCompatActivity {
   private    ListView listView;
    private ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        listView=(ListView)findViewById(R.id.list_view);
        ArrayList<String> arr=new ArrayList<>();
        arr.add("一");
        arr.add("二");
        arr.add("三");
        arr.add("四");
        arr.add("五");
        arr.add("六");
        arr.add("七");
        arr.add("八");
        arr.add("九");
        arr.add("十");
        arr.add("十一");
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        listView.setAdapter(arrayAdapter);
    }
}
