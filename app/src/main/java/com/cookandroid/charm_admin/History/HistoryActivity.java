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

import com.cookandroid.charm_admin.ImageDownLoader.ImageDownloader;
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
    LinearLayout layout;
    String HisCount,UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        UserId = getIntent().getStringExtra("UserId");
        HisCount = getIntent().getStringExtra("HisCount");

        layout = (LinearLayout)findViewById(R.id.layout_history);
        /*if (Integer.parseInt(HisCount)<1){
            Toast.makeText(getApplicationContext(),"저장된 사진이 없습니다.",Toast.LENGTH_SHORT).show();
        }else {
            imageDownLoad(UserId,HisCount);
        }*/

        imageDownLoad("ninehundred","3");
    }
    public void imageDownLoad(String UserId, String HisCount)
    {
        final ImageView imageView[] = new ImageView[Integer.parseInt(HisCount)];
        for(int i = 0; i < Integer.parseInt(HisCount) ; i++){
            String URL = "http://118.36.3.200/Images/";
            URL += UserId+"/";
            URL += Integer.toString(i+1);
            imageView[i] = new ImageView(this);
            imageView[i].setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            ImageDownloader.download(URL, imageView[i]);
            layout.addView(imageView[i]);
        }
    }

}
