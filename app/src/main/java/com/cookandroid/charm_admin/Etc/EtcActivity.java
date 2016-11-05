package com.cookandroid.charm_admin.Etc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cookandroid.charm_admin.CustomerManagement.CustomerActivity;
import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.User.LoginActivity;

/**
 * Created by HP on 2016-10-26.
 */
public class EtcActivity extends Activity {
    TextView txtPlace,txtLogout,txtCustomerModify;
    private String UserId,UserPass,UserName,UserPhone,UserNum,UserGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc);

        UserId = getIntent().getStringExtra("UserId");
        UserName = getIntent().getStringExtra("UserName");
        UserPhone = getIntent().getStringExtra("UserPhone");
        UserNum = getIntent().getStringExtra("UserNum");
        UserGender = getIntent().getStringExtra("UserGender");

        txtPlace = (TextView)findViewById(R.id.etc_txtPlace);
        txtLogout = (TextView)findViewById(R.id.etc_txtLogout);
        txtCustomerModify = (TextView)findViewById(R.id.etc_txtCustomerModify);

        txtPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent placeintent = new Intent(getApplicationContext(), PlaceActivity.class);
                startActivity(placeintent);

            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
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

        txtCustomerModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerintent = new Intent(getApplicationContext(), CustomerActivity.class);

                customerintent.putExtra("UserName",UserName);
                customerintent.putExtra("UserPhone",UserPhone);
                customerintent.putExtra("UserId",UserId);
                customerintent.putExtra("UserGender",UserGender);
                customerintent.putExtra("UserNum",UserNum);
                startActivity(customerintent);
            }
        });
    }
}
