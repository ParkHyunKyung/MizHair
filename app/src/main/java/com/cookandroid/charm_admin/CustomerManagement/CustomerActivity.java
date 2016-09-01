package com.cookandroid.charm_admin.CustomerManagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;

/**
 * Created by HP on 2016-08-19.
 */
public class CustomerActivity extends Activity{
    Button btnModify;
    EditText edtName,edtPhoneNum,edtMileage,edtDate,edtMemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        btnModify = (Button)findViewById(R.id.btnModiify);

        edtName = (EditText)findViewById(R.id.edtName);
        edtPhoneNum = (EditText)findViewById(R.id.edtPhoneNum);
        edtMileage = (EditText)findViewById(R.id.edtMileage);
        edtDate = (EditText)findViewById(R.id.edtDate);
        edtMemo = (EditText)findViewById(R.id.edtMemo);

        Intent intent = this.getIntent();
        String name = intent.getStringExtra("name");
        String phoneNum = intent.getStringExtra("phoneNum");
        String date = intent.getStringExtra("date");

        edtName.setText(name);
        edtPhoneNum.setText(phoneNum);
        edtDate.setText(date);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"수정되었습니다.",Toast.LENGTH_SHORT).show();

                /*여기서 서버로 수정된 내용 전송*/
            }
        });
    }
}
