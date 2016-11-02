package com.cookandroid.charm_admin.ImageDownLoader;
import android.os.AsyncTask;
import android.util.Log;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by HP on 2016-10-27.
 */
public class UploadImageToServer extends AsyncTask<String, String, String> {
    // LOG
    private String TAGLOG = "Log";

    // 서버로 업로드할 파일관련 변수
    public String uploadFilePath;

    // 파일을 업로드 하기 위한 변수 선언
    private int serverResponseCode = 0;

    String fileName;
    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    String UserID;
    int bytesRead, bytesAvailable, bufferSize, user_num;
    byte[] buffer;
    int maxBufferSize = 1 * 10240 * 10240;
    File sourceFile;

    public UploadImageToServer(String sourceFileUri, String UserID) {
        this.UserID = UserID;
        fileName = sourceFileUri;
        sourceFile = new File(sourceFileUri);
    }

    @Override
    protected String doInBackground(String... serverUrl) {
        if (!sourceFile.isFile()) {
            Log.i(TAGLOG, "[UploadImageToServer] Source File not exist :" + uploadFilePath);
            return null;
        } else {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                String strurl = "http://mizhair.ga/ImgUpload.php?UserID=";
                strurl += UserID;
                Log.i(TAGLOG, strurl);
                URL url = new URL(strurl);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                Log.i(TAGLOG, "fileName: " + fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                // 사용자 이름으로 폴더를 생성하기 위해 사용자 이름을 서버로 전송한다.
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"data1\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes("newImage");
                dos.writeBytes(lineEnd);

                // 이미지 전송
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i(TAGLOG, "[UploadImageToServer] HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {
                    Log.i(TAGLOG, "[UploadImageToServer] ok");
                }
                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Log.i(TAGLOG, "[UploadImageToServer] error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAGLOG, "[UploadImageToServer] Upload file to server Exception Exception : " + e.getMessage(), e);
            }
            Log.i(TAGLOG, "[UploadImageToServer] Finish");
            return null;
        } // End else block
    }
}