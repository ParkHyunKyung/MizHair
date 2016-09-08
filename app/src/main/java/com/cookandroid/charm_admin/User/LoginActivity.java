package com.cookandroid.charm_admin.User;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.Main.MainActivity;
import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONObject;

/**
 * Created by pentacore-3 on 2016. 8. 25..
 */
public class LoginActivity extends Activity{

    private EditText edtId, edtPw; //입력받는 아이디, 비밀번호
    private Button btnLogin; //로그인 버튼
    private TextView tvLinktoSignup, tvLinktoFindPw; //회원가입 및 비밀번호 찾기
    private String strInputId, strInputPass, strId, strPass, strName; //사용자 정보 객체
    private Intent mainIntent; //화면 인텐트
    private CheckBox chkAutoLogin; //자동 로그인 체크박스
    private boolean isLogin = false; //로그인 성공, 실패 논리변수
    private SharedPreferences setting;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("로그인");

        //Xml 변수들에 대입
        edtId = (EditText) findViewById(R.id.login_edtId);
        edtPw = (EditText) findViewById(R.id.login_edtPw);
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
        tvLinktoSignup = (TextView) findViewById(R.id.login_tvLinktoJoin);
        tvLinktoFindPw = (TextView) findViewById(R.id.login_tvLinktoFindPw);
        chkAutoLogin = (CheckBox) findViewById(R.id.login_chkAutoLogin);

        //메인액티비티를 불러오는 인텐트 선언
        mainIntent = new Intent(getApplicationContext(), MainActivity.class);


        //자동로그인 저장된 데이터가 있을 시 로그인
        autoLoginLoad();

        //회원가입, 비밀번호 찾기 밑줄
        SpannableString spanSignup = new SpannableString("회원가입");
        spanSignup.setSpan(new UnderlineSpan(), 0, spanSignup.length(), 0);
        tvLinktoSignup.setText(spanSignup);
        SpannableString spanFindpw = new SpannableString("비밀번호 찾기");
        spanFindpw.setSpan(new UnderlineSpan(), 0, spanFindpw.length(), 0);
        tvLinktoFindPw.setText(spanFindpw);



        //로그인 버튼 클릭했을 때
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자가 입력한 아이디와 비밀번호를 문자변수에 저장시킴
                strInputId = edtId.getText().toString();
                strInputPass = edtPw.getText().toString();
                login();
            }
        });

        //회원가입 클릭 시 회원가입 화면 출력
        tvLinktoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        //비밀번호 찾기 클릭 시
        tvLinktoFindPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "미구현", Toast.LENGTH_SHORT).show();
                //비밀번호 창 구현 시 사용
                //Intent intent = new Intent(getApplicationContext(), FindPwActivity.class);
                //startActivity(intent);
            }
        });
    }

    private boolean isLoginCheck(String id, String pw) {

        String LoginServer = "http://118.36.3.200/Login.php?UserID=";
        LoginServer += id;
        LoginServer += "&UserPass=";
        LoginServer += pw;
        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();
            JSONObject state = new JSONObject(result);

            Toast.makeText(getApplicationContext(), state.getString("State"), Toast.LENGTH_SHORT).show();
            if (state.getString("State").equals("0")) {
                return false;
            } else {
                JSONObject info = new JSONObject(state.getString("Info"));
                strId = info.getString("UserID");
                strPass = info.getString("UserPass");
                strName = info.getString("UserName");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //로그인 메소드
    private void login(){

        if (strInputId.equals("")) {
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        } else if (strInputPass.equals("")) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        //로그인 서버체크
        isLogin = isLoginCheck(strInputId, strInputPass);

        //저장된 아이디와 비번 불러와서 입력된 것과 일치하는지 비교
        if (isLogin) {
            autoLoginSave();
                //사용자가 입력한 아이디와 비번을 메인액티비티에 넘김
                mainIntent.putExtra("LoginId", strId);
                mainIntent.putExtra("LoginPass", strPass);
                mainIntent.putExtra("LoginName", strName);

                startActivity(mainIntent);
                finish();
            } else {
            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_SHORT).show();
            edtPw.setText(null);
        }
    }

    /**
     * 자동로그인 자료 저장 메소드
     * 채크박스를 매개변수로 보내며 채크박스가 채크되어 있을 시 데이터값을 저장한다.
     */
    private void autoLoginSave() {
        setting = getSharedPreferences("setting", 0);
        editor = setting.edit();
        if (chkAutoLogin.isChecked()) {
            editor.putString("ID", strInputId);
            editor.putString("PW", strInputPass);
            editor.putBoolean("Auto_Login_enabled", true);
            editor.commit();
        } else {
            editor.clear();
            editor.commit();
        }
    }

    /**
     * 자동로그인 로드 메소드
     * 전에 자동로그인을 채크했다면 로그인을 바로 시작한다.
     */
    private void autoLoginLoad() {
        setting = getSharedPreferences("setting", 0);
        if (setting.getBoolean("Auto_Login_enabled", false)) {
            strInputId = setting.getString("ID", "");
            strInputPass = setting.getString("PW", "");
            chkAutoLogin.setChecked(true);
            login();
        }
    }
}
