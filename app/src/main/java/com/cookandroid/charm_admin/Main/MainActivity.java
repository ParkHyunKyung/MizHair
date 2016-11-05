package com.cookandroid.charm_admin.Main;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.CustomerManagement.ContactsListActivity;
import com.cookandroid.charm_admin.Etc.EtcActivity;
import com.cookandroid.charm_admin.History.HistoryActivity;
import com.cookandroid.charm_admin.Notice.NoticeListActivity;
import com.cookandroid.charm_admin.PriceList.PriceListActivity;
import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Reservation.ReservationActivity;
import com.cookandroid.charm_admin.ReservationList.ReservationListActivity;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.net.URLEncoder;

/**
 * 테스트용 주석
 */
public class MainActivity extends AppCompatActivity {
    private Intent reservationIntent;
    private ImageView btnReservation, btnNotice, btnPricelist, btnCustomerlist,btnReservationlist,btnSetting;
    private String UserId,UserPass,UserName,UserPhone,UserNum,UserGender,HisCount;
    private TextView txtReservation, txtNotice, txtPricelist, txtCustomerlist,txtReservationlist,txtSetting,
            txtReservationCount,txtTime,txtStyle,refresh;
    private String Date = LocalDate.now().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReservation = (ImageView)findViewById(R.id.main_btnReservation);
        btnReservationlist = (ImageView)findViewById(R.id.main_btnReservationlist);
        btnNotice = (ImageView)findViewById(R.id.main_btnNotice);
        btnPricelist = (ImageView)findViewById(R.id.main_btnPricelist);
        btnCustomerlist = (ImageView)findViewById(R.id.main_btnCustomerlist);
        btnSetting = (ImageView)findViewById(R.id.main_btnSetting);

        txtReservation = (TextView) findViewById(R.id.main_txtReservation);
        txtReservationlist = (TextView)findViewById(R.id.main_txtReservationlist);
        txtNotice = (TextView)findViewById(R.id.main_txtNotice);
        txtPricelist = (TextView)findViewById(R.id.main_txtPricelist);
        txtCustomerlist = (TextView)findViewById(R.id.main_txtCustomerlist);
        txtSetting = (TextView)findViewById(R.id.main_txtSetting);
        txtReservationCount = (TextView)findViewById(R.id.main_txtReservationCount);
        txtTime = (TextView)findViewById(R.id.main_txtTime);
        txtStyle = (TextView)findViewById(R.id.main_txtStyle);
        refresh = (TextView)findViewById(R.id.refresh);

        UserId = getIntent().getStringExtra("LoginId");
        UserPass = getIntent().getStringExtra("LoginPass");
        UserName = getIntent().getStringExtra("LoginName");
        UserPhone = getIntent().getStringExtra("LoginPhone");
        UserNum = getIntent().getStringExtra("LoginNum");
        UserGender = getIntent().getStringExtra("LoginGender");
        HisCount  = getIntent().getStringExtra("HisCount");

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoginCheck(UserId,UserPass);
                searchTodayRservation(Date.substring(0,4)+Date.substring(5,7)+Date.substring(8,10));
            }
        });
        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reservationIntent = new Intent(getApplicationContext(),ReservationActivity.class);
                reservationIntent.putExtra("UserName",UserName);
                reservationIntent.putExtra("UserNum",UserNum);
                reservationIntent.putExtra("UserPhone",UserPhone);
                startActivity(reservationIntent);
            }
        });

        btnReservationlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReservationListActivity.class);
                startActivity(intent);
            }
        });
        
        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeListActivity.class);
                startActivity(intent);
            }
        });

        btnPricelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PriceListActivity.class);
                startActivity(intent);
            }
        });

        btnCustomerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContactsListActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent etcIntent = new Intent(getApplicationContext(), EtcActivity.class);
                etcIntent.putExtra("UserName",UserName);
                etcIntent.putExtra("UserNum",UserNum);
                etcIntent.putExtra("UserId",UserId);
                etcIntent.putExtra("UserPass",UserPass);
                etcIntent.putExtra("UserPhone",UserPhone);
                etcIntent.putExtra("UserGender",UserGender);
                startActivity(etcIntent);

            }
        });

        searchTodayRservation(Date.substring(0,4)+Date.substring(5,7)+Date.substring(8,10));
    }
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_button_right, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            Toast.makeText(this, "홈아이콘 이벤트", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_button_right) {
            Toast.makeText(this, "액션버튼 이벤트", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

*/
    private  void font(){
        Typeface type = Typeface.createFromAsset(this.getAssets(), "NanumSquareOTFR.ttf");

        txtReservation.setTypeface(type);
        txtReservationlist.setTypeface(type);
        txtNotice.setTypeface(type);
        txtPricelist.setTypeface(type);
        txtCustomerlist.setTypeface(type);
        txtSetting.setTypeface(type);
        txtReservationCount.setTypeface(type);
        txtTime.setTypeface(type);
        txtStyle.setTypeface(type);
    }

    private void searchTodayRservation(String date) {

        /*String[] dateArray = LocalDate.now().toString().split("-");
        String date = dateArray[0]+dateArray[1]+dateArray[2];*/

        try {
            String SingupServer = "http://mizhair.ga/showReservation.php?";
            date = URLEncoder.encode(date, "UTF-8");
            SingupServer += "ReserveDate="; // 10월20일 = 20161020
            SingupServer += date;
            URLConnector task = new URLConnector(SingupServer);
            task.start();
            //Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT).show();
            task.join();
            String result = task.getResult();
            JSONArray var = new JSONArray(result);

            txtReservationCount.setText(txtReservationCount.getText()+" "+Integer.toString(var.length())+"건");
            for (int i=0;i<var.length();i++){
                JSONObject varTest = new JSONObject(var.get(i).toString());// 한줄
                String ReserveTime = varTest.getString("ReserveTime");// NoticeNum에 해당하는 이름 가져옴
                String StName = varTest.getString("StName");// NoticeTitle에 해당하는 이름 가져옴
                String UserName = varTest.getString("UserName");// NoticeComment에 해당하는 이름 가져옴
                String Time = txtTime.getText().toString()+ReserveTime.substring(0,5)+"\n";
                String Style = txtStyle.getText().toString()+StName+"\n";
                txtTime.setText(Time);
                txtStyle.setText(Style);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private boolean isLoginCheck(String id, String pw) {

        String LoginServer = "http://mizhair.ga/Login.php?UserID=";
        LoginServer += id;
        LoginServer += "&UserPass=";
        LoginServer += pw;
        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();
            JSONObject state = new JSONObject(result);

            //Toast.makeText(getApplicationContext(), state.getString("State"), Toast.LENGTH_SHORT).show();
            if (state.getString("State").equals("0")) {
                Toast.makeText(getApplicationContext(),"!",Toast.LENGTH_SHORT).show();
                return false;
            } else {
                JSONObject info = new JSONObject(state.getString("Info"));
                UserId = info.getString("UserID");
                UserPass = info.getString("UserPass");
                UserName = info.getString("UserName");
                UserPhone = info.getString("UserPhone");
                UserNum = info.getString("UserNum");
                UserGender = info.getString("UserGender");
                HisCount = info.getString("HisCount");
                Toast.makeText(getApplicationContext(),HisCount+"!",Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

