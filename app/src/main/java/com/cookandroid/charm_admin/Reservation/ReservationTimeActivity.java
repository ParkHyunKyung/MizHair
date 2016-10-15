package com.cookandroid.charm_admin.Reservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cookandroid.charm_admin.R;
import org.threeten.bp.LocalDate;
import solar.blaz.date.week.WeekDatePicker;

/**
 * Created by HP on 2016-09-21.
 */
public class ReservationTimeActivity extends Activity {
    private WeekDatePicker datePicker;
    private TextView txt_item,txt_price;
    private LinearLayout layout_time;
    String Date = LocalDate.now().toString();
    String Name,Price,StNum,UserNum,UserPhone,UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationtime);

        txt_item = (TextView)findViewById(R.id.txt_item);
        txt_price = (TextView)findViewById(R.id.txt_price);
        layout_time = (LinearLayout)findViewById(R.id.layout_time);

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
                Date = date.toString();
                makeTime("1:30");
                makeTime("2:30");
                makeTime("3:30");
                Toast.makeText(getApplicationContext(),date.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        makeTime("1:30");
        makeTime("2:30");
        makeTime("3:30");
    }

    public void makeTime(final String Time){

        TextView name = new TextView(this);
        name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        name.setPadding(20,20,20,20);
        name.setTextSize(30);
        name.setText(Time);
        layout_time.addView(name);

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
}
