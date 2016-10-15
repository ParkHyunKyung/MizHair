package com.cookandroid.charm_admin.History;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by HP on 2016-10-15.
 */
public class HistoryActivity extends Activity {
    String HisCount,UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        UserId = getIntent().getStringExtra("UserId");
        HisCount = getIntent().getStringExtra("HisCount");
        HisCount = "5";

        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_history);
        ImageView imageView[] = new ImageView[Integer.parseInt(HisCount)];
        for(int i = 0; i < Integer.parseInt(HisCount); i++){
            imageView[i] = new ImageView(this);
            imageView[i].setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            connect(UserId,imageView[i]);
            layout.addView(imageView[i]);
        }
        /*for(int i = 0; i < Integer.parseInt(HisCount); i++){
            imageView[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // 하고싶은처리.....
                }
            });
        }*/

    }

    public void connect(final String UserId, final ImageView imageView){

        new Thread() {
            public void run() {
                String ImageServer = "http://118.36.3.200/Images";
                ImageServer += "/"+"ninehundred";
                ImageServer += "/"+"3";

                Bitmap bitmap = null;
                try {
                    URL url = new URL(ImageServer);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
