package com.cookandroid.charm_admin.Reservation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;

/**
 * Created by HP on 2016-10-01.
 */
public class ReservationCheckActivity extends Activity{
    TextView txt_Name,txt_PhoneNum,txt_Item,txt_Price,txt_Date,txt_Time,txt_Mileage;
    String name,phoneNum,item,price,date,time,mileage;
    Button reservationOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("예약확인");
        setContentView(R.layout.activity_reservationcheck);

        item = getIntent().getStringExtra("Item");
        price = getIntent().getStringExtra("price");
        time = getIntent().getStringExtra("time");
        date = getIntent().getStringExtra("date");

        txt_Name =(TextView)findViewById(R.id.txt_reservationCheckName);
        txt_PhoneNum =(TextView)findViewById(R.id.txt_reservationCheckPhoneNum);
        txt_Item =(TextView)findViewById(R.id.txt_reservationCheckItem);
        txt_Price =(TextView)findViewById(R.id.txt_reservationCheckPrice);
        txt_Date =(TextView)findViewById(R.id.txt_reservationCheckDate);
        txt_Time =(TextView)findViewById(R.id.txt_reservationCheckTime);
        txt_Mileage =(TextView)findViewById(R.id.txt_reservationCheckMileage);
        reservationOK = (Button)findViewById(R.id.btn_reservatioinOK);

        Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();

        txt_Item.setText(item.toString());
        txt_Price.setText(price.toString());
        txt_Date.setText(date.toString());
        txt_Time.setText(time.toString());
        int mileage = Integer.parseInt(price.toString())/20;
        txt_Mileage.setText(Integer.toString(mileage));

        reservationOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"예약",Toast.LENGTH_SHORT).show();
                //finish();
            }
        });



    }



}
