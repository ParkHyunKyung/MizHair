package com.cookandroid.charm_admin.ReservationList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.CustomerManagement.HangulUtils;
import com.cookandroid.charm_admin.Notice.NoticeAdapter;
import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import solar.blaz.date.week.WeekDatePicker;

/**
 * Created by HP on 2016-09-03.
 */
public class ReservationListActivity extends Activity{
    private WeekDatePicker datePicker;
    private String searchDate;
    private ReservationAdapter adapter;
    private ListView reservationlist;
    private TextView tv_NoneCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationlist);
        setTitle("메인화면");

        reservationlist = (ListView)findViewById(R.id.reservationlist);
        datePicker = (WeekDatePicker) findViewById(R.id.date_picker);
        datePicker.setDateIndicator(LocalDate.now().plusDays(0), true);
        datePicker.setLimits(LocalDate.now().minusWeeks(0), null);
        tv_NoneCustomer = (TextView)findViewById(R.id.tv_NoneCustomer);

        searchRservation(LocalDate.now().toString());
        /*날짜 클릭시 발생하는 이벤트*/
        datePicker.setOnDateSelectedListener(new WeekDatePicker.OnDateSelected() {
            @Override
            public void onDateSelected(LocalDate date) {

                try {
                    /*클릭한 날짜를 서버에 전송*/
                    String[] dateArray = date.toString().split("-");
                    searchDate = dateArray[0]+dateArray[1]+dateArray[2];

                    searchRservation(searchDate);

                } catch (Exception e) {
                    Log.e("", e.getMessage(), e);
                }
            }
        });
    }

    private void searchRservation(String date) {
        adapter = new ReservationAdapter();
        reservationlist.setAdapter(adapter);  // 리스트 뷰에 adapter 를 등록한다

        try {
            String SingupServer = "http://118.36.3.200/showReservation.php?";
            date = URLEncoder.encode(date, "UTF-8");
            SingupServer += "ReserveDate="; // 10월20일 = 20161020
            SingupServer += date;
            URLConnector task = new URLConnector(SingupServer);
            task.start();

            task.join();
            String result = task.getResult();
            JSONArray var = new JSONArray(result);

            if(var.length()==0){
                tv_NoneCustomer.setVisibility(View.VISIBLE);
            }else{
                tv_NoneCustomer.setVisibility(View.INVISIBLE);
            }

            for (int i=0;i<var.length();i++){
                JSONObject varTest = new JSONObject(var.get(i).toString());// 한줄
                String ReserveTime = varTest.getString("ReserveTime");// NoticeNum에 해당하는 이름 가져옴
                String StName = varTest.getString("StName");// NoticeTitle에 해당하는 이름 가져옴
                String UserName = varTest.getString("UserName");// NoticeComment에 해당하는 이름 가져옴
                adapter.addItem(UserName,StName,ReserveTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
