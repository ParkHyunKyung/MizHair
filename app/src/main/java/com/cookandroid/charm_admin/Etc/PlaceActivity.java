package com.cookandroid.charm_admin.Etc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cookandroid.charm_admin.R;

/**
 * Created by HP on 2016-10-29.
 */
public class PlaceActivity extends Activity {
    TextView txtHomePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        txtHomePage = (TextView)findViewById(R.id.place_txtHomePage);

        txtHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse( "http://mizhair2016.modoo.at"  )));
            }
        });

    }
}
