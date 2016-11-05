package com.cookandroid.charm_admin.CustomerManagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONObject;

/**
 * Created by HP on 2016-08-19.
 */
public class CustomerActivity extends Activity{
    Button btnModify,btnChangePass;
    EditText edtName,edtPhoneNum,edtMileage,edtID,edtMemo;
    private RadioGroup rgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        btnModify = (Button)findViewById(R.id.customer_btnModiify);
        btnChangePass = (Button)findViewById(R.id.btn_changePass);
        rgGender = (RadioGroup) findViewById(R.id.rg_Gender);
        edtName = (EditText)findViewById(R.id.edtName);
        edtPhoneNum = (EditText)findViewById(R.id.edtPhoneNum);
        edtMileage = (EditText)findViewById(R.id.edtMileage);
        edtID = (EditText)findViewById(R.id.edtID);
        edtMemo = (EditText)findViewById(R.id.edtMemo);

        Intent intent = this.getIntent();

        final String UserName = intent.getStringExtra("UserName");
        final String UserPhone = intent.getStringExtra("UserPhone");
        final String UserId = intent.getStringExtra("UserId");
        final String UserGender = intent.getStringExtra("UserGender");
        final String UserNum = intent.getStringExtra("UserNum");

        int genderNum = Integer.parseInt(UserGender);

        int id = rgGender.getCheckedRadioButtonId();
        RadioButton radioMan = (RadioButton)findViewById(R.id.radio_btnMan);
        RadioButton radioWoman = (RadioButton)findViewById(R.id.radio_btnWoman);
        if(UserGender.equals("1")){
            radioMan.setChecked(true);
        }else if(UserGender.equals("0")){
            radioWoman.setChecked(true);
        }else{

        }
        edtName.setText(UserName);
        edtPhoneNum.setText(UserPhone);
        edtID.setText(UserId);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChangePass = new Intent(getApplicationContext(), ChangePassActivity.class);
                intentChangePass.putExtra("UserNum",UserNum);
                startActivity(intentChangePass);
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = rgGender.getCheckedRadioButtonId();
                int genderNum = 0;
                RadioButton radioButton = (RadioButton)findViewById(id);
                if(radioButton.getText().toString().trim().equals("여성")){
                    genderNum = 0;
                }else if(radioButton.getText().toString().trim().equals("남성")){
                    genderNum = 1;
                }else{

                }
                connection(UserNum,edtName.getText().toString(),edtPhoneNum.getText().toString(),Integer.toString(genderNum));
            }
        });
    }

    private void connection(String UserNum,String UserName,String UserPhone,String UserGender) {
        String LoginServer = "http://mizhair.ga/updateUser.php?";
        LoginServer += "UserNum=";
        LoginServer += UserNum;
        LoginServer += "&UserName=";
        LoginServer += UserName;
        LoginServer += "&UserPhone=";
        LoginServer += UserPhone;
        LoginServer += "&UserGender=";
        LoginServer += UserGender;

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
