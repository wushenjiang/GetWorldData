package com.androidlearing.getworlddata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button bt_send;
    private EditText et_content;
    private static final int TEST_USER_SELECT = 1;
    private String content;
    private Spinner conditionSpinner;
    private String condition;
    private ListView lv;
    String[] strs = new String[]{};
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){

            switch(msg.what){
                case TEST_USER_SELECT:
                    String s = (String)msg.obj;
                    //System.out.println("***********");
                   // System.out.println("***********");
                    //System.out.println("data:"+s);
                    strs = s.split("  ");
                    lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,strs));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_send = findViewById(R.id.bt_send);
        et_content = findViewById(R.id.et_content);
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString();
            }
        });
        conditionSpinner = findViewById(R.id.condition);
        final String [] data = {"国家","时间"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,data);
        conditionSpinner.setAdapter(adapter);
        conditionSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //取得选中的值
                condition = data[position];
                //设置显示当前选择的项
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        System.out.println(condition);
        lv = findViewById(R.id.lv);

    }
    @Override
    protected void onStart(){
        super.onStart();
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行查询操作
                //连接数据库进行操作需要在主线程操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //调用数据库帮助类中的方法取数据
                        List<WorldData> list = DBOpenHelper.searchDataByCountry(condition,content);
                        Message message = mHandler.obtainMessage();
                        String s = "";
                        for(int i=0;i<list.size();i++){
                            s += "时间:"+list.get(i).getLastUpdateTime()+"  ";
                            s += list.get(i).getCountryname()+"确诊人数为:  ";
                            s += list.get(i).getConfirmed()+"  ";
                        }
                        message.what = TEST_USER_SELECT;
                        message.obj = s;
                        mHandler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}
