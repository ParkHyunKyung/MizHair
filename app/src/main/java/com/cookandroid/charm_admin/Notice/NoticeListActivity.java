package com.cookandroid.charm_admin.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;

import java.lang.reflect.Array;

/**
 * Created by Jungminki on 2016-07-07.
 * 미구현
 */
public class NoticeListActivity extends Activity {

    private Button btnCencle, btnAdd;
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

        String contents = "어쩌고 저쩌고" + "\n 가나다라 마바사 아자차카타파하 ";
        String[] strContent = contents.split("\n");// 첫줄만 자르기
        adapter.addItem("공지1",strContent[0]);
        adapter.addItem("공지2",strContent[0]);
        adapter.addItem("공지3",strContent[0]);
        adapter.addItem("공지4",strContent[0]);
        adapter.addItem("공지5",strContent[0]);

        noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /*리스트 내부에 있는 값을 가져오기위한 변수*/
                NoticeListView item = (NoticeListView)adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);

                /*title = item.getTvNoticeTItle() 에 해당하는 내용을 서버에서 가져옴*/
                String title = item.getTvNoticeTItle();

                String content = item.getTvNoticeContent();// 서버에서 가져온 내용을 저장

                /*title과 contents를 intent로 NoticeActivity로 전송*/
                intent.putExtra("Add","");
                intent.putExtra("Title",title);
                intent.putExtra("Content", content);
                startActivity(intent);
            }
        });

        btnCencle = (Button)findViewById(R.id.notice_btnCancle);
        btnCencle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd = (Button)findViewById(R.id.btn_add);
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
}
