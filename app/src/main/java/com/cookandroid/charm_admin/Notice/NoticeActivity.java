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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

        btnModify = (Button)findViewById(R.id.notice_btnNotify);
        btnDelete = (Button)findViewById(R.id.notice_btnDelete);

        notice_title = (EditText)findViewById(R.id.notice_title);
        notice_content = (EditText)findViewById(R.id.notice_content);

        Intent intent = this.getIntent();
        final String title = intent.getStringExtra("Title");
        final String content = intent.getStringExtra("Content");
        final String num = intent.getStringExtra("Num");
        String add = intent.getStringExtra("Add");

        //Toast.makeText(getApplicationContext(),title.toString()+content.toString()+num.toString(),Toast.LENGTH_SHORT).show();


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

                if(btnModify.getText().toString().equals("수정")){
                    Toast.makeText(getApplicationContext(),"수정",Toast.LENGTH_SHORT).show();
                    modifyInServer(notice_title.getText().toString(),notice_content.getText().toString(),num);
                }else if(btnModify.getText().toString().equals("저장")){
                    Toast.makeText(getApplicationContext(),"저장",Toast.LENGTH_SHORT).show();
                    insertInServer(notice_title.getText().toString(),notice_content.getText().toString());
                }
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteInServer(num);
                finish();

            }
        });
    }

    private void deleteInServer(String num) {
        try {
            num = URLEncoder.encode(num, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String LoginServer = "http://118.36.3.200/changeNotice.php?NoticeChange=1&NoticeNum="+num;
        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();

                Toast.makeText(getApplicationContext(),"3"+result.toString(),Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifyInServer(String title,String comment,String num) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
            comment = URLEncoder.encode(comment, "UTF-8");
            num = URLEncoder.encode(num, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String LoginServer = "http://118.36.3.200/changeNotice.php?NoticeChange=2&NoticeNum="+num+"&NoticeTitle="+title+"&NoticeComment="+comment;
        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();
            Toast.makeText(getApplicationContext(),"1"+result.toString(),Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertInServer(String title,String comment) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
            comment = URLEncoder.encode(comment, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String LoginServer = "http://118.36.3.200/changeNotice.php?NoticeChange=0&NoticeTitle="+title+"&NoticeComment="+comment;

        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();

            Toast.makeText(getApplicationContext(),"6"+result.toString(),Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}