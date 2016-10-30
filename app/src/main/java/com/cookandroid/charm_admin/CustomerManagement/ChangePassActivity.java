package com.cookandroid.charm_admin.CustomerManagement;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONObject;

/**
 * Created by HP on 2016-10-15.
 */
public class ChangePassActivity extends Activity {
    EditText edtPassword,edtPasswordCheck;
    Button btnModiify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_changepass);

        edtPassword = (EditText)findViewById(R.id.changePass_edtPassword);
        edtPasswordCheck = (EditText)findViewById(R.id.changePass_edtPasswordCheck);
        btnModiify = (Button)findViewById(R.id.changePass_btnModiify);

        final String UserNum = getIntent().getStringExtra("UserNum");

        btnModiify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(edtPasswordCheck.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if (!edtPasswordCheck.getText().toString().equals(edtPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "비밀번호를 체크해주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    connection(UserNum,edtPassword.getText().toString().trim());
                }
            }
        });
    }

    private void connection(String UserNum,String UserPass) {
        String LoginServer = "http://mizhair.ga/passChange.php?";
        LoginServer += "UserNum=";
        LoginServer += UserNum;
        LoginServer += "&UserPass=";
        LoginServer += UserPass;

        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();
            JSONObject state = new JSONObject(result);

            if (state.getString("STATE").equals("1")) {
                //Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                return;
            } else if (state.getString("STATE").equals("0")) {
                //Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
