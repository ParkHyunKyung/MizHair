package com.cookandroid.charm_admin.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.cookandroid.charm_admin.R;

/**
 * Created by HP on 2016-08-24.
 */
public class NoticeActivity extends Activity {
    Button btnModify;
    EditText notice_title,notice_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        setTitle("세부공지");

        btnModify = (Button)findViewById(R.id.btn_modify);

        notice_title = (EditText)findViewById(R.id.notice_title);
        notice_content = (EditText)findViewById(R.id.notice_content);

        Intent intent = this.getIntent();
        String title = intent.getStringExtra("Title");
        String content = intent.getStringExtra("Content");
        String add = intent.getStringExtra("Add");

        if(add.equals("")){
            notice_title.setText(title);
            notice_content.setText(content);
        }else {
            btnModify.setText(add);
        }

    }
}
