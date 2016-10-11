package com.cookandroid.charm_admin.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * 회원가입 화면
 * 사용자의 아이디, 비밀번호 및 비밀번호 체크, 이름을 입력받아 회원가입을 한다.
 */
public class JoinActivity extends Activity {

    private Button btnOK, btnCancel ; //회원가입 완료, 취소 버튼
    private EditText edtId, edtName, edtPassword, edtPasswordCheck,edtPhone,edtGender; //아이디, 이름, 비밀번호 및 비밀번호 재확인
    private RadioGroup rgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //Xml 변수들에 대입
        btnOK = (Button) findViewById(R.id.join_btnOK);
        btnCancel = (Button) findViewById(R.id.join_btnCancel);
        edtId = (EditText) findViewById(R.id.join_edtId);
        edtName = (EditText) findViewById(R.id.join_edtName);
        edtPhone = (EditText)findViewById(R.id.join_edtPhone);
        rgGender = (RadioGroup) findViewById(R.id.join_rgGender);
        edtPassword = (EditText) findViewById(R.id.join_edtPassword);
        edtPasswordCheck = (EditText) findViewById(R.id.join_edtPasswordCheck);

        //회원가입 버튼 클릭 시 제약조건 확인 및 회원가입 완료
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtId.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else if (edtName.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else if (edtPhone.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "연락처를 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else if (edtPassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!edtPassword.getText().toString().equals(edtPasswordCheck.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 체크해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "미구현", Toast.LENGTH_SHORT).show();
                    int id = rgGender.getCheckedRadioButtonId();
                    int genderNum = 0;
                    RadioButton radioButton = (RadioButton)findViewById(id);
                    if(radioButton.getText().toString().trim().equals("여성")){
                        genderNum = 0;
                    }else if(radioButton.getText().toString().trim().equals("남성")){
                        genderNum = 1;
                    }else{

                    }

                    Toast.makeText(getApplicationContext(),edtName.getText().toString().trim()+edtPhone.getText().toString().trim()+radioButton.getText().toString().trim()+Integer.toString(genderNum)+edtPassword.getText().toString(),Toast.LENGTH_SHORT).show();
                    //회원가입 메소드가 구현 완료시 사용
                    signUp(edtName.getText().toString().trim(), edtPhone.getText().toString().trim(),Integer.toString(genderNum).trim(),edtId.getText().toString().trim(), edtPassword.getText().toString());
                }
            }
        });

        //취소 시 로그인 화면을 출력
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    //회원가입이 서버가 되면 실행

    private void signUp(String name,String phone,String gender, String id, String pw) {
        try {
            String SingupServer = "http://118.36.3.200/SignUp.php?";
            name = URLEncoder.encode(name, "UTF-8");
            id = URLEncoder.encode(id, "UTF-8");
            pw = URLEncoder.encode(pw, "UTF-8");
            phone = URLEncoder.encode(phone, "UTF-8");
            gender = URLEncoder.encode(gender, "UTF-8");
            SingupServer += "UserName=";
            SingupServer += name;
            SingupServer += "&UserID=";
            SingupServer += id;
            SingupServer += "&UserPass=";
            SingupServer += pw;
            SingupServer += "&UserPhone=";
            SingupServer += phone;
            SingupServer += "&UserGender=";
            SingupServer += gender;
            URLConnector task = new URLConnector(SingupServer);
            task.start();


            task.join();
            String result = task.getResult();
            JSONObject state = new JSONObject(result);
            if (state.getString("STATE").equals("1")) {
                Toast.makeText(getApplicationContext(), "중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                return;
            } else if (state.getString("STATE").equals("0")) {
                Toast.makeText(getApplicationContext(), "회원가입 완료!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
