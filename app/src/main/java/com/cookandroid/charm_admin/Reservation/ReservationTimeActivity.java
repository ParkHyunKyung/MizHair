package com.cookandroid.charm_admin.Reservation;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONArray;
import org.threeten.bp.LocalDate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import solar.blaz.date.week.WeekDatePicker;

/**
 * Created by HP on 2016-09-21.
 */
public class ReservationTimeActivity extends Activity {
    //private WeekDatePicker datePicker;
    private com.cookandroid.charm_admin.DatePicker.WeekDatePicker datePicker;
    private TextView txt_item,txt_price,txt_date;
    private LinearLayout layout_time,layout_time2,layout_time3,layout_time4,layout_time5,layout_time6,layout_time7,layout_time8;
    String Date = LocalDate.now().toString();
    String Name,Price,StNum,UserNum,UserPhone,UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationtime);

        txt_item = (TextView)findViewById(R.id.txt_item);
        txt_price = (TextView)findViewById(R.id.txt_price);
        txt_date = (TextView)findViewById(R.id.txt_date);
        layout_time = (LinearLayout)findViewById(R.id.layout_time);
        layout_time2 = (LinearLayout)findViewById(R.id.layout_time2);
        layout_time3 = (LinearLayout)findViewById(R.id.layout_time3);
        layout_time4 = (LinearLayout)findViewById(R.id.layout_time4);
        layout_time5 = (LinearLayout)findViewById(R.id.layout_time5);
        layout_time6 = (LinearLayout)findViewById(R.id.layout_time6);
        layout_time7 = (LinearLayout)findViewById(R.id.layout_time7);
        layout_time8 = (LinearLayout)findViewById(R.id.layout_time8);
        datePicker = (com.cookandroid.charm_admin.DatePicker.WeekDatePicker) findViewById(R.id.reservationtime_date_picker);

        datePicker.setDateIndicator(LocalDate.now().plusDays(0), true);
        datePicker.setLimits(LocalDate.now().minusWeeks(0), null);


        Name = getIntent().getStringExtra("Name");
        Price = getIntent().getStringExtra("Price");
        StNum = getIntent().getStringExtra("StNum");
        UserName = getIntent().getStringExtra("UserName");
        UserNum = getIntent().getStringExtra("UserNum");
        UserPhone = getIntent().getStringExtra("UserPhone");
        txt_date.setText(LocalDate.now().toString().substring(0,4)+"년"+LocalDate.now().toString().substring(5,7)+"월"+LocalDate.now().toString().substring(8,10)+"일");
        txt_item.setText(Name);
        txt_price.setText(Price);

        datePicker.setOnDateSelectedListener(new com.cookandroid.charm_admin.DatePicker.WeekDatePicker.OnDateSelected() {
            @Override
            public void onDateSelected(LocalDate date) {
                layout_time.removeAllViews();
                layout_time2.removeAllViews();
                layout_time3.removeAllViews();
                layout_time4.removeAllViews();
                layout_time5.removeAllViews();
                layout_time6.removeAllViews();
                layout_time7.removeAllViews();
                layout_time8.removeAllViews();
                Date = date.toString();
                getTimeinServer(Date,StNum);
                txt_date.setText(Date.substring(0,4)+"년"+Date.substring(5,7)+"월"+Date.substring(8,10)+"일");
            }
        });


        getTimeinServer(Date,StNum);
    }

    public void makeTime(final String Time, int count){
        TextView name = new TextView(this);
        TextView blank = new TextView(this);
        name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        name.setTextSize(30);
        blank.setTextSize(30);
        name.setText(Time);
        blank.setText("  ");
        name.setBackgroundDrawable(getResources().getDrawable(R.drawable.time_text));
        int line = count/3;

        switch (line){
            case 0:
                layout_time.addView(name);
                layout_time.addView(blank);
                break;
            case 1:
                layout_time2.addView(name);
                layout_time2.addView(blank);
                break;
            case 2:
                layout_time3.addView(name);
                layout_time3.addView(blank);
                break;
            case 3:
                layout_time4.addView(name);
                layout_time4.addView(blank);
                break;
            case 4:
                layout_time5.addView(name);
                layout_time5.addView(blank);
                break;
            case 5:
                layout_time6.addView(name);
                layout_time6.addView(blank);
                break;
            case 6:
                layout_time7.addView(name);
                layout_time7.addView(blank);
                break;
            case 7:
                layout_time8.addView(name);
                layout_time8.addView(blank);
                break;
        }

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent checkIntent = new Intent(getApplicationContext(),ReservationCheckActivity.class);

                checkIntent.putExtra("Item",Name);
                checkIntent.putExtra("Price",Price);
                checkIntent.putExtra("Time",Time);
                checkIntent.putExtra("Date",Date);
                checkIntent.putExtra("StNum",StNum);
                checkIntent.putExtra("UserNum",UserNum);
                checkIntent.putExtra("UserPhone",UserPhone);
                checkIntent.putExtra("UserName",UserName);
                startActivity(checkIntent);
            }
        });
    }

    private void getTimeinServer(String ReserveDate,String StNum) {
        try {
            ReserveDate = URLEncoder.encode(ReserveDate, "UTF-8");
            StNum = URLEncoder.encode(StNum, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String LoginServer = "http://mizhair.ga/availableTime.php?";
        LoginServer += "ReserveDate";
        LoginServer += ReserveDate;
        LoginServer += "&StNum";
        LoginServer += StNum;

        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();

            JSONArray var = new JSONArray(result);

            for(int i=0;i<var.length();i++){
                String time = var.get(i).toString();
                makeTime(time.substring(0,5),i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


