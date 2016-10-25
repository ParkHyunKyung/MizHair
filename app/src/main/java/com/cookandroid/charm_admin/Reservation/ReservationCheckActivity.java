package com.cookandroid.charm_admin.Reservation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by HP on 2016-10-01.
 */
public class ReservationCheckActivity extends Activity{
    TextView txt_Name,txt_PhoneNum,txt_Item,txt_Price,txt_Date,txt_Time;
    EditText edt_Memo;
    String UserName,UserPhone,UserNum,Item,Price,Date,Time,StNum;
    Button reservationOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("예약확인");
        setContentView(R.layout.activity_reservationcheck);

        Item = getIntent().getStringExtra("Item");
        Price = getIntent().getStringExtra("Price");
        Time = getIntent().getStringExtra("Time");
        Date = getIntent().getStringExtra("Date");
        StNum = getIntent().getStringExtra("StNum");
        UserName = getIntent().getStringExtra("UserName");
        UserNum = getIntent().getStringExtra("UserNum");
        UserPhone = getIntent().getStringExtra("UserPhone");

        txt_Name =(TextView)findViewById(R.id.txt_reservationCheckName);
        txt_PhoneNum =(TextView)findViewById(R.id.txt_reservationCheckPhoneNum);
        txt_Item =(TextView)findViewById(R.id.txt_reservationCheckItem);
        txt_Price =(TextView)findViewById(R.id.txt_reservationCheckPrice);
        txt_Date =(TextView)findViewById(R.id.txt_reservationCheckDate);
        txt_Time =(TextView)findViewById(R.id.txt_reservationCheckTime);
        edt_Memo = (EditText) findViewById(R.id.edt_reservationCheckMemo);
        reservationOK = (Button)findViewById(R.id.btn_reservatioinOK);

        Toast.makeText(getApplicationContext(),Item.toString(),Toast.LENGTH_SHORT).show();


        txt_Name.setText(UserName);
        txt_PhoneNum.setText(UserPhone.substring(0,3)+"-"+UserPhone.substring(3,7)+"-"+UserPhone.substring(7));
        txt_Item.setText(Item.toString());
        txt_Price.setText(Price.toString());
        txt_Date.setText(Date.toString());
        txt_Time.setText(Time.toString());
        reservationOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_Memo.getText().toString();
                //String memo;
                reservation(UserNum,StNum,Date,Time,edt_Memo.getText().toString());

            }
        });
    }

    private void reservation(String UserNum,String StNum,String ReserveDate, String ReserveTime, String ReserveMemo) {

        //5개의 값(ReserveDate ReserveTime ReserveMemo UserNum StNum)
        try {
            String SingupServer = "http://118.36.3.200/reservation.php?";
            UserNum = URLEncoder.encode(UserNum, "UTF-8");
            StNum = URLEncoder.encode(StNum, "UTF-8");
            ReserveDate = URLEncoder.encode(ReserveDate, "UTF-8");
            ReserveTime = URLEncoder.encode(ReserveTime, "UTF-8");
            ReserveMemo = URLEncoder.encode(ReserveMemo, "UTF-8");
            SingupServer += "&ReserveDate=";
            SingupServer += ReserveDate;
            SingupServer += "&ReserveMemo=";
            SingupServer += ReserveMemo;
            SingupServer += "&ReserveTime=";
            SingupServer += ReserveTime;
            SingupServer += "&UserNum=";
            SingupServer += UserNum;
            SingupServer += "&StNum=";
            SingupServer += StNum;
            URLConnector task = new URLConnector(SingupServer);
            task.start();

            task.join();
            String result = task.getResult();
            JSONObject state = new JSONObject(result);
            if (state.getString("STATE").equals("1")) {
                Toast.makeText(getApplicationContext(), "1.", Toast.LENGTH_SHORT).show();
                return;
            } else if (state.getString("STATE").equals("0")) {
                Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
