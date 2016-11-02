package com.cookandroid.charm_admin.History;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
    static final int EDIT_PASSWORD = 0;
    static final int PICK_FROM_CAMERA = 1;
    static final int PICK_FROM_GALLERY = 2;
    static final int CROP_FROM_CAMERA = 3;
    private LinearLayout layout;
    private String HisCount,UserId,UserNum,ImageDate;
    private Bitmap bitmapEditPhoto;
    private ImageView AddImage;
    private Uri mImageUri;
    private File photo;
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
                Intent GalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(GalleryIntent,PICK_FROM_GALLERY);

            }
        });
        /*if (Integer.parseInt(HisCount)<1){
            Toast.makeText(getApplicationContext(),"저장된 사진이 없습니다.",Toast.LENGTH_SHORT).show();
        }else {
            imageDownLoad(UserId,HisCount);
        }*/

        imageDownLoad("ninehundred","6","2");
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
                String URL = "http://mizhair.ga/Images/";
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
            case EDIT_PASSWORD: {
                /*//새 비밀번호 받아오기
                strEditPw = data.getStringExtra("newpassword");
                //에디트텍스트에 출력
                edtEditPw.setText(String.valueOf(strEditPw));*/
                break;
            }

            //앨범 가져오기
            case PICK_FROM_GALLERY: {
                mImageUri = data.getData();
                Log.i("NR", mImageUri.getPath().toString());
                Toast.makeText(getApplicationContext(),"Gallery",Toast.LENGTH_SHORT).show();
                // 이후의 처리가 카메라 부분과 같아 break 없이 진행
            }

            //카메라에서 사진 가져오기
            case PICK_FROM_CAMERA: {
                photo = getImageFile(mImageUri);
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageUri, "image/*");
                Toast.makeText(getApplicationContext(),mImageUri.getPath().toString(),Toast.LENGTH_SHORT).show();
                //photo 경로추가 *수정
                Toast.makeText(getApplicationContext(),"Camera",Toast.LENGTH_SHORT).show();
                uploadImageToServer = new UploadImageToServer(photo.getPath(), "ninehundred");
                Toast.makeText(getApplicationContext(),photo.getPath(),Toast.LENGTH_SHORT).show();
                uploadImageToServer.execute();

                //crop한 이미지를 저장할 때 크기 및 비율 조정
                intent.putExtra("outputX", 120);
                intent.putExtra("outputY", 120);
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);

                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            }

            //crop완료 후 프로필 사진 설정
            case CROP_FROM_CAMERA: {
                final Bundle extras = data.getExtras();
                if (extras != null) {
                    bitmapEditPhoto = extras.getParcelable("data");
                    String SDCARD = Environment.getExternalStorageDirectory().getPath();
                    Toast.makeText(getApplicationContext(),"SDCARD:"+SDCARD,Toast.LENGTH_SHORT).show();
                    String TEMP_FOLDER = SDCARD + File.separator + "MizHair" + File.separator;
                    Toast.makeText(getApplicationContext(),"TEMP_FOLDER:"+TEMP_FOLDER,Toast.LENGTH_SHORT).show();
                    saveBitmapToFileCache(bitmapEditPhoto, TEMP_FOLDER, "temp.jpg");
                }
            }
        }
    }

    // Bitmap to File
    public  void saveBitmapToFileCache(Bitmap bitmap, String strFilePath,
                                       String filename) {

        File file = new File(strFilePath);
        Log.i("NR", file.toString());

        // If no folders
        if (!file.exists()) {
            file.mkdirs();
        }

        File fileCacheItem = new File(strFilePath +"/"+ filename);

        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //try {
            //    out.close();
            //} catch (IOException e) {
            //    e.printStackTrace();
            //}
        }
    }

    /**
     * 선택된 uri의 사진 Path를 가져온다.
     * uri 가 null 경우 마지막에 저장된 사진을 가져온다.
     *
     * @param uri
     * @return
     */
    private File getImageFile(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        if (uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        Cursor mCursor = getContentResolver().query(uri, projection, null, null,
                MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if (mCursor == null || mCursor.getCount() < 1) {
            return null; // no cursor or no record
        }
        int column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        mCursor.moveToFirst();

        String path = mCursor.getString(column_index);

        if (mCursor != null) {
            mCursor.close();
        }

        return new File(path);
    }
}
