package com.cookandroid.charm_admin.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jungminki on 2016-07-07.
 * 미구현
 */
public class NoticeListActivity extends Activity {

    private Button btnCencle;
    private TextView btnAdd;
    private ListView noticeList;
    private NoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticelist);
        setTitle("공지사항");

        adapter = new NoticeAdapter();

        noticeList = (ListView)findViewById(R.id.notice_list);
        noticeList.setAdapter(adapter);  // 리스트 뷰에 adapter 를 등록한다

        connection();

        noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /*리스트 내부에 있는 값을 가져오기위한 변수*/
                NoticeListData item = (NoticeListData)adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);

                String num = item.getTvNum();

                Toast.makeText(getApplicationContext(),num,Toast.LENGTH_SHORT).show();

                String title = item.getTvNoticeTitle();

                String content = item.getTvNoticeContent();

                //title과 contents를 intent로 NoticeActivity로 전송
                intent.putExtra("Num",num);
                intent.putExtra("Add","");
                intent.putExtra("Title",title);
                intent.putExtra("Content", content);
                startActivity(intent);
            }
        });

/*        btnCencle = (Button)findViewById(R.id.notice_btnCancle);
        btnCencle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        btnAdd = (TextView) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                String changeName = "저장";
                intent.putExtra("Add",changeName);
                startActivity(intent);
            }
        });
    }

    private void connection() {

        String LoginServer = "http://118.36.3.200/notice.php";
        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();
            JSONArray var = new JSONArray(result);

            for (int i=0;i<var.length();i++){
                JSONObject varTest = new JSONObject(var.get(i).toString());// 한줄
                String NoticeNum = varTest.getString("NoticeNum");// NoticeNum에 해당하는 이름 가져옴
                String NoticeTitle = varTest.getString("NoticeTitle");// NoticeTitle에 해당하는 이름 가져옴
                String NoticeComment = varTest.getString("NoticeComment");// NoticeComment에 해당하는 이름 가져옴
                String NoticeDate = varTest.getString("NoticeDate");// NoticeDate에 해당하는 이름 가져옴
                adapter.addItem(NoticeNum,NoticeTitle,NoticeComment,NoticeDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
