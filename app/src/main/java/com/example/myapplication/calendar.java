package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import static java.lang.String.valueOf;
public class calendar extends AppCompatActivity {
    static final String db_name = "project.db";
    static final String table_name = "todo";
    SQLiteDatabase db;
    Cursor cur;
    TextView text;
    CalendarView cv;
    EditText todolist;
    Button add;
    static int Myear,Mmonth,Mday;
    String useraccount = MainActivity.accountinformation;
    String thing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);
        text = (TextView)findViewById(R.id.textView11);//顯示今天日期或選擇的日期
        cv = (CalendarView)findViewById(R.id.calendarView);
        todolist = (EditText)findViewById(R.id.todolist);//顯示或改變當日日程
        add = (Button) findViewById(R.id.add);//新增鍵
        //開啟或新增資料庫
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        //刪除資料表
       // String deletetable = "DROP TABLE IF EXISTS " +table_name;
      //  db.execSQL(deletetable);
        //建立資料表
        String createTable = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(todo_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userid TEXT, " +
                "year int, " +
                "month int, " +
                "day int, " +
                "thing VARCHAR(50))";
        db.execSQL(createTable);

        //textview直接出現當天日期
        text.setText(new StringBuilder().append(Calendar.getInstance().get(Calendar.YEAR)).append("-").
                append(Calendar.getInstance().getTime().getMonth()+1).append("-").append(Calendar.getInstance().getTime().getDate()));

        //點擊變換textview日期
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                Myear = year;
                Mmonth = month;
                Mday = dayOfMonth;

                text.setText(new StringBuilder().append(Myear).append("-").
                        append(Mmonth+1).append("-").append(Mday));
                if (query(useraccount,Myear,Mmonth+1,Mday) == "nothing")
                    todolist.setText("");
                else
                    todolist.setText(query(useraccount,Myear,Mmonth+1,Mday));
            }
        });

        //點擊新增資料進資料庫
        add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(useraccount,
                        Myear,
                        Mmonth+1,
                        Mday,
                        valueOf(todolist.getText()));
            }
        });

    }
    //新增資料的方法
    private void addData(String account,int year, int month, int day, String thing){
        ContentValues cv = new ContentValues(5);

        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        cur = db.rawQuery("SELECT todo_id FROM todo where userid='"+account+"' AND year='"+year+"' AND month='"+month+"' AND day='"+day+"'" , null);
        int rows_num = cur.getCount();//取得資料表列數
        if (rows_num != 0)//如果此使用者這天已寫進資料庫過了，就只改備忘錄的部分
            updateData(account,year,month,day,thing);
        else//如果沒有就新增帳號、年月日、備忘錄內容進去資料庫
            cv.put("userid",account);
            cv.put("year",year);
            cv.put("month",month);
            cv.put("day",day);
            cv.put("thing",thing);

        db.insert(table_name,null,cv);
    }
    //查詢資料
    private  String query(String account, int year, int month, int day){
        cur = db.rawQuery("SELECT thing FROM " + table_name + " WHERE userid = '"+account+"' AND year = " + year + " AND month = " + month +" AND day = "+day, null);
        int rows_num = cur.getCount();//取得資料表列數
        if(rows_num != 0){
            cur.moveToFirst();   //將指標移至第一筆資料
            thing =cur.getString(0);
            return thing;
        }
        else
            return "nothing";
    }

    //更新資料的方法
    private void updateData(String account,int year, int month, int day, String thing){
        //開啟或新增資料庫
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        String update_text="UPDATE todo SET thing = '"+thing+"' WHERE userid = '"+account+"' AND year = "+ year + " AND month = " + month + " AND day = " + day;
        db.execSQL(update_text);
    }
    }


