package com.cookandroid.charm_admin.ReservationList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.cookandroid.charm_admin.R;

/**
 * Created by HP on 2016-09-06.
 * 예약리스트에 클릭된 리스트의 구체적인 정보를 보여준다.
 */
public class ReservationViewActivity extends Activity {
    private EditText edtName,edtItem,edtPrise,edtDate,edtTime,edtRunTime,edtMileage,edtMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_view);

        edtName = (EditText)findViewById(R.id.edtName);
        edtItem = (EditText)findViewById(R.id.edtItem);
        edtPrise = (EditText)findViewById(R.id.edtPrise);
        edtDate = (EditText)findViewById(R.id.edtDate);
        edtTime = (EditText)findViewById(R.id.edtTime);
        edtRunTime = (EditText)findViewById(R.id.edtRunTime);
        edtMileage = (EditText)findViewById(R.id.edtMileage);
        edtMemo = (EditText)findViewById(R.id.edtMemo);

        Intent intent = this.getIntent();
        String Name = intent.getStringExtra("Name");
        String Item = intent.getStringExtra("Item");
        String Date = intent.getStringExtra("Date");

        /*
        String Prise = intent.getStringExtra("Prise");
        String Time = intent.getStringExtra("Time");
        String RunTime = intent.getStringExtra("RunTime");
        String Mileage = intent.getStringExtra("Mileage");
        String Memo = intent.getStringExtra("Memo");*/

        edtName.setText(Name);
        edtItem.setText(Item);
        edtDate.setText(Date);

        /*
        edtPrise.setText(Prise);
        edtTime.setText(Time);
        edtRunTime.setText(RunTime);
        edtMileage.setText(Mileage);
        edtMemo.setText(Memo);*/

    }
}
