package com.cookandroid.charm_admin.Reservation;

import android.app.Activity;
import android.os.Bundle;

import com.cookandroid.charm_admin.R;

/**
 * Created by HP on 2016-09-21.
 */
public class ReservationTimeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationtime);

        String Name = getIntent().getStringExtra("Name");
        String Price = getIntent().getStringExtra("Price");
        String Time = getIntent().getStringExtra("Time");


    }
}
