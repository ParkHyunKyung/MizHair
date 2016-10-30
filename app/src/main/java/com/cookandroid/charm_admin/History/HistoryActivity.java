package com.cookandroid.charm_admin.History;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.ImageDownLoader.ImageDownloader;
import com.cookandroid.charm_admin.ImageDownLoader.UploadImageToServer;
import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
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
    private LinearLayout layout;
    private String HisCount,UserId,UserNum,ImageDate;
    private ImageView AddImage;
    private Uri mImageUri;
    private UploadImageToServer uploadImageToServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        UserId = getIntent().getStringExtra("UserId");
        HisCount = getIntent().getStringExtra("HisCount");
        UserNum = getIntent().getStringExtra("UserNum");

        layout = (LinearLayout)findViewById(R.id.layout_history);
        AddImage = (ImageView)findViewById(R.id.history_addImage);

        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GalleryIntent = new Intent(Intent.ACTION_PICK);
                GalleryIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(GalleryIntent,0);

            }
        });
        /*if (Integer.parseInt(HisCount)<1){
            Toast.makeText(getApplicationContext(),"저장된 사진이 없습니다.",Toast.LENGTH_SHORT).show();
        }else {
            imageDownLoad(UserId,HisCount);
        }*/

        imageDownLoad("ninehundred","5","2");
    }
    public void imageDownLoad(String UserId, String HisCount,String UserNum)
    {
        final ImageView imageView[] = new ImageView[Integer.parseInt(HisCount)];
        try {
            String ImageDateServer = "http://mizhair.ga/imgInfo.php?UserNum=";
            ImageDateServer += UserNum;
            UserNum = URLEncoder.encode(UserNum, "UTF-8");
            URLConnector task = new URLConnector(ImageDateServer);
            task.start();

            task.join();
            String result = task.getResult();
            JSONObject date = new JSONObject(result);

            for (int i=0;i<date.length();i++){

                //이미지 넣기
                String URL = "http://118.36.3.200/Images/";
                URL += UserId+"/";
                URL += Integer.toString(i+1);
                imageView[i] = new ImageView(this);
                imageView[i].setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                ImageDownloader.download(URL, imageView[i]);
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)imageView[i].getLayoutParams();
                params.width = 700;
                params.height = 700;
                imageView[i].setLayoutParams(params);
                imageView[i].setBackgroundResource(R.drawable.image_border);
                layout.addView(imageView[i]);

                //날짜 넣기
                TextView textView = new TextView(this);
                TextView blank = new TextView(this);
                ImageDate = date.getString(Integer.toString(i+1));//
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(30);
                blank.setTextSize(30);
                textView.setText(ImageDate.substring(2,10).toString());
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(R.drawable.imagedate_border);
                layout.addView(textView);
                layout.addView(blank);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override //양방향 액티비티
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {

            //앨범 가져오기
            case 0: {
                mImageUri = data.getData();
                String uri = mImageUri.getPath();
                uploadImageToServer = new UploadImageToServer(uri,2);
                uploadImageToServer.execute();
            }

        }
    }
}
