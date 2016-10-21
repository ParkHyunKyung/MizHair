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
    private WeekDatePicker datePicker;
    private TextView txt_item,txt_price;
    private LinearLayout layout_time,layout_time2,layout_time3;
    String Date = LocalDate.now().toString();
    String Name,Price,StNum,UserNum,UserPhone,UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationtime);

        txt_item = (TextView)findViewById(R.id.txt_item);
        txt_price = (TextView)findViewById(R.id.txt_price);
        layout_time = (LinearLayout)findViewById(R.id.layout_time);
        layout_time2 = (LinearLayout)findViewById(R.id.layout_time2);
        layout_time3 = (LinearLayout)findViewById(R.id.layout_time3);
        datePicker = (WeekDatePicker) findViewById(R.id.reservationtime_date_picker);

        datePicker.setDateIndicator(LocalDate.now().plusDays(0), true);
        datePicker.setLimits(LocalDate.now().minusWeeks(0), null);

        Name = getIntent().getStringExtra("Name");
        Price = getIntent().getStringExtra("Price");
        StNum = getIntent().getStringExtra("StNum");
        UserName = getIntent().getStringExtra("UserName");
        UserNum = getIntent().getStringExtra("UserNum");
        UserPhone = getIntent().getStringExtra("UserPhone");

        txt_item.setText(Name);
        txt_price.setText(Price);

        datePicker.setOnDateSelectedListener(new WeekDatePicker.OnDateSelected() {
            @Override
            public void onDateSelected(LocalDate date) {
                layout_time.removeAllViews();
                layout_time2.removeAllViews();
                layout_time3.removeAllViews();
                Date = date.toString();
                getTimeinServer(Date,StNum);
                Toast.makeText(getApplicationContext(),date.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        getTimeinServer(Date,StNum);
    }

    public void makeTime(final String Time, int count){
        TextView name = new TextView(this);
        name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        name.setPadding(20,20,20,20);
        name.setTextSize(30);
        name.setText(Time);
        name.setBackgroundDrawable(getResources().getDrawable(R.drawable.pricetime_text));
        if(count<7){
            layout_time.addView(name);
        }else if (count<14){
            layout_time2.addView(name);
        }else{
            layout_time3.addView(name);
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
        String LoginServer = "http://118.36.3.200/availableTime.php?";
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
