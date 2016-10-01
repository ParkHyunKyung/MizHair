package com.cookandroid.charm_admin.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

/**
 * Created by HP on 2016-08-24.
 */
public class NoticeActivity extends Activity {
    Button btnModify,btnDelete;
    EditText notice_title,notice_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        setTitle("세부공지");

        btnModify = (Button)findViewById(R.id.btn_modify);
        btnDelete = (Button)findViewById(R.id.btn_delete);

        notice_title = (EditText)findViewById(R.id.notice_title);
        notice_content = (EditText)findViewById(R.id.notice_content);

        Intent intent = this.getIntent();
        final String title = intent.getStringExtra("Title");
        final String content = intent.getStringExtra("Content");
        final String num = intent.getStringExtra("Num");
        String add = intent.getStringExtra("Add");


        if(add.toString().equals("")){
            notice_title.setText(title);
            notice_content.setText(content);
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setClickable(true);
        }else {
            notice_title.setText("");
            notice_content.setText("");
            btnModify.setText(add);
            btnDelete.setVisibility(View.INVISIBLE);
            btnDelete.setClickable(false);
        }

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                modifyInServer(title,content,num);

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteInServer(title);

            }
        });
    }

    private void deleteInServer(String name) {

        String LoginServer = "http://118.36.3.200/notice.php?changeNum=1&NoticeTitle="+name;
        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();

            if (result.equals(Integer.toString(0))){
                Toast.makeText(getApplicationContext(),result.toString()+"성공",Toast.LENGTH_SHORT).show();
            }else if (result.equals(Integer.toString(1))){
                Toast.makeText(getApplicationContext(),result.toString()+"실패",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifyInServer(String name,String comment,String num) {

        String LoginServer = "http://118.36.3.200/changeNotice.php?changeNum=1&NoticeTitle="+name
                +"&NoticeComment="+comment+"&NoticeNum="+num;
        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();

            if (result.equals(Integer.toString(0))){
                Toast.makeText(getApplicationContext(),result.toString()+"성공",Toast.LENGTH_SHORT).show();
            }else if (result.equals(Integer.toString(1))){
                Toast.makeText(getApplicationContext(),result.toString()+"실패",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
