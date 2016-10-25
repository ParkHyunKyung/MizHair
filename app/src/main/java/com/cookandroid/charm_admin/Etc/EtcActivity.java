package com.cookandroid.charm_admin.Etc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.User.LoginActivity;

/**
 * Created by HP on 2016-10-26.
 */
public class EtcActivity extends Activity {
    TextView txtPlace,txtLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc);

        txtPlace = (TextView)findViewById(R.id.etc_txtPlace);
        txtLogout = (TextView)findViewById(R.id.etc_txtLogout);

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

    }
}
