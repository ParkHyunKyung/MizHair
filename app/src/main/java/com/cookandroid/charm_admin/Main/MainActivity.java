package com.cookandroid.charm_admin.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cookandroid.charm_admin.CustomerManagement.ContactsListActivity;
import com.cookandroid.charm_admin.Notice.NoticeListActivity;
import com.cookandroid.charm_admin.PriceList.PriceListActivity;
import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Reservation.ReservationActivity;
import com.cookandroid.charm_admin.Reservation.ReservationTest;
import com.cookandroid.charm_admin.ReservationList.ReservationListActivity;
import com.cookandroid.charm_admin.User.LoginActivity;

import java.util.ArrayList;

/**
 * 테스트용 주석
 */
public class MainActivity extends AppCompatActivity {
    private Intent reservationIntent;
    Button btnReservation;
    private Button btn_Reservation, btnNotice, btnPricelist, btnCustomerlist,btnReservationlist,btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ActionBar에 타이틀 변경
        getSupportActionBar().setTitle("메인화면");
        // ActionBar의 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        // 홈 아이콘 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_Reservation = (Button)findViewById(R.id.main_btnReservation);
        btnReservationlist = (Button)findViewById(R.id.main_btnReservationlist);
        btnReservation = (Button)findViewById(R.id.btnReservation);
        btnNotice = (Button)findViewById(R.id.main_btnNotice);
        btnPricelist = (Button)findViewById(R.id.main_btnPricelist);
        btnCustomerlist = (Button)findViewById(R.id.main_btnCustomerlist);
        btnSetting = (Button)findViewById(R.id.main_btnSetting);

        btnReservationlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reservationlistIntent = new Intent(getApplicationContext(),ReservationListActivity.class);
                startActivity(reservationlistIntent);
            }
        });

        btn_Reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reservationIntent = new Intent(getApplicationContext(),ReservationTest.class);
                startActivity(reservationIntent);
            }
        });

        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reservationIntent = new Intent(getApplicationContext(),ReservationActivity.class);
                ArrayList<String> arrCheckBox = new ArrayList<>();
                ArrayList<Integer> arrPrise = new ArrayList<>();
                ArrayList<Integer> arrNum = new ArrayList<>(); // 기본키 배열



                /*서버에서 기본키를 포함해서 가져옴
                * 화면에는 기본키를 보여주지는 않으나 서버에는 기본키만 전송함*/
                //test용
                arrCheckBox.add("일반컷");
                arrPrise.add(10000);
                arrCheckBox.add("특수컷");
                arrPrise.add(15000);
                arrCheckBox.add("청소년컷");
                arrPrise.add(7000);
                arrCheckBox.add("특수펌");
                arrPrise.add(5000);
                arrCheckBox.add("드라이");
                arrPrise.add(20000);
                arrCheckBox.add("염색");
                arrPrise.add(30000);
                arrCheckBox.add("업스타일");
                arrPrise.add(30000);
                arrCheckBox.add("매니큐어");
                arrPrise.add(30000);
                reservationIntent.putExtra("arrNum",arrNum);
                reservationIntent.putExtra("arrCheckBox",arrCheckBox);
                reservationIntent.putExtra("arrPrise",arrPrise);
                startActivity(reservationIntent);
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
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
                //자동로그인 캐시를 지운다.
                SharedPreferences setting;
                SharedPreferences.Editor editor;
                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.clear();
                editor.commit();
                finish();
            }
        });

    }

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
}
